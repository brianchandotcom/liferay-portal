/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.increment;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.lang.SafeCloseable;

/**
 * @author Dante Wang
 */
public class BufferedIncrementThreadLocal {

	public static boolean isForceSync() {
		return _forceSync.get();
	}

	public static SafeCloseable setWithSafeCloseable(boolean forceSync) {
		return _forceSync.setWithSafeCloseable(forceSync);
	}

	private static final CentralizedThreadLocal<Boolean> _forceSync =
		new CentralizedThreadLocal<>(
			BufferedIncrementThreadLocal.class + "_forceSync",
			() -> Boolean.FALSE);

}