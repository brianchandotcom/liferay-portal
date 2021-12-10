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