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

package com.liferay.portal.kernel.security.pacl.permission;

import com.liferay.portal.kernel.security.pacl.PACLConstants;

import java.lang.reflect.Method;

import java.security.BasicPermission;
import java.security.Permission;

/**
 * @author Raymond Augé
 */
public class PortalServicePermission extends BasicPermission {

	public static void checkDynamicQuery(Class<?> implClass) {
		SecurityManager securityManager = System.getSecurityManager();

		if (securityManager == null) {
			return;
		}

		Permission permission = new PortalServicePermission(
			PACLConstants.PORTAL_SERVICE_PERMISSION_DYNAMIC_QUERY, implClass,
			null);

		securityManager.checkPermission(permission);
	}

	public static void checkService(
		Object object, Method method, Object[] arguments) {

		SecurityManager securityManager = System.getSecurityManager();

		if (securityManager == null) {
			return;
		}

		PortalServicePermission portalServicePermission =
			new PortalServicePermission(
				PACLConstants.PORTAL_SERVICE_PERMISSION_SERVICE, object, method,
				arguments);

		securityManager.checkPermission(portalServicePermission);
	}

	public PortalServicePermission(String name, Object object, Method method) {
		this(name, object, method, null);
	}

	public PortalServicePermission(
		String name, Object object, Method method, Object[] arguments) {

		super(name);

		_object = object;
		_method = method;
		_arguments = arguments;
	}

	public Object[] getArguments() {
		return _arguments;
	}

	public Method getMethod() {
		return _method;
	}

	public Object getObject() {
		return _object;
	}

	private transient Object[] _arguments;
	private transient Method _method;
	private transient Object _object;

}