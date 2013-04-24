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

package com.liferay.portal.security.pacl;

import java.lang.reflect.Method;

import java.security.Permission;

import java.util.Properties;

/**
 * @author Brian Wing Shun Chan
 */
public class InactivePACLPolicy extends BasePACLPolicy {

	public InactivePACLPolicy(
		String servletContextName, ClassLoader classLoader,
		Properties properties) {

		super(servletContextName, classLoader, properties);
	}

	public boolean hasJNDI(String name) {
		return true;
	}

	public boolean hasPortalService(
		Object object, Method method, Object[] arguments) {

		return true;
	}

	public boolean hasSQL(String sql) {
		return true;
	}

	public boolean implies(Permission permission) {
		return true;
	}

	public boolean isActive() {
		return false;
	}

}