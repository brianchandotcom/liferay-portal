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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Shuyang Zhou
 */
public class IPDetector {

	public static boolean isPrefersV4() {
		if (_prefersV4 != null) {
			return _prefersV4.booleanValue();
		}

		_prefersV4 = Boolean.valueOf(
			System.getProperty("java.net.preferIPv4Stack"));

		return _prefersV4.booleanValue();
	}

	public static boolean isPrefersV6() {
		if (_prefersV6 != null) {
			return _prefersV6.booleanValue();
		}

		_prefersV6 = Boolean.valueOf(
			System.getProperty("java.net.preferIPv6Stack"));

		return _prefersV6.booleanValue();
	}

	public static boolean isSupportsV6() {
		if (_suppportsV6 != null) {
			return _suppportsV6.booleanValue();
		}

		try {
			InetAddress[] inetAddresses = InetAddress.getAllByName("localhost");

			for (InetAddress inetAddress : inetAddresses) {
				String hostAddress = inetAddress.getHostAddress();

				if (hostAddress.contains(":")) {
					_suppportsV6 = Boolean.TRUE;

					break;
				}
			}
		}
		catch (UnknownHostException uhe) {
			_log.error(uhe, uhe);
		}

		if (_suppportsV6 == null) {
			_suppportsV6 = Boolean.FALSE;
		}

		return _suppportsV6.booleanValue();
	}

	private static final Log _log = LogFactoryUtil.getLog(IPDetector.class);

	private static Boolean _prefersV4;
	private static Boolean _prefersV6;
	private static Boolean _suppportsV6;

}