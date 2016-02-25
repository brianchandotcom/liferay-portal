/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

		_methodName = methodName;

		_init();
	}

	public PortalServicePermission(
		String name, String servletContextName, String className,
		String methodName) {

		super(_createLongName(name, servletContextName, className));

		_methodName = methodName;

		_init();
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

	public interface PACL {

		public void checkService(
			Object object, Method method, Object[] arguments);

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

	private void _init() {
		String[] nameParts = StringUtil.split(getName(), StringPool.POUND);

		if (nameParts.length != 3) {
			throw new IllegalArgumentException(
				"Name " + getName() + " does not follow the format " +
					"[name]#[servletContextName]#[subject]");
		}

		_shortName = nameParts[0];
		_servletContextName = nameParts[1];
		_className = nameParts[2];
	}

	private static final PACL _pacl = new NoPACL();

	private String _className;
	private final String _methodName;
	private String _servletContextName;
	private String _shortName;

	private static class NoPACL implements PACL {

		@Override
		public void checkService(
			Object object, Method method, Object[] arguments) {
		}

	}

}