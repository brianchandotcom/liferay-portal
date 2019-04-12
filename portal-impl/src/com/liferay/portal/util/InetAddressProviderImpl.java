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

package com.liferay.portal.util;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.InetAddressProvider;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;

import java.util.Enumeration;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Michael C. Han
 * @author Shuyang Zhou
 * @author Marta Medio
 */
public class InetAddressProviderImpl implements InetAddressProvider {

	public InetAddress getInetAddressByName(String domain)
		throws UnknownHostException {

		InetAddress inetAddress = null;

		int i = _atomicInteger.decrementAndGet();

		try {
			if (i > 0) {
				Future<InetAddress> result = _executorService.submit(
					() -> InetAddress.getByName(domain));

				inetAddress = result.get(
					PropsValues.DNS_SECURITY_ADDRESS_TIMEOUT_SECONDS,
					TimeUnit.SECONDS);
			}
			else {
				_log.error(
					"Thread limit exceeded to determine address for host: " +
						domain);
			}
		}
		catch (ExecutionException | InterruptedException | TimeoutException e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}

			throw new UnknownHostException("Unable to resolve URL: " + domain);
		}
		finally {
			_atomicInteger.incrementAndGet();
		}

		return inetAddress;
	}

	public String getLocalHostName() throws Exception {
		String localHostName;

		try {
			InetAddress inetAddress = _getLocalInetAddress();

			localHostName = inetAddress.getHostName();
		}
		catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}

		return localHostName;
	}

	public InetAddress getLoopbackInetAddress() throws UnknownHostException {
		return InetAddress.getByName("127.0.0.1");
	}

	public boolean isLocalInetAddress(InetAddress inetAddress) {
		if (inetAddress.isAnyLocalAddress() ||
			inetAddress.isLinkLocalAddress() ||
			inetAddress.isLoopbackAddress() ||
			inetAddress.isSiteLocalAddress()) {

			return true;
		}

		return false;
	}

	private InetAddress _getLocalInetAddress() throws Exception {
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

	private static final Log _log = LogFactoryUtil.getLog(
		InetAddressProviderImpl.class);

	private static final AtomicInteger _atomicInteger = new AtomicInteger(
		PropsValues.DNS_SECURITY_THREAD_LIMIT);
	private static final ExecutorService _executorService =
		Executors.newFixedThreadPool(PropsValues.DNS_SECURITY_THREAD_LIMIT);

}