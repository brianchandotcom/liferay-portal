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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.util.AutoResetThreadLocal;

/**
 * @author Cristina González
 */
public class RoleClassNameThreadLocal {

	public static String getRoleClassName() {
		String roleClassName = _roleClassName.get();

		if (_log.isDebugEnabled()) {
			_log.debug("getRoleClassName " + roleClassName);
		}

		return roleClassName;
	}

	public static boolean isDeleteInProcess() {
		return _deleteInProcess.get();
	}

	public static void setDeleteInProcess(boolean deleteInProcess) {
		_deleteInProcess.set(deleteInProcess);
	}

	public static void setRoleClassName(String roleClassName) {
		if (_log.isDebugEnabled()) {
			_log.debug("setRoleClassName" + roleClassName);
		}

		_roleClassName.set(roleClassName);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RoleClassNameThreadLocal.class);

	private static final ThreadLocal<Boolean> _deleteInProcess =
		new AutoResetThreadLocal<>(
			RoleClassNameThreadLocal.class + "._deleteInProcess", false);
	private static final ThreadLocal<String> _roleClassName =
		new AutoResetThreadLocal<>(
			RoleClassNameThreadLocal.class + ".className",
			Role.class.getName());

}