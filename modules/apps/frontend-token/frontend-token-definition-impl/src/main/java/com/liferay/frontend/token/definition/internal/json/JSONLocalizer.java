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

package com.liferay.frontend.token.definition.internal.json;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Iván Zaera
 * @review
 */
public class JSONLocalizer {

	public JSONLocalizer(
		String json, JSONFactory jsonFactory,
		ResourceBundleLoader resourceBundleLoader, String themeId) {

		_json = json;
		_jsonFactory = jsonFactory;
		_resourceBundleLoader = resourceBundleLoader;
		_themeId = themeId;
	}

	/**
	 * Get a translated JSON
	 * @param locale the target locale or null to get the untranslated JSON
	 * @return the translated JSON
	 */
	public String getJSON(Locale locale) {
		if ((_resourceBundleLoader == null) || (locale == null)) {
			return _json;
		}

		String json = _jsons.get(locale);

		if (json == null) {
			try {
				JSONObject jsonObject = _jsonFactory.createJSONObject(_json);

				_localize(jsonObject, locale);

				json = _jsonFactory.looseSerializeDeep(jsonObject);
			}
			catch (JSONException jsonException) {
				_log.error(
					"Unable to parse JSON for theme " + _themeId,
					jsonException);

				json = _json;
			}

			_jsons.put(locale, json);
		}

		return json;
	}

	private void _localize(JSONObject jsonObject, Locale locale) {
		if (locale == null) {
			return;
		}

		ResourceBundle resourceBundle =
			_resourceBundleLoader.loadResourceBundle(locale);

		if (resourceBundle == null) {
			return;
		}

		Map<String, String> map = new HashMap<>();

		for (String key : jsonObject.keySet()) {
			if (_localizableKeys.contains(key)) {
				String value = jsonObject.getString(key);

				if (Validator.isNotNull(value)) {
					try {
						map.put(
							key,
							GetterUtil.getString(
								ResourceBundleUtil.getString(
									resourceBundle, value),
								value));
					}
					catch (MissingResourceException missingResourceException) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								StringBundler.concat(
									"Unable to find key ", key, " in theme ",
									_themeId),
								missingResourceException);
						}
					}
				}
			}
			else {
				Object object = jsonObject.get(key);

				if (object instanceof JSONObject) {
					_localize((JSONObject)object, locale);
				}
				else if (object instanceof JSONArray) {
					JSONArray jsonArray = (JSONArray)object;

					for (int i = 0; i < jsonArray.length(); i++) {
						Object currentObject = jsonArray.get(i);

						if (currentObject instanceof JSONObject) {
							_localize((JSONObject)currentObject, locale);
						}
					}
				}
			}
		}

		for (Map.Entry<String, String> entry : map.entrySet()) {
			jsonObject.put(entry.getKey(), entry.getValue());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(JSONLocalizer.class);

	private static final Set<String> _localizableKeys = new HashSet<>(
		Arrays.asList("label"));

	private final String _json;
	private final JSONFactory _jsonFactory;
	private final Map<Locale, String> _jsons = new ConcurrentHashMap<>();
	private final ResourceBundleLoader _resourceBundleLoader;
	private final String _themeId;

}