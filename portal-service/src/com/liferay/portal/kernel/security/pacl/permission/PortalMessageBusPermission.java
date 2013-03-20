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

package com.liferay.portal.kernel.security.pacl.permission;

import com.liferay.portal.kernel.security.pacl.PACLConstants;

import java.security.BasicPermission;
import java.security.Permission;

/**
 * @author Brian Wing Shun Chan
 */
public class PortalMessageBusPermission extends BasicPermission {

	public static void checkListen(String destinationName) {
		SecurityManager securityManager = System.getSecurityManager();

		if (securityManager == null) {
			return;
		}

		Permission permission = new PortalMessageBusPermission(
			PACLConstants.PORTAL_MESSAGE_BUS_PERMISSION_LISTEN,
			destinationName);

		securityManager.checkPermission(permission);
	}

	public static void checkSend(String destinationName) {
		SecurityManager securityManager = System.getSecurityManager();

		if (securityManager == null) {
			return;
		}

		Permission permission = new PortalMessageBusPermission(
			PACLConstants.PORTAL_MESSAGE_BUS_PERMISSION_SEND, destinationName);

		securityManager.checkPermission(permission);
	}

	public PortalMessageBusPermission(String name, String destinationName) {
		super(name);

		_destinationName = destinationName;
	}

	public String getDestinationName() {
		return _destinationName;
	}

	private String _destinationName;

}