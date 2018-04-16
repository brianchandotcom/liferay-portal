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

package com.liferay.portlet.internal;

import com.liferay.portal.kernel.model.PortletApp;

/**
 * @author Neil Griffin
 */
public class PortletAppUtil {

	public static int getSpecMajorVersion(PortletApp portletApp) {
		if (portletApp == null) {
			return 2;
		}

		return portletApp.getSpecMajorVersion();
	}

	public static int getSpecMinorVersion(PortletApp portletApp) {
		if (portletApp == null) {
			return 0;
		}

		return portletApp.getSpecMinorVersion();
	}

	public static boolean isPortletSpec2(PortletApp portletApp) {
		if (getSpecMajorVersion(portletApp) == 2) {
			return true;
		}

		return false;
	}

	public static boolean isPortletSpec3(PortletApp portletApp) {
		if (getSpecMajorVersion(portletApp) == 3) {
			return true;
		}

		return false;
	}

}