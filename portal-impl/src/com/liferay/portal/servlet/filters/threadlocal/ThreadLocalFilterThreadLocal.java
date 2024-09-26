/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.servlet.filters.threadlocal;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.lang.SafeCloseable;

/**
 * @author Tina Tian
 */
public class ThreadLocalFilterThreadLocal {

	public static boolean isFilterInvoked() {
		return _filterInvoked.get();
	}

	public static void setFilterInvoked() {
		_filterInvoked.set(true);
	}

	public static SafeCloseable setFilterInvokedWithSafeCloseable(
		boolean filterInvoked) {

		return _filterInvoked.setWithSafeCloseable(filterInvoked);
	}

	private static final CentralizedThreadLocal<Boolean> _filterInvoked =
		new CentralizedThreadLocal<>(
			ThreadLocalFilterThreadLocal.class + "._filterInvoked",
			() -> Boolean.FALSE);

}