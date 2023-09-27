/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

import com.liferay.petra.lang.CentralizedThreadLocal;

/**
 * @author Rubén Pulido
 */
public class CopyLayoutThreadLocal {

	public static boolean isCopyLayout() {
		return _copyLayout.get();
	}

	public static void setCopyLayout(boolean enabled) {
		_copyLayout.set(enabled);
	}

	private static final ThreadLocal<Boolean> _copyLayout =
		new CentralizedThreadLocal<>(
			CopyLayoutThreadLocal.class + "._copyLayout", () -> Boolean.FALSE);

}