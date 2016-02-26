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
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.util.AutoResetThreadLocal;

/**
 * @author Cristina González
 */
public class ResourcePermissionClassThreadLocal {

	public static String getResourcePermissionClassName() {
		String resourceClassName = _resourcePermissionClassName.get();

		if (_log.isDebugEnabled()) {
			_log.debug("getResourceClassName " + resourceClassName);
		}

		return resourceClassName;
	}

	public static boolean isDeleteInProcess() {
		return _deleteInProcess.get();
	}

	public static void setDeleteInProcess(boolean deleteInProcess) {
		_deleteInProcess.set(deleteInProcess);
	}

	public static void setResourcePermissionClassName(
		String resourcePermissionClassName) {

		if (_log.isDebugEnabled()) {
			_log.debug("setResourceClassName" + resourcePermissionClassName);
		}

		_resourcePermissionClassName.set(resourcePermissionClassName);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ResourcePermissionClassThreadLocal.class);

	private static final ThreadLocal<Boolean> _deleteInProcess =
		new AutoResetThreadLocal<>(
			ResourcePermissionClassThreadLocal.class + "._deleteInProcess",
			false);
	private static final ThreadLocal<String> _resourcePermissionClassName =
		new AutoResetThreadLocal<>(
			ResourcePermissionClassThreadLocal.class + ".className",
			ResourcePermission.class.getName());

}