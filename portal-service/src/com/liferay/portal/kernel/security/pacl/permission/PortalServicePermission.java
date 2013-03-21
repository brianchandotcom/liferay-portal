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

import java.lang.reflect.Method;

import java.security.BasicPermission;

/**
 * @author Raymond Augé
 */
public class PortalServicePermission extends BasicPermission {

	public static void checkDynamicQuery(Class<?> implClass) {
		_pacl.checkDynamicQuery(implClass);
	}

	public static void checkService(
		Object object, Method method, Object[] arguments) {

		_pacl.checkService(object, method, arguments);
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

	private static PACL _pacl = new NoPACL();

	private transient Object[] _arguments;
	private transient Method _method;
	private transient Object _object;

	public static interface PACL {

		public void checkDynamicQuery(Class<?> implClass);

		public void checkService(
			Object object, Method method, Object[] arguments);

	}

	private static class NoPACL implements PACL {

		public void checkDynamicQuery(Class<?> implClass) {

			// no operation

		}

		public void checkService(
			Object object, Method method, Object[] arguments) {

			// no operation

		}

	}

}