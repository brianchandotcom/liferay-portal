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

package com.liferay.portal.kernel.portlet;

/**
 * @author Brian Wing Shun Chan
 */
public class WindowStateFactory_IW {
	public static WindowStateFactory_IW getInstance() {
		return _instance;
	}

	public javax.portlet.WindowState getWindowState(java.lang.String name) {
		return WindowStateFactory.getWindowState(name);
	}

	public javax.portlet.WindowState getWindowState(java.lang.String name,
		int portletMajorVersion) {
		return WindowStateFactory.getWindowState(name, portletMajorVersion);
	}

	private WindowStateFactory_IW() {
	}

	private static WindowStateFactory_IW _instance = new WindowStateFactory_IW();
}