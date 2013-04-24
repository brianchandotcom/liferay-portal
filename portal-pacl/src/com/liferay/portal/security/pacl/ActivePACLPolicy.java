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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.security.pacl.checker.Checker;
import com.liferay.portal.security.pacl.checker.JNDIChecker;
import com.liferay.portal.security.pacl.checker.SQLChecker;

import java.security.Permission;

import java.util.Properties;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class ActivePACLPolicy extends BasePACLPolicy {

	public ActivePACLPolicy(
		String servletContextName, ClassLoader classLoader,
		Properties properties) {

		super(servletContextName, classLoader, properties);

		try {
			initJNDIChecker();
			initSQLChecker();
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public JNDIChecker getJndiChecker() {
		return _jndiChecker;
	}

	public SQLChecker getSqlChecker() {
		return _sqlChecker;
	}

	public boolean hasJNDI(String name) {
		return _jndiChecker.hasJNDI(name);
	}

	public boolean hasSQL(String sql) {
		return _sqlChecker.hasSQL(sql);
	}

	public boolean implies(Permission permission) {
		Checker checker = getChecker(permission.getClass());

		return checker.implies(permission);
	}

	public boolean isActive() {
		return true;
	}

	protected void initJNDIChecker() {
		_jndiChecker = new JNDIChecker();

		initChecker(_jndiChecker);
	}

	protected void initSQLChecker() {
		_sqlChecker = new SQLChecker();

		initChecker(_sqlChecker);
	}

	private static Log _log = LogFactoryUtil.getLog(ActivePACLPolicy.class);

	private JNDIChecker _jndiChecker;
	private SQLChecker _sqlChecker;

}