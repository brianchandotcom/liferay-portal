/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.loader.modules.extender.internal.servlet.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import java.util.UUID;

/**
 * @author Joao Victor Alves
 */
public class JSLoaderModulesUtil {

	public static boolean eTagEquals(String headerValue) {
		return _eTag.equals(headerValue);
	}

	public static String getETag() {
		return _eTag;
	}

	public static void updateETag() {
		_eTag = _newETag();
	}

	private static String _newETag() {
		return StringBundler.concat(
			"W/\"", UUID.randomUUID(), StringPool.QUOTE);
	}

	private static volatile String _eTag = _newETag();

}