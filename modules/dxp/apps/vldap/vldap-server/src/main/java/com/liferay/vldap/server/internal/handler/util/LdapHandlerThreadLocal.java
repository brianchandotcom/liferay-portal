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

package com.liferay.vldap.server.internal.handler.util;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.AutoResetThreadLocal;
import com.liferay.portal.kernel.util.PortalUtil;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 */
public class LdapHandlerThreadLocal {

	public static void clearSocketAddress() {
		_socketAddress.remove();
	}

	public static boolean isHostAllowed(String[] allowList) {
		if (allowList.length == 0) {
			return true;
		}

		SocketAddress socketAddress = _socketAddress.get();

		if (!(socketAddress instanceof InetSocketAddress)) {
			return false;
		}

		InetSocketAddress inetSocketAddress = (InetSocketAddress)socketAddress;

		InetAddress inetAddress = inetSocketAddress.getAddress();

		String hostAddress = inetAddress.getHostAddress();

		if (ArrayUtil.contains(allowList, hostAddress)) {
			return true;
		}

		Set<String> computerAddresses = PortalUtil.getComputerAddresses();

		if (computerAddresses.contains(hostAddress) &&
			ArrayUtil.contains(allowList, _SERVER_IP)) {

			return true;
		}

		return false;
	}

	public static void setSocketAddress(SocketAddress socketAddress) {
		_socketAddress.set(socketAddress);
	}

	private static final String _SERVER_IP = "SERVER_IP";

	private static final ThreadLocal<SocketAddress> _socketAddress =
		new AutoResetThreadLocal<>(
			LdapHandlerThreadLocal.class + "._socketAddress");

}