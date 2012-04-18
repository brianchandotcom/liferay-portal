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

package com.liferay.portal.security.pacl.permission;

import java.lang.reflect.Method;

/**
 * @author Raymond Augé
 */
public class PortalServicePermission extends PortalPermission {

	public PortalServicePermission(
		String name, ClassLoader policyClassLoader, Object object) {

		this(name, policyClassLoader, object, null);
	}

	public PortalServicePermission(
		String name, ClassLoader policyClassLoader, Object object,
		Method method) {

		super(name, policyClassLoader);

		_object = object;
		_method = method;
	}

	public Object getObject() {
		return _object;
	}

	public Method getMethod() {
		return _method;
	}

	private Object _object;
	private Method _method;

}