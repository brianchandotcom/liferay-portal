/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.buffer;

import com.liferay.petra.lang.CentralizedThreadLocal;

/**
 * @author Michael C. Han
 */
public class BufferOverflowThreadLocal {

	public static boolean isOverflowMode() {
		return _overflowMode.get();
	}

	public static void setOverflowMode(boolean overflowMode) {
		_overflowMode.set(overflowMode);
	}

	private static final ThreadLocal<Boolean> _overflowMode =
		new CentralizedThreadLocal<>(
			BufferOverflowThreadLocal.class + "._overflowMode",
			() -> Boolean.FALSE);

}