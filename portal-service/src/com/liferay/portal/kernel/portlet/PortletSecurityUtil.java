/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

import java.util.Set;

/**
 * @author Tomas Polesovsky
 * @author Raymond Augé
 */
public class PortletSecurityUtil {

	public static PortletSecurity getPortletSecurity() {
		PortalRuntimePermission.checkGetBeanProperty(PortletSecurity.class);

		return _portletSecurity;
	}

	public static Set<String> getWhitelist() {
		return getPortletSecurity().getWhitelist();
	}

	public static Set<String> getWhitelistActions() {
		return getPortletSecurity().getWhitelistActions();
	}

	public static Set<String> resetWhitelist() {
		return getPortletSecurity().resetWhitelist();
	}

	public static Set<String> resetWhitelistActions() {
		return getPortletSecurity().resetWhitelistActions();
	}

	public void setPortletSecurity(PortletSecurity portletSecurity) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_portletSecurity = portletSecurity;
	}

	private static PortletSecurity _portletSecurity;

}