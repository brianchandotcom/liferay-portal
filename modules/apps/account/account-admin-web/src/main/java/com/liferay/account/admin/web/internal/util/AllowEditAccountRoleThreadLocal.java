/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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