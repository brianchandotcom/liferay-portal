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

package com.liferay.portal.kernel.security.pacl.permission;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.net.URL;

/**
 * @author Raymond Augé
 */
public class PortalSocketPermission {

	public static void checkConnect(Http.Options options) {
		checkConnect(options.getLocation());
	}

	public static void checkConnect(String location) {
		String domainAndPort = HttpUtil.getDomain(location);

		String[] domainAndPortArray = domainAndPort.split(StringPool.COLON);

		String domain = domainAndPortArray[0];

		int port = -1;

		if (domainAndPortArray.length > 1) {
			port = GetterUtil.getInteger(domainAndPortArray[1]);
		}

		String protocol = HttpUtil.getProtocol(location);

		checkConnect(domain, port, protocol);
	}

	public static void checkConnect(URL url) {
		if (url == null) {
			return;
		}

		String domain = url.getHost();
		int port = url.getPort();
		String protocol = url.getProtocol();

		checkConnect(domain, port, protocol);
	}

	public interface PACL {

		public void checkPermission(String host, String action);

	}

	private static void checkConnect(String domain, int port, String protocol) {
		if (Validator.isNull(domain) ||
			(!protocol.startsWith(Http.HTTPS) &&
			 !protocol.startsWith(Http.HTTP))) {

			return;
		}

		if (port == -1) {
			protocol = StringUtil.toLowerCase(protocol);

			if (protocol.startsWith(Http.HTTPS)) {
				port = Http.HTTPS_PORT;
			}
			else if (protocol.startsWith(Http.HTTP)) {
				port = Http.HTTP_PORT;
			}
		}

		String location = domain.concat(StringPool.COLON).concat(
			String.valueOf(port));

		_pacl.checkPermission(location, "connect");
	}

	private static final PACL _pacl = new NoPACL();

	private static class NoPACL implements PACL {

		@Override
		public void checkPermission(String host, String action) {
		}

	}

}