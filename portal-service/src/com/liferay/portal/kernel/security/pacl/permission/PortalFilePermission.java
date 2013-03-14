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

package com.liferay.portal.kernel.security.pacl.permission;

/**
 * @author Raymond Augé
 */
public class PortalFilePermission {

	public static void checkCopy(String source, String destination) {
		SecurityManager securityManager = System.getSecurityManager();

		if (securityManager == null) {
			return;
		}

		securityManager.checkRead(source);
		securityManager.checkWrite(destination);
	}

	public static void checkDelete(String path) {
		SecurityManager securityManager = System.getSecurityManager();

		if (securityManager == null) {
			return;
		}

		securityManager.checkDelete(path);
	}

	public static void checkMove(String source, String destination) {
		SecurityManager securityManager = System.getSecurityManager();

		if (securityManager == null) {
			return;
		}

		securityManager.checkRead(source);
		securityManager.checkWrite(destination);
		securityManager.checkDelete(source);
		securityManager.checkDelete(destination);
	}

	public static void checkRead(String path) {
		SecurityManager securityManager = System.getSecurityManager();

		if (securityManager == null) {
			return;
		}

		securityManager.checkRead(path);
	}

	public static void checkWrite(String path) {
		SecurityManager securityManager = System.getSecurityManager();

		if (securityManager == null) {
			return;
		}

		securityManager.checkWrite(path);
	}

}