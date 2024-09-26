/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.initializer.kernel.util;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.lang.SafeCloseable;

/**
 * @author Nilton Vieira
 */
public class SiteInitializerThreadLocal {

	public static String getKey() {
		return _key.get();
	}

	public static SafeCloseable setKeyWithSafeCloseable(String key) {
		return _key.setWithSafeCloseable(key);
	}

	private static final CentralizedThreadLocal<String> _key =
		new CentralizedThreadLocal<>(
			SiteInitializerThreadLocal.class + "._key");

}