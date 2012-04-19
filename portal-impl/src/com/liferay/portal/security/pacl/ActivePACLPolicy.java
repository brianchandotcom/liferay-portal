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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.security.pacl.checker.Checker;
import com.liferay.portal.security.pacl.checker.SQLChecker;

import java.security.Permission;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author Brian Wing Shun Chan
 */
public class ActivePACLPolicy extends BasePACLPolicy {

	public ActivePACLPolicy(
		String servletContextName, ClassLoader classLoader,
		Properties properties) {

		super(servletContextName, classLoader, properties);

		try {
			initCheckers();
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public void checkPermission(Permission permission) {
		Checker checker = getChecker(permission.getClass().getName());

		if (checker != null) {
			checker.checkPermission(permission);
		}
	}

	public Checker getChecker(String permissionClassName) {
		return _checkers.get(permissionClassName);
	}

	public boolean hasSQL(String sql) {
		return _sqlChecker.hasSQL(sql);
	}

	public boolean isActive() {
		return true;
	}

	protected void initCheckers() throws Exception {
		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		Properties portalProperties = PropsUtil.getProperties(
			"portal.security.manager.pacl.policy.checker", false);

		for (Map.Entry<Object, Object> entry : portalProperties.entrySet()) {
			String key = (String)entry.getKey();

			int x = key.indexOf("[");
			int y = key.indexOf("]");

			String permissionClassName = key.substring(x + 1, y);

			String checkerClassName = (String)entry.getValue();

			Class<?> checkerClass = classLoader.loadClass(checkerClassName);

			Checker checker = (Checker)checkerClass.newInstance();

			checker.setPACLPolicy(this);

			checker.afterPropertiesSet();

			_checkers.put(permissionClassName, checker);
		}

		_sqlChecker = new SQLChecker();

		_sqlChecker.setPACLPolicy(this);

		_sqlChecker.afterPropertiesSet();
	}

	private static Log _log = LogFactoryUtil.getLog(ActivePACLPolicy.class);

	private Map<String, Checker> _checkers = new HashMap<String, Checker>();
	private SQLChecker _sqlChecker;

}