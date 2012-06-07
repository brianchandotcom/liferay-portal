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
public class DisplayStyleHandlerRegistryUtil {

	public static DisplayStyleHandler getDisplayStyleHandler(String className) {
		return getDisplayStyleRegistry().getDisplayStyleHandler(className);
	}

	public static DisplayStyleHandler getDisplayStyleHandler(long classNameId) {
		return getDisplayStyleRegistry().getDisplayStyleHandler(classNameId);
	}

	public static List<DisplayStyleHandler> getDisplayStyleHandlers() {
		return getDisplayStyleRegistry().getDisplayStyleHandlers();
	}

	public static DisplayStyleHandlerRegistry getDisplayStyleRegistry() {
		return _displayStyleHandlerRegistry;
	}

	public static void register(DisplayStyleHandler displayStyleHandler) {
		getDisplayStyleRegistry().register(displayStyleHandler);
	}

	public void setDisplayStyleHandlerRegistry(
		DisplayStyleHandlerRegistry displayStyleHandlerRegistry) {

		_displayStyleHandlerRegistry = displayStyleHandlerRegistry;
	}

	public static void unregister(DisplayStyleHandler displayStyleHandler) {
		getDisplayStyleRegistry().unregister(displayStyleHandler);
	}

	private static DisplayStyleHandlerRegistry _displayStyleHandlerRegistry;

}