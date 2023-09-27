/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.core.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Zoltán Takács
 */
public class LanguageUtils {

	public static Map<String, String> getLanguageIdMap(
		Map<Locale, String> map) {

		Map<String, String> localizedMap = new HashMap<>();

		map.forEach(
			(locale, value) -> localizedMap.put(
				LocaleUtil.toLanguageId(locale), value));

		return Collections.unmodifiableMap(localizedMap);
	}

	public static Map<Locale, String> getLocalizedMap(Map<String, String> map)
		throws PortalException {

		if (map == null) {
			return null;
		}

		Map<Locale, String> localizedMap = new HashMap<>();

		for (Map.Entry<String, String> entry : map.entrySet()) {
			Locale locale = LocaleUtil.fromLanguageId(entry.getKey());

			if (locale == null) {
				throw new PortalException(
					"No Locale exist with languageId : " + entry.getKey());
			}

			localizedMap.put(locale, entry.getValue());
		}

		return Collections.unmodifiableMap(localizedMap);
	}

}