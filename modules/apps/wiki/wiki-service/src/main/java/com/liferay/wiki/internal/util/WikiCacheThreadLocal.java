/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.wiki.internal.util;

import com.liferay.petra.lang.CentralizedThreadLocal;

/**
 * @author Jorge Ferrer
 */
public class WikiCacheThreadLocal {

	public static boolean isClearCache() {
		return _clearCache.get();
	}

	public static void setClearCache(boolean clearCache) {
		_clearCache.set(clearCache);
	}

	private static final ThreadLocal<Boolean> _clearCache =
		new CentralizedThreadLocal<>(
			WikiCacheThreadLocal.class + "._clearCache", () -> Boolean.TRUE,
			false);

}