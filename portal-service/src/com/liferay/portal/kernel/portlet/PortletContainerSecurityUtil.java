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
 */
public class PortletContainerSecurityUtil {

	public static Set<String> getPortletAddDefaultResourceCheckWhitelist() {
		return getPortletContainerSecurity().
			getPortletAddDefaultResourceCheckWhitelist();
	}

	public static Set<String>
		getPortletAddDefaultResourceCheckWhitelistActions() {

		return getPortletContainerSecurity().
			getPortletAddDefaultResourceCheckWhitelistActions();
	}

	public static PortletContainerSecurity getPortletContainerSecurity() {
		PortalRuntimePermission.checkGetBeanProperty(
			PortletContainerSecurity.class);

		return _portletContainerSecurity;
	}

	public static Set<String> resetPortletAddDefaultResourceCheckWhitelist() {
		return getPortletContainerSecurity().
			resetPortletAddDefaultResourceCheckWhitelist();
	}

	public static Set<String>
		resetPortletAddDefaultResourceCheckWhitelistActions() {

		return getPortletContainerSecurity().
			resetPortletAddDefaultResourceCheckWhitelistActions();
	}

	public void setPortletContainerSecurity(
		PortletContainerSecurity portletContainerSecurity) {

		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_portletContainerSecurity = portletContainerSecurity;
	}

	private static PortletContainerSecurity _portletContainerSecurity;

}