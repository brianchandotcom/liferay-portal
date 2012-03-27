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

package com.liferay.portal.security.pacl;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.lang.reflect.Method;

import java.net.InetAddress;
import java.net.UnknownHostException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 */
public class ActivePACLPolicy extends BasePACLPolicy {

	public ActivePACLPolicy(String servletContextName, Properties properties) {
		super(servletContextName, properties);

		initServices();
		initSocketConnectHostsAndPorts();
		initSocketListenPorts();
	}

	public boolean hasAccess(Object object, Method method) {
		Class<?> clazz = object.getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		PACLPolicy paclPolicy = PACLPolicyManager.getPACLPolicy(classLoader);

		if (paclPolicy == this) {
			return true;
		}

		Set<String> services = null;

		if (paclPolicy == null) {
			services = _portalServices;
		}
		else {
			services = _pluginServices.get(paclPolicy.getServletContextName());
		}

		String className = clazz.getName();

		if (services.contains(className)) {
			return true;
		}

		String methodName = method.getName();

		if (services.contains(
				className.concat(StringPool.POUND).concat(methodName))) {

			return true;
		}

		return false;
	}

	public boolean isActive() {
		return true;
	}

	public boolean isSocketConnect(String host, int port) {
		Set<Integer> ports = _socketConnectHostsAndPorts.get(host);

		if (ports == null) {
			return false;
		}

		return ports.contains(port);
	}

	public boolean isSocketListen(int port) {
		return _socketListenPorts.contains(port);
	}

	protected void initServices() {
		Properties properties = getProperties();

		for (Map.Entry<Object, Object> entry : properties.entrySet()) {
			String key = (String)entry.getKey();
			String value = (String)entry.getValue();

			if (!key.startsWith("security-manager-services[")) {
				continue;
			}

			int x = key.indexOf("[");
			int y = key.indexOf("]", x);

			String servicesServletContextName = key.substring(x + 1, y);

			Set<String> services = SetUtil.fromArray(StringUtil.split(value));

			if (servicesServletContextName.equals(
					_PORTAL_SERVLET_CONTEXT_NAME)) {

				_portalServices = services;
			}
			else {
				_pluginServices.put(servicesServletContextName, services);
			}
		}
	}

	protected void initSocketConnectHostsAndPorts() {
		Properties properties = getProperties();

		String socketConnect = properties.getProperty(
			"security-manager-socket-connect");

		String[] socketConnectParts = StringUtil.split(socketConnect);

		for (String socketConnectPart : socketConnectParts) {
			initSocketConnectHostsAndPorts(socketConnectPart);
		}
	}

	protected void initSocketConnectHostsAndPorts(String socketConnectPart) {
		int pos = socketConnectPart.indexOf(StringPool.COLON);

		if (pos == -1) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to determine socket connect host and port from " +
						socketConnectPart +
							" because it is missing a colon delimeter");
			}

			return;
		}

		String host = socketConnectPart.substring(0, pos);

		if (!Validator.isDomain(host)) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Socket connect host " + host + " is not a valid domain");
			}

			return;
		}

		String portString = socketConnectPart.substring(pos + 1);

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
			Set<Integer> ports = _socketConnectHostsAndPorts.get(
				inetAddress.getHostAddress());

			if (ports == null) {
				ports = new HashSet<Integer>();

				if (_log.isDebugEnabled()) {
					_log.debug(
						"Allowing socket connect host " + host + " with IP " +
							inetAddress.getHostAddress() + " on port " + port);
				}

				_socketConnectHostsAndPorts.put(
					inetAddress.getHostAddress(), ports);
			}

			ports.add(port);
		}
	}

	protected void initSocketListenPorts() {
		Properties properties = getProperties();

		String socketListen = properties.getProperty(
			"security-manager-socket-listen");

		String[] socketListenParts = StringUtil.split(socketListen);

		for (String socketListenPart : socketListenParts) {
			initSocketListenPorts(socketListenPart);
		}
	}

	protected void initSocketListenPorts(String socketListenPart) {
		int pos = socketListenPart.indexOf(StringPool.DASH);

		if (pos == -1) {
			if (!Validator.isNumber(socketListenPart)) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Socket listen port " + socketListenPart +
							" is not a number");
				}

				return;
			}

			int port = GetterUtil.getInteger(socketListenPart);

			if (_log.isDebugEnabled()) {
				_log.debug("Allowing socket listen port " + port);
			}

			_socketListenPorts.add(port);
		}
		else {
			String portString1 = socketListenPart.substring(0, pos);
			String portString2 = socketListenPart.substring(pos + 1);

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
						"Socket listen port range " + socketListenPart +
							" is invalid");
				}

				return;
			}

			for (int i = port1; i <= port2; i++) {
				if (_log.isDebugEnabled()) {
					_log.debug("Allowing socket listen port " + i);
				}

				_socketListenPorts.add(i);
			}
		}
	}

	private static final String _PORTAL_SERVLET_CONTEXT_NAME = "portal";

	private static Log _log = LogFactoryUtil.getLog(ActivePACLPolicy.class);

	private Map<String, Set<String>> _pluginServices =
		new HashMap<String, Set<String>>();
	private Set<String> _portalServices;
	private Map<String, Set<Integer>> _socketConnectHostsAndPorts =
		new HashMap<String, Set<Integer>>();
	private Set<Integer> _socketListenPorts = new HashSet<Integer>();

}