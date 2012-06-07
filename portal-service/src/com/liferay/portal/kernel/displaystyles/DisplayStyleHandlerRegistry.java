/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.displaystyles;

import java.util.List;

/**
 * @author Juan Fernández
 */
public interface DisplayStyleHandlerRegistry {

	/**
	 * Returns the display style handler associated with the class name.
	 *
	 * @param  className class name of the DisplayStyle
	 * @return the display style handler associated with the class name
	 */
	public DisplayStyleHandler getDisplayStyleHandler(String className);

	/**
	 * Returns the display style handler associated with the class name id.
	 *
	 * @param  classNameId class name id of the DisplayStyle
	 * @return the display style handler associated with the class name
	 */
	public DisplayStyleHandler getDisplayStyleHandler(long classNameId);

	/**
	 * Returns all of the display style handlers.
	 *
	 * @return the display style handlers
	 */
	public List<DisplayStyleHandler> getDisplayStyleHandlers();

	/**
	 * Registers the display style handler.
	 *
	 * @param displayStyleHandler the DisplayStyle to register
	 */
	public void register(DisplayStyleHandler displayStyleHandler);

	/**
	 * Unregisters the display style handler.
	 *
	 * @param displayStyleHandler the display style handler to unregister
	 */
	public void unregister(DisplayStyleHandler displayStyleHandler);

}