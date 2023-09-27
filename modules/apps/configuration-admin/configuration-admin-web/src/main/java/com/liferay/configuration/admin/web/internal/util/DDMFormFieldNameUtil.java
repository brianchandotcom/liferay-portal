/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.configuration.admin.web.internal.util;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.util.CamelCaseUtil;

/**
 * @author Drew Brokke
 */
public class DDMFormFieldNameUtil {

	public static String normalizeFieldName(String fieldName) {
		return CamelCaseUtil.toCamelCase(fieldName, CharPool.PERIOD);
	}

}