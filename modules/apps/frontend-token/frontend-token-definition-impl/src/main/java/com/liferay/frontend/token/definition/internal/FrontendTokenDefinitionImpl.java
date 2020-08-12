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

package com.liferay.frontend.token.definition.internal;

import com.liferay.frontend.token.definition.FrontendTokenDefinition;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
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
 */
public class FrontendTokenDefinitionImpl implements FrontendTokenDefinition {

	public FrontendTokenDefinitionImpl(
		String json, JSONFactory jsonFactory,
		ResourceBundleLoader resourceBundleLoader, String themeId) {

		_json = json;
		_jsonFactory = jsonFactory;
		_resourceBundleLoader = resourceBundleLoader;
		_themeId = themeId;
	}

	@Override
	public String getJSON(Locale locale) {
		String json = _jsons.get(locale);

		if (json == null) {
			json = translateJSON(locale);

			_jsons.put(locale, json);
		}

		return json;
	}

	public String getThemeId() {
		return _themeId;
	}

	protected String translateJSON(Locale locale) {
		if (_resourceBundleLoader == null) {
			return _json;
		}

		try {
			JSONObject jsonObject = _jsonFactory.createJSONObject(_json);

			translateJSONObject(
				jsonObject, _resourceBundleLoader.loadResourceBundle(locale));

			return _jsonFactory.looseSerializeDeep(jsonObject);
		}
		catch (JSONException jsonException) {
			_log.error(
				"Unable to parse frontend-token-definition.json for theme " +
					_themeId,
				jsonException);
		}

		return _json;
	}

	protected void translateJSONObject(
		JSONObject jsonObject, ResourceBundle resourceBundle) {

		Map<String, String> translationsMap = new HashMap<>();

		for (String key : jsonObject.keySet()) {
			if (_localizableKeys.contains(key)) {
				String value = jsonObject.getString(key);

				if (Validator.isNotNull(value)) {
					try {
						translationsMap.put(
							key,
							ResourceBundleUtil.getString(
								resourceBundle, value));
					}
					catch (MissingResourceException missingResourceException) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								"Unable to find key " + key,
								missingResourceException);
						}
					}
				}
			}
			else {
				Object object = jsonObject.get(key);

				if (object instanceof JSONObject) {
					translateJSONObject((JSONObject)object, resourceBundle);
				}
				else if (object instanceof JSONArray) {
					JSONArray jsonArray = (JSONArray)object;

					for (int i = 0; i < jsonArray.length(); i++) {
						Object childObject = jsonArray.get(i);

						if (childObject instanceof JSONObject) {
							translateJSONObject(
								(JSONObject)childObject, resourceBundle);
						}
					}
				}
			}
		}

		for (Map.Entry<String, String> entry : translationsMap.entrySet()) {
			jsonObject.put(entry.getKey(), entry.getValue());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FrontendTokenDefinitionImpl.class);

	private static final Set<String> _localizableKeys = new HashSet<>(
		Arrays.asList("label"));

	private final String _json;
	private final JSONFactory _jsonFactory;
	private final Map<Locale, String> _jsons = new ConcurrentHashMap<>();
	private final ResourceBundleLoader _resourceBundleLoader;
	private final String _themeId;

}