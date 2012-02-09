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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.exception.SystemException;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;

import java.util.Enumeration;

/**
 * @author Michael C. Han
 * @author Shuyang Zhou
 */
public class InetAddressUtil {

	public static String getLocalHostName() {
		return LocalHostNameHolder._localHostName;
	}

	public static InetAddress getLocalInetAddress() throws Exception {
		Enumeration<NetworkInterface> enu1 =
			NetworkInterface.getNetworkInterfaces();

		while (enu1.hasMoreElements()) {
			NetworkInterface networkInterface = enu1.nextElement();

			Enumeration<InetAddress> enu2 = networkInterface.getInetAddresses();

			while (enu2.hasMoreElements()) {
				InetAddress inetAddress = enu2.nextElement();

				if (!inetAddress.isLoopbackAddress() &&
					(inetAddress instanceof Inet4Address)) {

					return inetAddress;
				}
			}
		}

		throw new SystemException("No local internet address");
	}

	private static class LocalHostNameHolder {

		private static final String _localHostName;

		static {
			try {
				_localHostName = getLocalInetAddress().getHostName();
			}
			catch (Exception e) {
				throw new ExceptionInInitializerError(e);
			}
		}
	}

}