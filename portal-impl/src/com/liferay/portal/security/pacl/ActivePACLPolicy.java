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
import com.liferay.portal.kernel.servlet.WebDirDetector;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortalUtil;

import java.io.FilePermission;

import java.lang.reflect.Method;

import java.net.InetAddress;
import java.net.UnknownHostException;

import java.security.Permission;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Brian Wing Shun Chan
 */
public class ActivePACLPolicy extends BasePACLPolicy {

	public ActivePACLPolicy(
		String servletContextName, ClassLoader classLoader,
		Properties properties) {

		super(servletContextName, properties);

		_properties = properties;

		_rootDir = WebDirDetector.getRootDir(classLoader);

		initFiles();
		initHookServices();
		initServices();
		initSocketConnectHostsAndPorts();
		initSocketListenPorts();
	}

	public boolean hasDynamicQueryPermission(Class<?> clazz) {
		ClassLoader classLoader = clazz.getClassLoader();

		PACLPolicy paclPolicy = PACLPolicyManager.getPACLPolicy(classLoader);

		if (paclPolicy == this) {
			return true;
		}

		Set<String> services = getServices(paclPolicy);

		String className = getInterfaceName(clazz.getName());

		if (services.contains(className)) {
			return true;
		}

		return false;
	}

	public boolean hasFileDeletePermission(String fileName) {
		Permission permission = new FilePermission(
			fileName, _FILE_PERMISSION_DELETE);

		for (Permission deleteFilePermission : _deleteFilePermissions) {
			if (deleteFilePermission.implies(permission)) {
				return true;
			}
		}

		return false;
	}

	public boolean hasFileExecutePermission(String fileName) {
		Permission permission = new FilePermission(
			fileName, _FILE_PERMISSION_EXECUTE);

		for (Permission executeFilePermission : _executeFilePermissions) {
			if (executeFilePermission.implies(permission)) {
				return true;
			}
		}

		return false;
	}

	public boolean hasFileReadPermission(String fileName) {
		Permission permission = new FilePermission(
			fileName, _FILE_PERMISSION_READ);

		for (Permission readFilePermission : _readFilePermissions) {
			if (readFilePermission.implies(permission)) {
				return true;
			}
		}

		return false;
	}

	public boolean hasFileWritePermission(String fileName) {
		Permission permission = new FilePermission(
			fileName, _FILE_PERMISSION_WRITE);

		for (Permission writeFilePermission : _writeFilePermissions) {
			if (writeFilePermission.implies(permission)) {
				return true;
			}
		}

		return false;
	}

	public boolean hasHookService(String className) {
		return _hookServices.contains(className);
	}

	public boolean hasServicePermission(Object object, Method method) {
		Class<?> clazz = object.getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		PACLPolicy paclPolicy = PACLPolicyManager.getPACLPolicy(classLoader);

		if (paclPolicy == this) {
			return true;
		}

		Set<String> services = getServices(paclPolicy);

		String className = getInterfaceName(clazz.getName());

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

	public boolean hasSocketConnectPermission(String host, int port) {
		Set<Integer> ports = _socketConnectHostsAndPorts.get(host);

		if (ports == null) {
			return false;
		}

		return ports.contains(port);
	}

	public boolean hasSocketListenPermission(int port) {
		return _socketListenPorts.contains(port);
	}

	public boolean isActive() {
		return true;
	}

	protected void addFilePermission(
		List<Permission> permissions, String path, String action) {

		if (_log.isDebugEnabled()) {
			_log.debug("Allowing " + action + " on " + path);
		}

		Permission permission = new FilePermission(path, action);

		permissions.add(permission);
	}

	protected List<Permission> getFilePermissions(String key, String action) {
		List<Permission> permissions = new CopyOnWriteArrayList<Permission>();

		String value = _properties.getProperty(key);

		String[] paths = StringUtil.split(value);

		if (value.contains("${comma}")) {
			for (int i = 0; i < paths.length; i++) {
				paths[i] = StringUtil.replace(
					paths[i], "${comma}", StringPool.COMMA);
			}
		}

		for (String path : paths) {
			addFilePermission(permissions, path, action);
		}

		if (!action.equals(_FILE_PERMISSION_READ)) {
			return permissions;
		}

		String catalinaHome = System.getProperty("catalina.home") + "/";

		String portalWebDir = PortalUtil.getPortalWebDir();

		String[] defaultPaths = {

			// Plugin

			_rootDir + "-",

			// Plugin JSPC

			catalinaHome + "work/Catalina/localhost/" +
				getServletContextName() + "/-",

			// Portal taglibs

			portalWebDir + "html/common/-", portalWebDir + "html/taglib/-",
			portalWebDir + "localhost/html/taglib/-",

			// Portal JSPC

			portalWebDir + "WEB-INF/classes/org/apache/jasper/-",
			portalWebDir + "WEB-INF/classes/org/apache/tomcat/-",
			catalinaHome + "work/Catalina/localhost/_",
			catalinaHome + "work/Catalina/localhost/_/-"
		};

		for (String defaultPath : defaultPaths) {
			addFilePermission(permissions, defaultPath, action);
		}

		return permissions;
	}

	protected String getInterfaceName(String className) {
		int pos = className.indexOf(".impl.");

		if (pos != -1) {
			className =
				className.substring(0, pos + 1) + className.substring(pos + 6);
		}

		if (className.endsWith("Impl")) {
			className = className.substring(0, className.length() - 4);
		}

		return className;
	}

	protected Set<String> getServices(PACLPolicy paclPolicy) {
		Set<String> services = null;

		if (paclPolicy == null) {
			services = _portalServices;
		}
		else {
			services = _pluginServices.get(paclPolicy.getServletContextName());
		}

		return services;
	}

	protected void initFiles() {
		_deleteFilePermissions = getFilePermissions(
			"security-manager-files-delete", _FILE_PERMISSION_DELETE);
		_executeFilePermissions = getFilePermissions(
			"security-manager-files-execute", _FILE_PERMISSION_EXECUTE);
		_readFilePermissions = getFilePermissions(
			"security-manager-files-read", _FILE_PERMISSION_READ);
		_writeFilePermissions = getFilePermissions(
			"security-manager-files-write", _FILE_PERMISSION_WRITE);
	}

	protected void initHookServices() {
		_hookServices = SetUtil.fromArray(
			StringUtil.split(
				_properties.getProperty("security-manager-hook-services")));
	}

	protected void initServices() {
		for (Map.Entry<Object, Object> entry : _properties.entrySet()) {
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
		String socketConnect = _properties.getProperty(
			"security-manager-sockets-connect");

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
		String socketListen = _properties.getProperty(
			"security-manager-sockets-listen");

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

	private static final String _FILE_PERMISSION_DELETE = "delete";

	private static final String _FILE_PERMISSION_EXECUTE = "execute";

	private static final String _FILE_PERMISSION_READ = "read";

	private static final String _FILE_PERMISSION_WRITE = "write";

	private static final String _PORTAL_SERVLET_CONTEXT_NAME = "portal";

	private static Log _log = LogFactoryUtil.getLog(ActivePACLPolicy.class);

	private List<Permission> _deleteFilePermissions;
	private List<Permission> _executeFilePermissions;
	private Set<String> _hookServices = new HashSet<String>();
	private Map<String, Set<String>> _pluginServices =
		new HashMap<String, Set<String>>();
	private Set<String> _portalServices;
	private Properties _properties;
	private List<Permission> _readFilePermissions;
	private String _rootDir;
	private Map<String, Set<Integer>> _socketConnectHostsAndPorts =
		new HashMap<String, Set<Integer>>();
	private Set<Integer> _socketListenPorts = new HashSet<Integer>();
	private List<Permission> _writeFilePermissions;

}