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

package com.liferay.portal.kernel.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Michael C. Han
 * @author Shuyang Zhou
 * @author Marta Medio
 */
public class InetAddressUtil {

	public static InetAddress getInetAddressByName(String domain)
		throws UnknownHostException {

		return getInetAddressProvider().getInetAddressByName(domain);
	}

	public static InetAddressProvider getInetAddressProvider() {
		return _inetAddressProvider;
	}

	public static String getLocalHostName() throws Exception {
		return getInetAddressProvider().getLocalHostName();
	}

	public static InetAddress getLoopbackInetAddress()
		throws UnknownHostException {

		return getInetAddressProvider().getLoopbackInetAddress();
	}

	public static boolean isLocalInetAddress(InetAddress inetAddress) {
		return getInetAddressProvider().isLocalInetAddress(inetAddress);
	}

	public static void setInetAddressProvider(
		InetAddressProvider inetAddressProvider) {

		_inetAddressProvider = inetAddressProvider;
	}

	private static InetAddressProvider _inetAddressProvider;

}