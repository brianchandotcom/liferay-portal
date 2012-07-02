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
import com.liferay.portal.kernel.security.pacl.permission.PortalServicePermission;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.security.pacl.PACLClassLoaderUtil;
import com.liferay.portal.security.pacl.PACLPolicy;
import com.liferay.portal.security.pacl.PACLPolicyManager;

import java.lang.reflect.Method;

import java.security.Permission;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 */
public class PortalServiceChecker extends BaseChecker {

	public void afterPropertiesSet() {
		initServices();
	}

	public void checkPermission(Permission permission) {
		PortalServicePermission portalServicePermission =
			(PortalServicePermission)permission;

		String name = portalServicePermission.getName();
		Object object = portalServicePermission.getObject();

		if (name.equals(PORTAL_SERVICE_PERMISSION_DYNAMIC_QUERY)) {
			Class<?> implClass = (Class<?>)object;

			if (!hasDynamicQuery(implClass)) {
				throwSecurityException(
					_log,
					"Attempted to create a dynamic query for " + implClass);
			}
		}
	}

	public boolean hasService(
		Object object, Method method, Object[] arguments) {

		Class<?> clazz = object.getClass();

		if (ProxyUtil.isProxyClass(clazz)) {
			Class<?>[] interfaces = clazz.getInterfaces();

			if (interfaces.length == 0) {
				return false;
			}

			clazz = interfaces[0];
		}

		ClassLoader classLoader = PACLClassLoaderUtil.getClassLoader(clazz);

		PACLPolicy paclPolicy = PACLPolicyManager.getPACLPolicy(classLoader);

		if (paclPolicy == getPACLPolicy()) {
			return true;
		}

		Set<String> services = getServices(paclPolicy);

		String className = getInterfaceName(clazz.getName());

		if (services.contains(className)) {
			return true;
		}

		String methodName = method.getName();

		if (methodName.equals("invokeMethod")) {
			methodName = (String)arguments[0];
		}

		if (services.contains(
				className.concat(StringPool.POUND).concat(methodName))) {

			return true;
		}

		return false;
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

	protected boolean hasDynamicQuery(Class<?> clazz) {
		ClassLoader classLoader = PACLClassLoaderUtil.getClassLoader(clazz);

		PACLPolicy paclPolicy = PACLPolicyManager.getPACLPolicy(classLoader);

		if (paclPolicy == getPACLPolicy()) {
			return true;
		}

		/*Set<String> services = getServices(paclPolicy);

		String className = getInterfaceName(clazz.getName());

		if (services.contains(className)) {
			return true;
		}*/

		return false;
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

				touchServices(_portalServices);
			}
			else {
				_pluginServices.put(servicesServletContextName, services);
			}
		}
	}

	protected void touchService(String service) {
		String className = service;

		if (!className.contains(".service.")) {
			return;
		}

		String methodName = null;

		if (className.contains(".service.persistence.") &&
			(className.endsWith("Persistence") ||
			 className.contains("Persistence#"))) {

			methodName = "getPersistence";
		}
		else if (className.endsWith("Service") ||
				 className.contains("Service#")) {

			methodName = "getService";
		}
		else {
			_log.error("Invalid service " + service);

			return;
		}

		int pos = className.indexOf(StringPool.POUND);

		if (pos != -1) {
			className = className.substring(0, pos);
		}

		if (className.endsWith("Persistence")) {
			className = className.substring(0, className.length() - 11);
		}

		className += "Util";

		if (_log.isDebugEnabled()) {
			_log.debug("Invoking " + className + "#" + methodName);
		}

		// Invoke method since it will attempt to access declared members

		ClassLoader classLoader = getCommonClassLoader();

		if (ServerDetector.isGeronimo() || ServerDetector.isJBoss()) {
			classLoader = getPortalClassLoader();
		}

		try {
			Class<?> clazz = classLoader.loadClass(className);

			Method method = clazz.getMethod(methodName);

			method.invoke(clazz);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	protected void touchServices(Set<String> services) {
		for (String service : services) {
			touchService(service);
		}
	}

	private static final String _PORTAL_SERVLET_CONTEXT_NAME = "portal";

	private static Log _log = LogFactoryUtil.getLog(PortalServiceChecker.class);

	private Map<String, Set<String>> _pluginServices =
		new HashMap<String, Set<String>>();
	private Set<String> _portalServices = Collections.emptySet();

}