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
 * @author Alessio Antonio Rendina
 */
public class CPSpecificationOptionFacetsUtil {

	public static String getCPSpecificationOptionKeyFromIndexFieldName(
		String fieldName) {

		if (Validator.isNull(fieldName)) {
			return StringPool.BLANK;
		}

		String[] fieldNameParts = StringUtil.split(
			fieldName, StringPool.UNDERLINE);

		return fieldNameParts[3];
	}

	public static String getIndexFieldName(String key, String languageId) {
		return StringBundler.concat(
			languageId, "_SPECIFICATION_", key, "_VALUE_NAME");
	}

}