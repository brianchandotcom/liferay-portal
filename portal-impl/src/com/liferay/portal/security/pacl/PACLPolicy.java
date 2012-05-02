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

package com.liferay.portal.security.pacl;

import java.lang.reflect.Method;

import java.security.Permission;

import java.util.Properties;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 */
public interface PACLPolicy {

	public void checkPermission(Permission permission);

	public ClassLoader getClassLoader();

	public Properties getProperties();

	public String getProperty(String key);

	public String[] getPropertyArray(String key);

	public boolean getPropertyBoolean(String key);

	public Set<String> getPropertySet(String key);

	public String getServletContextName();

	public boolean hasJNDI(String name);

	public boolean hasPortalService(
		Object object, Method method, Object[] arguments);

	public boolean hasSQL(String sql);

	public boolean isActive();

	public boolean isCheckablePermission(Permission permission);

}