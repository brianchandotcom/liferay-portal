/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.security.pacl.permission.PortalServicePermission;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.security.Permission;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import sun.reflect.Reflection;

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

		if ((arguments == null) || (arguments.length != 1) ||
			!(arguments[0] instanceof Permission)) {

			return null;
		}

		AuthorizationProperty authorizationProperty =
			new AuthorizationProperty();

		StringBundler sb = new StringBundler(4);

		sb.append("security-manager-services");
		sb.append(StringPool.OPEN_BRACKET);

		PortalServicePermission portalServicePermission =
			(PortalServicePermission)arguments[0];

		sb.append(portalServicePermission.getServletContextName());

		sb.append(StringPool.CLOSE_BRACKET);

		authorizationProperty.setKey(sb.toString());

		authorizationProperty.setValue(
			portalServicePermission.getClassName() + StringPool.POUND +
				portalServicePermission.getMethodName());

		return authorizationProperty;
	}

	public boolean implies(Permission permission) {
		PortalServicePermission portalServicePermission =
			(PortalServicePermission)permission;

		String name = portalServicePermission.getShortName();

		if (name.equals(PORTAL_SERVICE_PERMISSION_SERVICE)) {
			if (!hasService(
					portalServicePermission.getServletContextName(),
					portalServicePermission.getClassName(),
					portalServicePermission.getMethodName(), permission)) {

				return false;
			}
		}

		return true;
	}

	protected Set<String> getServices(String servletContextName) {
		Set<String> services = null;

		if (servletContextName.equals("portal")) {
			services = _portalServices;
		}
		else {
			services = _pluginServices.get(servletContextName);

			if (services == null) {
				return Collections.emptySet();
			}
		}

		return services;
	}

	protected boolean hasService(
		String servletContextName, String className, String methodName,
		Permission permission) {

		int stackIndex = getStackIndex(15, 14);

		Class<?> callerClass = Reflection.getCallerClass(stackIndex);

		if (isTrustedCaller(callerClass, permission)) {
			callerClass = Reflection.getCallerClass(stackIndex + 1);

			if (isTrustedCaller(callerClass, permission)) {
				return true;
			}
		}

		Set<String> services = getServices(servletContextName);

		if (services.contains(className)) {
			return true;
		}

		if (Validator.isNull(methodName)) {
			return false;
		}

		if (services.contains(
				className.concat(StringPool.POUND).concat(methodName))) {

			return true;
		}

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

	private Map<String, Set<String>> _pluginServices =
		new HashMap<String, Set<String>>();
	private Set<String> _portalServices = Collections.emptySet();

}