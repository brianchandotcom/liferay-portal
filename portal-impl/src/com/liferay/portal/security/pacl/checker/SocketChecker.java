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

package com.liferay.portal.security.pacl.checker;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.pacl.PACLPolicy;

import java.net.InetAddress;
import java.net.UnknownHostException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 */
public class SocketChecker extends BaseChecker {

	public SocketChecker(PACLPolicy paclPolicy) {
		super(paclPolicy);

		initConnectHostsAndPorts();
		initListenPorts();
	}

	public boolean hasConnect(String host, int port) {
		Set<Integer> ports = _connectHostsAndPorts.get(host);

		if (ports == null) {
			return false;
		}

		return ports.contains(port);
	}

	public boolean hasListen(int port) {
		return _listenPorts.contains(port);
	}

	protected void initConnectHostsAndPorts() {
		String[] connectParts = getPropertyArray(
			"security-manager-sockets-connect");

		for (String connectPart : connectParts) {
			initConnectHostsAndPorts(connectPart);
		}
	}

	protected void initConnectHostsAndPorts(String connectPart) {
		int pos = connectPart.indexOf(StringPool.COLON);

		if (pos == -1) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to determine socket connect host and port from " +
						connectPart +
							" because it is missing a colon delimeter");
			}

			return;
		}

		String host = connectPart.substring(0, pos);

		if (!Validator.isDomain(host)) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Socket connect host " + host + " is not a valid domain");
			}

			return;
		}

		String portString = connectPart.substring(pos + 1);

		int port = GetterUtil.getInteger(portString);

		if (port <= 0) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Socket connect port " + portString +
						" is less than or equal to 0");
			}

			return;
		}

		InetAddress[] inetAddresses = null;

		try {
			inetAddresses = InetAddress.getAllByName(host);
		}
		catch (UnknownHostException uhe) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to resolve host " + host);
			}

			return;
		}

		for (InetAddress inetAddress : inetAddresses) {
			Set<Integer> ports = _connectHostsAndPorts.get(
				inetAddress.getHostAddress());

			if (ports == null) {
				ports = new HashSet<Integer>();

				if (_log.isDebugEnabled()) {
					_log.debug(
						"Allowing socket connect host " + host + " with IP " +
							inetAddress.getHostAddress() + " on port " + port);
				}

				_connectHostsAndPorts.put(inetAddress.getHostAddress(), ports);
			}

			ports.add(port);
		}
	}

	protected void initListenPorts() {
		String[] listenParts = getPropertyArray(
			"security-manager-sockets-listen");

		for (String listenPart : listenParts) {
			initListenPorts(listenPart);
		}
	}

	protected void initListenPorts(String listenPart) {
		int pos = listenPart.indexOf(StringPool.DASH);

		if (pos == -1) {
			if (!Validator.isNumber(listenPart)) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Socket listen port " + listenPart +
							" is not a number");
				}

				return;
			}

			int port = GetterUtil.getInteger(listenPart);

			if (_log.isDebugEnabled()) {
				_log.debug("Allowing socket listen port " + port);
			}

			_listenPorts.add(port);
		}
		else {
			String portString1 = listenPart.substring(0, pos);
			String portString2 = listenPart.substring(pos + 1);

			if (!Validator.isNumber(portString1)) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Socket listen port " + portString1 +
							" is not a number");
				}

				return;
			}

			if (!Validator.isNumber(portString2)) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Socket listen port " + portString2 +
							" is not a number");
				}

				return;
			}

			int port1 = GetterUtil.getInteger(portString1);
			int port2 = GetterUtil.getInteger(portString2);

			if (port1 >= port2) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Socket listen port range " + listenPart +
							" is invalid");
				}

				return;
			}

			for (int i = port1; i <= port2; i++) {
				if (_log.isDebugEnabled()) {
					_log.debug("Allowing socket listen port " + i);
				}

				_listenPorts.add(i);
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(SocketChecker.class);

	private Map<String, Set<Integer>> _connectHostsAndPorts =
		new HashMap<String, Set<Integer>>();
	private Set<Integer> _listenPorts = new HashSet<Integer>();

}