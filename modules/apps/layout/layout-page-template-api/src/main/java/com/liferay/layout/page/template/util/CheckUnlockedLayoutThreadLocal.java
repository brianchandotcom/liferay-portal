/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.util;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.lang.SafeCloseable;

/**
 * @author Jürgen Kappler
 */
public class CheckUnlockedLayoutThreadLocal {

	public static Boolean isCheckUnlockedLayout() {
		return _checkUnlockedLayout.get();
	}

	public static SafeCloseable setWithSafeCloseable(
		Boolean checkUnlockedLayout) {

		boolean currentCheckUnlockedLayout = _checkUnlockedLayout.get();

		_checkUnlockedLayout.set(checkUnlockedLayout);

		return () -> _checkUnlockedLayout.set(currentCheckUnlockedLayout);
	}

	private static final CentralizedThreadLocal<Boolean> _checkUnlockedLayout =
		new CentralizedThreadLocal<>(
			CheckUnlockedLayoutThreadLocal.class + "._checkUnlockedLayout",
			() -> Boolean.TRUE);

}