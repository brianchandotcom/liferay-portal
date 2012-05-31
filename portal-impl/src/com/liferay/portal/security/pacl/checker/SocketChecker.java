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

import java.net.InetAddress;
import java.net.UnknownHostException;

import java.security.Permission;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 */
public class SocketChecker extends BaseChecker {

	public void afterPropertiesSet() {
		initAcceptHostsAndPorts();
		initConnectHostsAndPorts();
		initListenPorts();
	}

	public void checkPermission(Permission permission) {
		String actions = permission.getActions();

		String name = permission.getName();

		int pos = name.indexOf(StringPool.COLON);

		String host = "localhost";

		if (pos != -1) {
			host = name.substring(0, pos);
		}

		int port = GetterUtil.getInteger(name.substring(pos + 1));

		// resolve

		if (port == -1) {
			if (_log.isDebugEnabled()) {
				_log.debug("Always allow resolving of host " + host);
			}

			return;
		}

		if (actions.contains(SOCKET_PERMISSION_ACCEPT)) {
			if (!hasAccept(host, port)) {
				throwSecurityException(
					_log,
					"Attempted to accept from host " + host + " on port " +
						port);
			}
		}
		else if (actions.contains(SOCKET_PERMISSION_CONNECT)) {
			if (!hasConnect(host, port)) {
				throwSecurityException(
					_log,
					"Attempted to connect to host " + host + " on port " +
						port);
			}
		}
		else if (actions.contains(SOCKET_PERMISSION_LISTEN)) {
			if (!hasListen(port)) {
				throwSecurityException(
					_log, "Attempted to listen on port " + port);
			}
		}
	}

	protected boolean hasAccept(String host, int port) {
		Set<Integer> ports = _acceptHostsAndPorts.get(host);

		if (ports == null) {
			return false;
		}

		return ports.contains(port);
	}

	protected boolean hasConnect(String host, int port) {
		Set<Integer> ports = _connectHostsAndPorts.get(host);

		if (ports == null) {
			return false;
		}

		return ports.contains(port);
	}

	protected boolean hasListen(int port) {
		return _listenPorts.contains(port);
	}

	protected void initAcceptHostsAndPorts() {
		String[] networkParts = getPropertyArray(
			"security-manager-sockets-accept");

		for (String networkPart : networkParts) {
			initHostsAndPorts(networkPart, true);
		}
	}

	protected void initConnectHostsAndPorts() {
		String[] networkParts = getPropertyArray(
			"security-manager-sockets-connect");

		for (String networkPart : networkParts) {
			initHostsAndPorts(networkPart, false);
		}
	}

	protected void initHostsAndPorts(String networkPart, boolean accept) {
		String action = "accept";

		if (!accept) {
			action = "connect";
		}

		int pos = networkPart.indexOf(StringPool.COLON);

		if (pos == -1) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to determine socket " + action +
						" host and port from " + networkPart +
							" because it is missing a colon delimeter");
			}

			return;
		}

		String host = networkPart.substring(0, pos);

		if (!Validator.isDomain(host)) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Socket " + action + " host " + host +
						" is not a valid domain");
			}

			return;
		}

		String portString = networkPart.substring(pos + 1);

		int port = GetterUtil.getInteger(portString);

		if (port <= 0) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Socket " + action + " port " + portString +
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

		Map<String, Set<Integer>> hostsAndPorts = _acceptHostsAndPorts;

		if (!accept) {
			hostsAndPorts = _connectHostsAndPorts;
		}

		for (InetAddress inetAddress : inetAddresses) {
			Set<Integer> ports = hostsAndPorts.get(
				inetAddress.getHostAddress());

			if (ports == null) {
				ports = new HashSet<Integer>();

				if (_log.isDebugEnabled()) {
					_log.debug(
						"Allowing socket " + action + " host " + host +
							" with IP " + inetAddress.getHostAddress() +
								" on port " + port);
				}

				hostsAndPorts.put(inetAddress.getHostAddress(), ports);
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

	private Map<String, Set<Integer>> _acceptHostsAndPorts =
		new HashMap<String, Set<Integer>>();
	private Map<String, Set<Integer>> _connectHostsAndPorts =
		new HashMap<String, Set<Integer>>();
	private Set<Integer> _listenPorts = new HashSet<Integer>();

}