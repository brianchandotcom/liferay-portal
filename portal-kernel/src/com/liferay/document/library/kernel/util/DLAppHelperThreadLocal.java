/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.kernel.util;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.lang.SafeCloseable;

/**
 * @author Eudaldo Alonso
 */
public class DLAppHelperThreadLocal {

	public static boolean isEnabled() {
		return _enabled.get();
	}

	public static void setEnabled(boolean enabled) {
		_enabled.set(enabled);
	}

	public static SafeCloseable setEnabledWithSafeCloseable(boolean enabled) {
		return _enabled.setWithSafeCloseable(enabled);
	}

	private static final CentralizedThreadLocal<Boolean> _enabled =
		new CentralizedThreadLocal<>(
			DLAppHelperThreadLocal.class + "._enabled", () -> Boolean.TRUE);

}