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

package com.liferay.data.engine.rest.internal.dto.v1_0.util;

import com.liferay.data.engine.rest.dto.v1_0.LocalizedValue;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Jeyvison Nascimento
 */
public class DataEngineUtil {

	public static Map<Locale, String> toLocalizationMap(
		LocalizedValue[] localizedValues) {

		Map<Locale, String> localizationMap = new HashMap<>();

		for (LocalizedValue localizedValue : localizedValues) {
			localizationMap.put(
				LocaleUtil.fromLanguageId(localizedValue.getKey()),
				localizedValue.getValue());
		}

		return localizationMap;
	}

	public static LocalizedValue[] toLocalizedValues(Map<Locale, String> map) {
		List<LocalizedValue> localizedValues = new ArrayList<>();

		for (Map.Entry<Locale, String> entry : map.entrySet()) {
			LocalizedValue localizedValue = new LocalizedValue();

			localizedValue.setKey(String.valueOf(entry.getKey()));
			localizedValue.setValue(entry.getValue());

			localizedValues.add(localizedValue);
		}

		return localizedValues.toArray(new LocalizedValue[map.size()]);
	}

}