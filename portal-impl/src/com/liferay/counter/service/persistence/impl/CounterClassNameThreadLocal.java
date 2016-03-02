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

package com.liferay.counter.service.persistence.impl;

import com.liferay.counter.kernel.model.Counter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.AutoResetThreadLocal;

/**
 * @author Cristina González
 */
public class CounterClassNameThreadLocal {

	public static String getCounterClassName() {
		String resourceClassName = _counterClassName.get();

		if (_log.isDebugEnabled()) {
			_log.debug("getCounterClassName " + resourceClassName);
		}

		return resourceClassName;
	}

	public static boolean isDeleteInProcess() {
		return _deleteInProcess.get();
	}

	public static void setCounterClassName(String resourcePermissionClassName) {
		if (_log.isDebugEnabled()) {
			_log.debug("setCounterClassName" + resourcePermissionClassName);
		}

		_counterClassName.set(resourcePermissionClassName);
	}

	public static void setDeleteInProcess(boolean deleteInProcess) {
		_deleteInProcess.set(deleteInProcess);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CounterClassNameThreadLocal.class);

	private static final ThreadLocal<String> _counterClassName =
		new AutoResetThreadLocal<>(
			CounterClassNameThreadLocal.class + ".className",
			Counter.class.getName());
	private static final ThreadLocal<Boolean> _deleteInProcess =
		new AutoResetThreadLocal<>(
			CounterClassNameThreadLocal.class + "._deleteInProcess", false);

}