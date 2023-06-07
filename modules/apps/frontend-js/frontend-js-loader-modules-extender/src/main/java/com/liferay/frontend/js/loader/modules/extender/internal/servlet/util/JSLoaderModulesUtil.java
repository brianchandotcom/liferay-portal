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

	public static boolean etagEquals(String header) {
		return _etag.equals(header);
	}

	public static String getEtag() {
		return _etag;
	}

	public static void updateEtag() {
		_etag = StringBundler.concat(
			"W/\"", UUID.randomUUID(), StringPool.QUOTE);
	}

	private static volatile String _etag = StringBundler.concat(
		"W/\"", UUID.randomUUID(), StringPool.QUOTE);

}