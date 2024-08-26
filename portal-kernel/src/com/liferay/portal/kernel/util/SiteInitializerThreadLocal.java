/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

import com.liferay.petra.lang.CentralizedThreadLocal;

import java.util.Objects;

/**
 * @author Nilton Vieira
 */
public class SiteInitializerThreadLocal {

	public static String getKey() {
		return _key.get();
	}

	public static void setKey(String name) {
		if (Objects.equals(_key.get(), name)) {
			return;
		}

		_key.set(name);
	}

	private static final ThreadLocal<String> _key =
		new CentralizedThreadLocal<>(
			SiteInitializerThreadLocal.class + "._key");

}