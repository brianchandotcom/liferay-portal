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

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.lang.reflect.Method;

import java.security.BasicPermission;

/**
 * @author Raymond Augé
 */
public class PortalServicePermission extends BasicPermission {

	public static void checkService(
		Object object, Method method, Object[] arguments) {

		_pacl.checkService(object, method, arguments);
	}

	public PortalServicePermission(String name, String methodName) {
		super(name);

		_init();
		_methodName = methodName;
	}

	public PortalServicePermission(
		String name, String servletContextName, String className,
		String methodName) {

		super(_createLongName(name, servletContextName, className));

		_init();
		_methodName = methodName;
	}

	@Override
	public String getActions() {
		return _methodName;
	}

	public String getClassName() {
		return _className;
	}

	public String getMethodName() {
		return _methodName;
	}

	public String getServletContextName() {
		return _servletContextName;
	}

	public String getShortName() {
		return _shortName;
	}

	private void _init() {
		String[] nameParts = StringUtil.split(getName(), StringPool.POUND);

		if (nameParts.length != 3) {
			throw new IllegalArgumentException(
				"The format for name is: " +
					"[name]#[servletContextName]#[className], was " +
						getName());
		}

		_shortName = nameParts[0];
		_servletContextName = nameParts[1];
		_className = nameParts[2];
	}

	private static String _createLongName(
		String name, String servletContextName, String className) {

		StringBundler sb = new StringBundler(5);

		sb.append(name);
		sb.append(StringPool.POUND);

		if (Validator.isNull(servletContextName)) {
			sb.append("portal");
		}
		else {
			sb.append(servletContextName);
		}

		sb.append(StringPool.POUND);
		sb.append(className);

		return sb.toString();
	}

	private static PACL _pacl = new NoPACL();

	private String _className;
	private String _methodName;
	private String _servletContextName;
	private String _shortName;

	private static class NoPACL implements PACL {

		public void checkService(
			Object object, Method method, Object[] arguments) {
		}

	}

	public static interface PACL {

		public void checkService(
			Object object, Method method, Object[] arguments);

	}

}