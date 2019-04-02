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
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @author Jeyvison Nascimento
 */
public class LocalizedValueUtil {

	public static LocalizedValue[] getLocalizedProperty(
		String property, JSONObject jsonObject) {

		if (!jsonObject.has(property)) {
			return null;
		}

		List<LocalizedValue> localizedValues = new ArrayList<>();

		JSONObject languageJSONObject = jsonObject.getJSONObject(property);

		Iterator<String> keys = languageJSONObject.keys();

		while (keys.hasNext()) {
			String key = keys.next();

			LocalizedValue localizedValue = new LocalizedValue();

			localizedValue.setKey(key);
			localizedValue.setValue(languageJSONObject.getString(key));

			localizedValues.add(localizedValue);
		}

		return localizedValues.toArray(
			new LocalizedValue[localizedValues.size()]);
	}

	public static String getLocalizedValue(
		LocalizedValue[] localizedValues, String languageId) {

		Map<Locale, String> localizedValue = toLocalizationMap(localizedValues);

		Locale locale = LocaleUtil.fromLanguageId(languageId);

		return localizedValue.get(locale);
	}

	public static void setLocalizedProperty(
		String property, JSONFactory jsonFactory, JSONObject jsonObject,
		Map<Locale, String> map) {

		JSONObject languageJSONObject = jsonFactory.createJSONObject();

		Set<Map.Entry<Locale, String>> set = map.entrySet();

		Stream<Map.Entry<Locale, String>> stream = set.stream();

		stream.forEach(
			entry -> languageJSONObject.put(
				entry.getKey(
				).getLanguage(),
				entry.getValue()));

		jsonObject.put(property, languageJSONObject);
	}

	public static JSONObject toJSONObject(LocalizedValue[] localizedValues)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		if (ArrayUtil.isEmpty(localizedValues)) {
			return jsonObject;
		}

		for (LocalizedValue localizedValue : localizedValues) {
			jsonObject.put(localizedValue.getKey(), localizedValue.getValue());
		}

		return jsonObject;
	}

	public static Map<Locale, String> toLocalizationMap(
		LocalizedValue[] localizedValues) {

		if (ArrayUtil.isEmpty(localizedValues)) {
			return Collections.emptyMap();
		}

		Map<Locale, String> localizationMap = new HashMap<>();

		for (LocalizedValue localizedValue : localizedValues) {
			localizationMap.put(
				LocaleUtil.fromLanguageId(localizedValue.getKey()),
				localizedValue.getValue());
		}

		return localizationMap;
	}

	public static LocalizedValue[] toLocalizedValues(JSONObject jsonObject) {
		List<LocalizedValue> localizedValues = new ArrayList<>();

		Iterator<String> keys = jsonObject.keys();

		while (keys.hasNext()) {
			LocalizedValue localizedValue = new LocalizedValue();

			String key = keys.next();

			localizedValue.setKey(key);
			localizedValue.setValue(jsonObject.getString(key));

			localizedValues.add(localizedValue);
		}

		return localizedValues.toArray(
			new LocalizedValue[localizedValues.size()]);
	}

	public static LocalizedValue[] toLocalizedValues(
		Map<Locale, String> localizationMap) {

		List<LocalizedValue> localizedValues = new ArrayList<>();

		for (Map.Entry<Locale, String> entry : localizationMap.entrySet()) {
			LocalizedValue localizedValue = new LocalizedValue();

			localizedValue.setKey(String.valueOf(entry.getKey()));
			localizedValue.setValue(entry.getValue());

			localizedValues.add(localizedValue);
		}

		return localizedValues.toArray(
			new LocalizedValue[localizationMap.size()]);
	}

}