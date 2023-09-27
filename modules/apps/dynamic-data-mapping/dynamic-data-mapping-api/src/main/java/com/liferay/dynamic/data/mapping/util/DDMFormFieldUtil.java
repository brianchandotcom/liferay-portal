/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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