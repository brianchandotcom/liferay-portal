/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.servlet.filters.threadlocal;

import com.liferay.petra.lang.CentralizedThreadLocal;

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

	private static final ThreadLocal<Boolean> _filterInvoked =
		new CentralizedThreadLocal<>(
			ThreadLocalFilterThreadLocal.class + "._filterInvoked",
			() -> Boolean.FALSE);

}