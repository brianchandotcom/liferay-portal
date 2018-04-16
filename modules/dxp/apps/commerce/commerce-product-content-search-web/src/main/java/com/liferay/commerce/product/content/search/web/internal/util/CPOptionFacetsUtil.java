/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.commerce.product.content.search.web.internal.util;

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

		return fieldNameParts[1];
	}

	public static String getIndexFieldName(String optionKey) {
		return "ATTRIBUTE_" + optionKey + "_VALUES_NAMES";
	}

}