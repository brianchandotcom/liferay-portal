/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.content.search.web.internal.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Marco Leo
 */
public class CPOptionFacetsUtil {

	public static String getCPOptionKeyFromIndexFieldName(String fieldName) {
		if (Validator.isNull(fieldName)) {
			return StringPool.BLANK;
		}

		String[] fieldNameParts = StringUtil.split(
			fieldName, StringPool.UNDERLINE);

		return fieldNameParts[3];
	}

	public static String getIndexFieldName(
		String optionKey, String languageId) {

		return StringBundler.concat(
			languageId, "_ATTRIBUTE_", optionKey, "_VALUES_NAMES");
	}

}