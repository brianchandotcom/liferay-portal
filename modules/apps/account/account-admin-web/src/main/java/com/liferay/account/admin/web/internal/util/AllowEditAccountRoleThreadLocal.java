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

package com.liferay.account.admin.web.internal.util;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

/**
 * @author Drew Brokke
 */
public class AllowEditAccountRoleThreadLocal {

	public static Boolean isAllowEditAccountRole() {
		Boolean allowEditAccountRole = _allowEditAccountRole.get();

		if (_log.isDebugEnabled()) {
			_log.debug("allowEditAccountRole " + allowEditAccountRole);
		}

		return allowEditAccountRole;
	}

	public static SafeCloseable setWithSafeCloseable(
		Boolean allowEditAccountRole) {

		boolean currentAllowEditAccountRole = _allowEditAccountRole.get();

		_allowEditAccountRole.set(allowEditAccountRole);

		return () -> _allowEditAccountRole.set(currentAllowEditAccountRole);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AllowEditAccountRoleThreadLocal.class);

	private static final CentralizedThreadLocal<Boolean> _allowEditAccountRole =
		new CentralizedThreadLocal<>(
			AllowEditAccountRoleThreadLocal.class + "._allowEditAccountRole",
			() -> Boolean.FALSE);

}