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
public class PortletModeFactory_IW {
	public static PortletModeFactory_IW getInstance() {
		return _instance;
	}

	public javax.portlet.PortletMode getPortletMode(java.lang.String name) {
		return PortletModeFactory.getPortletMode(name);
	}

	public javax.portlet.PortletMode getPortletMode(java.lang.String name,
		int portletMajorVersion) {
		return PortletModeFactory.getPortletMode(name, portletMajorVersion);
	}

	private PortletModeFactory_IW() {
	}

	private static PortletModeFactory_IW _instance = new PortletModeFactory_IW();
}