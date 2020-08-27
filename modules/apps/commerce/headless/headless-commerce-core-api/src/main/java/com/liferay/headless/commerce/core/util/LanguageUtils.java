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