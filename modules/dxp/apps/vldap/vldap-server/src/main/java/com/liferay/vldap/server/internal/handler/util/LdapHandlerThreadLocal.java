/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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