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

import com.liferay.portal.kernel.security.pacl.PACLConstants;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.net.SocketPermission;
import java.net.URL;

import java.security.Permission;


/**
 * @author Raymond Augé
 */
public class PortalSocketPermission {

	public static void checkConnect(Http.Options options) {
		String location = options.getLocation();

		String domain = HttpUtil.getDomain(location);
		int port = -1;
		String protocol = HttpUtil.getProtocol(location).toLowerCase();

		checkConnect(domain, port, protocol);
	}

	public static void checkConnect(String location) {
		String domain = HttpUtil.getDomain(location);
		int port = -1;
		String protocol = HttpUtil.getProtocol(location).toLowerCase();

		checkConnect(domain, port, protocol);
	}

	public static void checkConnect(URL url) {
		if (url == null) {
			return;
		}

		String domain = url.getHost();
		int port = url.getPort();
		String protocol = url.getProtocol().toLowerCase();

		checkConnect(domain, port, protocol);
	}

	private static void checkConnect(String domain, int port, String protocol) {
		if (Validator.isNull(domain) ||
			(!protocol.startsWith(Http.HTTPS) &&
			 !protocol.startsWith(Http.HTTP))) {

			return;
		}

		if (port == -1) {
			if (protocol.startsWith(Http.HTTPS)) {
				port = Http.HTTPS_PORT;
			}
			else if (protocol.startsWith(Http.HTTP)) {
				port = Http.HTTP_PORT;
			}
		}

		String location = domain.concat(StringPool.COLON).concat(
			String.valueOf(port));

		SecurityManager securityManager = System.getSecurityManager();

		if (securityManager == null) {
			return;
		}

		Permission permission = new SocketPermission(
			location, PACLConstants.SOCKET_PERMISSION_CONNECT);

		securityManager.checkPermission(permission);
	}

}