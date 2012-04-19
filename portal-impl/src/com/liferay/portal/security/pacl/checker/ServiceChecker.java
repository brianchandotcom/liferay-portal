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

import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.security.pacl.PACLClassLoaderUtil;
import com.liferay.portal.security.pacl.PACLPolicy;
import com.liferay.portal.security.pacl.PACLPolicyManager;
import com.liferay.portal.security.pacl.permission.PortalServicePermission;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import java.security.Permission;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 */
public class ServiceChecker extends BaseChecker {

	public ServiceChecker(PACLPolicy paclPolicy) {
		super(paclPolicy);

		initServices();
	}

	@Override
	public void checkPermission(Permission permission) {
		PortalServicePermission portalServicePermission =
			(PortalServicePermission)permission;

		String name = portalServicePermission.getName();
		Object object = portalServicePermission.getObject();

		if (name.equals("hasService")) {
			Method method = portalServicePermission.getMethod();

			if (!hasService(object, method)) {
				throw new SecurityException("Attempted to invoke " + method);
			}
		}
		else if (name.equals("hasDynamicQuery")) {
			Class<?> implClass = (Class<?>)object;

			if (!hasDynamicQuery(implClass)) {
				throw new SecurityException(
					"Attempted to create a dynamic query for " + implClass);
			}
		}
	}

	public boolean hasDynamicQuery(Class<?> clazz) {
		ClassLoader classLoader = PACLClassLoaderUtil.getClassLoader(clazz);

		PACLPolicy paclPolicy = PACLPolicyManager.getPACLPolicy(
			classLoader);

		if (paclPolicy == getPACLPolicy()) {
			return true;
		}

		Set<String> services = getServices(paclPolicy);

		String className = getInterfaceName(clazz.getName());

		if (services.contains(className)) {
			return true;
		}

		return false;
	}

	public boolean hasService(Object object, Method method) {
		Class<?> clazz = object.getClass();

		if (Proxy.isProxyClass(clazz)) {
			Class<?>[] interfaces = clazz.getInterfaces();

			if (interfaces.length == 0) {
				return false;
			}

			clazz = interfaces[0];
		}

		ClassLoader classLoader = PACLClassLoaderUtil.getClassLoader(clazz);

		PACLPolicy paclPolicy = PACLPolicyManager.getPACLPolicy(
			classLoader);

		if (paclPolicy == getPACLPolicy()) {
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

	private Map<String, Set<String>> _pluginServices =
		new HashMap<String, Set<String>>();
	private Set<String> _portalServices;

}