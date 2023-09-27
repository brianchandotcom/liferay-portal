/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.util;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author André de Oliveira
 */
public class SearchStringUtil {

	public static String[] splitAndUnquote(String s) {
		return TransformUtil.transform(
			StringUtil.split(s.trim(), CharPool.COMMA),
			string -> StringUtil.unquote(string.trim()), String.class);
	}

}