/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.search;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.lang.SafeCloseable;

/**
 * @author Shuyang Zhou
 */
public class StrictObjectReindexThreadLocal {

	public static boolean isStrictObjectReindex() {
		return _strictObjectReindex.get();
	}

	public static SafeCloseable setStrictObjectReindexWithSafeCloseable(
		boolean strictObjectReindex) {

		return _strictObjectReindex.setWithSafeCloseable(strictObjectReindex);
	}

	private static final CentralizedThreadLocal<Boolean> _strictObjectReindex =
		new CentralizedThreadLocal<>(
			StrictObjectReindexThreadLocal.class + "._strictObjectReindex",
			() -> Boolean.FALSE);

}