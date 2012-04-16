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

import java.util.Locale;
import java.util.Properties;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 */
public interface PACLPolicy {

	public ClassLoader getClassLoader();

	public Properties getProperties();

	public String getProperty(String key);

	public String[] getPropertyArray(String key);

	public boolean getPropertyBoolean(String key);

	public Set<String> getPropertySet(String key);

	public String getServletContextName();

	public boolean hasDynamicQuery(Class<?> clazz);

	public boolean hasFileDelete(String fileName);

	public boolean hasFileExecute(String fileName);

	public boolean hasFileRead(String fileName);

	public boolean hasFileWrite(String fileName);

	public boolean hasHookCustomJspDir();

	public boolean hasHookLanguagePropertiesLocale(Locale locale);

	public boolean hasHookPortalPropertiesKey(String key);

	public boolean hasHookService(String className);

	public boolean hasHookServletFilters();

	public boolean hasHookStrutsActionPath(String path);

	public boolean hasService(Object object, Method method);

	public boolean hasSocketConnect(String host, int port);

	public boolean hasSocketListen(int port);

	public boolean hasSQL(String sql);

	public boolean isActive();

}