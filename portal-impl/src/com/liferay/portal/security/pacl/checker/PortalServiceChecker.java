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
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.security.pacl.PACLPolicy;
import com.liferay.portal.security.pacl.PACLPolicyManager;
import com.liferay.portal.util.ClassLoaderUtil;

import java.lang.reflect.Method;

import java.security.Permission;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class PortalServiceChecker extends BaseChecker {

	public void afterPropertiesSet() {
		initServices();
	}

	@Override
	public AuthorizationProperty generateAuthorizationProperty(
		Object... arguments) {

		if ((arguments == null) || (arguments.length == 0)) {
			return null;
		}

		Object object = null;
		Method method = null;

		if (arguments[0] instanceof Permission) {
			PortalServicePermission portalServicePermission =
				(PortalServicePermission)arguments[0];

			object = portalServicePermission.getObject();
			method = portalServicePermission.getMethod();
		}
		else {
			object = arguments[0];
			method = (Method)arguments[1];
		}

		Class<?> clazz = getClass(object);

		if (clazz == null) {
			return null;
		}

		ClassLoader classLoader = ClassLoaderUtil.getClassLoader(clazz);

		PACLPolicy paclPolicy = PACLPolicyManager.getPACLPolicy(classLoader);

		String filter = "[portal]";

		if (paclPolicy != null) {
			filter =
				StringPool.OPEN_BRACKET + paclPolicy.getServletContextName() +
					StringPool.CLOSE_BRACKET;
		}

		String className = getInterfaceName(clazz.getName());

		String methodName = method.getName();

		if (methodName.equals("invokeMethod")) {
			methodName = (String)arguments[0];
		}

		AuthorizationProperty authorizationProperty =
			new AuthorizationProperty();

		authorizationProperty.setKey("security-manager-services" + filter);
		authorizationProperty.setValue(
			className + StringPool.POUND + methodName);

		return authorizationProperty;
	}

	public boolean hasService(
		Object object, Method method, Object[] arguments) {

		Class<?> clazz = getClass(object);

		if (clazz == null) {
			return false;
		}

		ClassLoader classLoader = ClassLoaderUtil.getClassLoader(clazz);

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

	public boolean implies(Permission permission) {
		PortalServicePermission portalServicePermission =
			(PortalServicePermission)permission;

		String name = portalServicePermission.getName();
		Object object = portalServicePermission.getObject();

		if (name.equals(PORTAL_SERVICE_PERMISSION_DYNAMIC_QUERY)) {
			Class<?> implClass = (Class<?>)object;

			if (!hasDynamicQuery(implClass)) {
				logSecurityException(
					_log,
					"Attempted to create a dynamic query for " + implClass);

				return false;
			}
		}

		return true;
	}

	protected Class<?> getClass(Object object) {
		Class<?> clazz = object.getClass();

		if (ProxyUtil.isProxyClass(clazz)) {
			Class<?>[] interfaces = clazz.getInterfaces();

			if (interfaces.length == 0) {
				return null;
			}

			clazz = interfaces[0];
		}

		return clazz;
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
		ClassLoader classLoader = ClassLoaderUtil.getClassLoader(clazz);

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
			}
			else {
				_pluginServices.put(servicesServletContextName, services);
			}
		}
	}

	private static final String _PORTAL_SERVLET_CONTEXT_NAME = "portal";

	private static Log _log = LogFactoryUtil.getLog(PortalServiceChecker.class);

	private Map<String, Set<String>> _pluginServices =
		new HashMap<String, Set<String>>();
	private Set<String> _portalServices = Collections.emptySet();

}