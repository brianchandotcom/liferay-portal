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

import com.liferay.portal.security.pacl.checker.FileChecker;
import com.liferay.portal.security.pacl.checker.HookChecker;
import com.liferay.portal.security.pacl.checker.SQLChecker;
import com.liferay.portal.security.pacl.checker.ServiceChecker;
import com.liferay.portal.security.pacl.checker.SocketChecker;

import java.lang.reflect.Method;

import java.util.Locale;
import java.util.Properties;

/**
 * @author Brian Wing Shun Chan
 */
public class ActivePACLPolicy extends BasePACLPolicy {

	public ActivePACLPolicy(
		String servletContextName, ClassLoader classLoader,
		Properties properties) {

		super(servletContextName, classLoader, properties);

		_fileChecker = new FileChecker(this);
		_hookChecker = new HookChecker(this);
		_serviceChecker = new ServiceChecker(this);
		_socketChecker = new SocketChecker(this);
		_sqlChecker = new SQLChecker(this);
	}

	public boolean hasDynamicQuery(Class<?> clazz) {
		return _serviceChecker.hasDynamicQuery(clazz);
	}

	public boolean hasFileDelete(String fileName) {
		return _fileChecker.hasDelete(fileName);
	}

	public boolean hasFileExecute(String fileName) {
		return _fileChecker.hasExecute(fileName);
	}

	public boolean hasFileRead(String fileName) {
		return _fileChecker.hasRead(fileName);
	}

	public boolean hasFileWrite(String fileName) {
		return _fileChecker.hasWrite(fileName);
	}

	public boolean hasHookCustomJspDir() {
		return _hookChecker.hasCustomJspDir();
	}

	public boolean hasHookLanguagePropertiesLocale(Locale locale) {
		return _hookChecker.hasLanguagePropertiesLocale(locale);
	}

	public boolean hasHookPortalPropertiesKey(String key) {
		return _hookChecker.hasPortalPropertiesKey(key);
	}

	public boolean hasHookService(String className) {
		return _hookChecker.hasService(className);
	}

	public boolean hasService(Object object, Method method) {
		return _serviceChecker.hasService(object, method);
	}

	public boolean hasSocketConnect(String host, int port) {
		return _socketChecker.hasConnect(host, port);
	}

	public boolean hasSocketListen(int port) {
		return _socketChecker.hasListen(port);
	}

	public boolean hasSQL(String sql) {
		return _sqlChecker.hasSQL(sql);
	}

	public boolean isActive() {
		return true;
	}

	private FileChecker _fileChecker;
	private HookChecker _hookChecker;
	private ServiceChecker _serviceChecker;
	private SocketChecker _socketChecker;
	private SQLChecker _sqlChecker;

}