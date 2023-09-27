/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.workflow.internal.dto.v1_0.util;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.Locale;
import java.util.Map;

/**
 * @author Feliphe Marinho
 */
public class LabelUtil {

	public static String getLabel(
		String key, Map<Locale, String> labelMap, Locale locale) {

		if (MapUtil.isNotEmpty(labelMap) && (labelMap.get(locale) != null)) {
			return labelMap.get(locale);
		}

		return LanguageUtil.get(locale, key);
	}

}