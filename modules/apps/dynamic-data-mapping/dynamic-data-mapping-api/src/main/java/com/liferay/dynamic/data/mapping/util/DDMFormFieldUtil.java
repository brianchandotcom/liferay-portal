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

package com.liferay.dynamic.data.mapping.util;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.security.RandomUtil;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Carolina Barbosa
 */
public class DDMFormFieldUtil {

	public static String getDDMFormFieldName(String ddmFormFieldName) {
		for (int i = 0; i < _DDM_FORM_FIELD_NAME_RANDOM_NUMBERS_LENGTH; i++) {
			ddmFormFieldName = ddmFormFieldName.concat(
				String.valueOf(RandomUtil.nextInt(10)));
		}

		return StringUtil.removeChar(ddmFormFieldName, CharPool.SPACE);
	}

	private static final int _DDM_FORM_FIELD_NAME_RANDOM_NUMBERS_LENGTH = 8;

}