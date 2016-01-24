/*
 *  IconicComponent.java
 *  (FScape)
 *
 *  Copyright (c) 2001-2016 Hanns Holger Rutz. All rights reserved.
 *
 *  This software is published under the GNU General Public License v3+
 *
 *
 *	For further information, please contact Hanns Holger Rutz at
 *	contact@sciss.de
 *
 *
 *  Changelog:
 *		24-Jun-06	renamed to IconicComponent
 */

package de.sciss.fscape.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

/**
 *  Created on Java 1.1 using the
 *	same name as the Swing interface,
 *	this class draws a portion of
 *	a icon collection bitmap graphic.
 */
public class IconicComponent
        extends JComponent
        implements Dragable {

// -------- public variables --------

    public static DataFlavor flavor	= null;		// DataFlavor representing this class

// -------- private variables --------

    protected IconBitmap ib;
    protected Dimension d;
    protected int ID;

    private static DataFlavor flavors[] = null;	// alle erlaubten DataFlavors

// -------- public methods --------
    // public void setID( int ID );
    // public int getID();

    /**
     *	@param	ib	IconBitmap, die die Graphik enthaelt
     *	@param	ID	Icon-ID in der Bitmap-Matrix
     */
    protected IconicComponent( IconBitmap ib, int ID )
    {
        this.ib	= ib;
        d		= ib.getDimension();
        setSize( getPreferredSize() );
        setID( ID );

        // data flavor
        if( flavor == null ) {
            flavor			= new DataFlavor( getClass(), "Icon" );
            flavors			= new DataFlavor[ 1 ];
            flavors[ 0 ]	= IconicComponent.flavor;
        }
    }

    /**
     *	@param	ib	IconBitmap, die die Graphik enthaelt
     */
    protected IconicComponent( IconBitmap ib )
    {
        this( ib, -1 );
    }

    /**
     *	ID des Icons setzen
     *
     *	@param	ID	Icon-ID in der Bitmap-Matrix
     */
    public void setID( int ID )
    {
        this.ID = ID;
    }

    /**
     *	ID des Icons ermitteln
     *
     *	@return	Icon-ID in der Bitmap-Matrix
     */
    public int getID()
    {
        return ID;
    }

    public Dimension getPreferredSize()
    {
        return new Dimension( d );
    }
    public Dimension getMinimumSize()
    {
        return getPreferredSize();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Dimension realD = getSize();
        ib.paint(g, ID, (realD.width - d.width) >> 1, (realD.height - d.height) >> 1);
    }

// -------- Dragable methods --------

    /**
     *	Zeichnet ein Schema des Icons
     */
    public void paintScheme(Graphics g, int x, int y, boolean mode) {
        g.drawRect(x - (d.width >> 1), y - (d.height >> 1), d.width - 1, d.height - 1);
    }

// -------- Transferable methods --------

    public DataFlavor[] getTransferDataFlavors() {
        return flavors;
    }

    public boolean isDataFlavorSupported(DataFlavor fl) {
        DataFlavor flavs[] = getTransferDataFlavors();
        for (int i = 0; i < flavs.length; i++) {
            if (flavs[i].equals(fl)) return true;
        }
        return false;
    }

    public Object getTransferData(DataFlavor fl)
            throws UnsupportedFlavorException, IOException {
        if (fl.equals(IconicComponent.flavor)) {
            return this;

        } else throw new UnsupportedFlavorException(fl);
    }
}