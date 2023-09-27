/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.search;

import com.liferay.petra.lang.CentralizedThreadLocal;

/**
 * @author Daniel Kocsis
 */
public class IndexStatusManagerThreadLocal {

	public static boolean isIndexReadOnly() {
		return _indexReadOnly.get();
	}

	public static void setIndexReadOnly(boolean indexReadOnly) {
		_indexReadOnly.set(indexReadOnly);
	}

	private static final ThreadLocal<Boolean> _indexReadOnly =
		new CentralizedThreadLocal<>(
			IndexStatusManagerThreadLocal.class + "._indexReadOnly",
			() -> Boolean.FALSE);

}