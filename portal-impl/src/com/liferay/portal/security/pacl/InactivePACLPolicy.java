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

/**
 * @author Brian Wing Shun Chan
 */
public class InactivePACLPolicy extends BasePACLPolicy {

	public InactivePACLPolicy(
		String servletContextName, ClassLoader classLoader,
		Properties properties) {

		super(servletContextName, classLoader, properties);
	}

	public boolean hasDynamicQuery(Class<?> clazz) {
		return true;
	}

	public boolean hasFileDelete(String fileName) {
		return true;
	}

	public boolean hasFileExecute(String fileName) {
		return true;
	}

	public boolean hasFileRead(String fileName) {
		return true;
	}

	public boolean hasFileWrite(String fileName) {
		return true;
	}

	public boolean hasHookCustomJspDir() {
		return true;
	}

	public boolean hasHookIndexer(String className) {
		return true;
	}

	public boolean hasHookLanguagePropertiesLocale(Locale locale) {
		return true;
	}

	public boolean hasHookPortalPropertiesKey(String key) {
		return true;
	}

	public boolean hasHookService(String className) {
		return true;
	}

	public boolean hasHookServletFilters() {
		return true;
	}

	public boolean hasHookStrutsActionPath(String actionPath) {
		return true;
	}

	public boolean hasService(Object object, Method method) {
		return true;
	}

	public boolean hasSocketConnect(String host, int port) {
		return true;
	}

	public boolean hasSocketListen(int port) {
		return true;
	}

	public boolean hasSQL(String sql) {
		return true;
	}

	public boolean isActive() {
		return false;
	}

}