/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.util;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.lang.SafeCloseable;

/**
 * @author Lourdes Fernández Besada
 */
public class CheckNoninstanceablePortletThreadLocal {

	public static boolean isCheckNoninstanceablePortlet() {
		return _checkNoninstanceablePortlet.get();
	}

	public static SafeCloseable setCheckNoninstanceablePortletWithSafeCloseable(
		Boolean checkNoninstanceablePortlet) {

		boolean currentCheckNoninstanceablePortlet =
			_checkNoninstanceablePortlet.get();

		_checkNoninstanceablePortlet.set(checkNoninstanceablePortlet);

		return () -> _checkNoninstanceablePortlet.set(
			currentCheckNoninstanceablePortlet);
	}

	private static final ThreadLocal<Boolean> _checkNoninstanceablePortlet =
		new CentralizedThreadLocal<>(
			CheckNoninstanceablePortletThreadLocal.class +
				"._checkNoninstanceablePortlet",
			() -> Boolean.FALSE);

}