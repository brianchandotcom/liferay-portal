/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.struts;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.struts.tiles.NoSuchDefinitionException;

/**
 * @author Shuyang Zhou
 */
public class XmlDefinitionsSet
{
    /** Defined definitions. */
  protected Map definitions;

     /**
      * Constructor.
      */
  public XmlDefinitionsSet()
   {
   definitions = new HashMap();
   }

  /**
   * Put definition in set.
   * @param definition Definition to add.
   */
  public void putDefinition(XmlDefinition definition)
  {
  definitions.put( definition.getName(), definition );
  }

  /**
   * Get requested definition.
   * @param name Definition name.
   */
  public XmlDefinition getDefinition(String name)
  {
  return (XmlDefinition)definitions.get( name );
  }

  /**
   * Get definitions map.
   */
  public Map getDefinitions()
  {
  return definitions;
  }

  /**
   * Resolve extended instances.
   */
  public void resolveInheritances() throws NoSuchDefinitionException
    {
      // Walk through all definitions and resolve individual inheritance
    Iterator i = definitions.values().iterator();
    while( i.hasNext() )
      {
      XmlDefinition definition = (XmlDefinition)i.next();
      definition.resolveInheritance( this );
      }  // end loop
    }

  /**
   * Add definitions from specified child definitions set.
   * For each definition in child, look if it already exists in this set.
   * If not, add it, if yes, overload parent's definition with child definition.
   * @param child Definition used to overload this object.
   */
  public void extend( XmlDefinitionsSet child )
    {
    if(child==null)
      return;
    Iterator i = child.getDefinitions().values().iterator();
    while( i.hasNext() )
      {
      XmlDefinition childInstance = (XmlDefinition)i.next();
      XmlDefinition parentInstance = getDefinition(childInstance.getName() );
      if( parentInstance != null )
        {
        parentInstance.overload( childInstance );
        }
       else
        putDefinition( childInstance );
      } // end loop
    }
    /**
     * Get String representation.
     */
  public String toString()
    {
    return "definitions=" + definitions.toString() ;
    }

}