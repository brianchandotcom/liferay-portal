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

package com.liferay.portal.displaystyles;

import com.liferay.portal.kernel.displaystyles.DisplayStyleHandler;
import com.liferay.portal.kernel.displaystyles.DisplayStyleHandlerRegistry;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.util.PortalUtil;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Juan Fernández
 */
public class DisplayStyleHandlerRegistryImpl implements
	DisplayStyleHandlerRegistry {

	public DisplayStyleHandler getDisplayStyleHandler(String className) {
		return _displayStyleHandlers.get(className);
	}

	public DisplayStyleHandler getDisplayStyleHandler(long classNameId) {
		return _displayStyleHandlers.get(PortalUtil.getClassName(classNameId));
	}

	public List<DisplayStyleHandler> getDisplayStyleHandlers() {
		return ListUtil.fromMapValues(_displayStyleHandlers);
	}

	public void register(DisplayStyleHandler displayStyleHandler) {
		_displayStyleHandlers.put(
			displayStyleHandler.getClassName(), displayStyleHandler);
	}

	public void unregister(DisplayStyleHandler displayStyleHandler) {
		_displayStyleHandlers.remove(displayStyleHandler.getClassName());
	}

	private Map<String, DisplayStyleHandler> _displayStyleHandlers =
		new TreeMap<String, DisplayStyleHandler>();

}