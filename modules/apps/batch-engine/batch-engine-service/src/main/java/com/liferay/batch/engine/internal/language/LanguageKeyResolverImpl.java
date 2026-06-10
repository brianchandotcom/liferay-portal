/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.internal.language;

import com.liferay.batch.engine.language.LanguageKeyResolver;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Vendel Töreki
 */
@Component(service = LanguageKeyResolver.class)
public class LanguageKeyResolverImpl implements LanguageKeyResolver {

	@Override
	public void expand(JSONObject jsonObject) {
		_expand(jsonObject);
	}

	@Override
	public void expand(Map<String, Object> fieldNameValueMap) {
		_expand(fieldNameValueMap);
	}

	private void _expand(Object object) {
		if (object instanceof JSONArray) {
			JSONArray jsonArray = (JSONArray)object;

			for (int i = 0; i < jsonArray.length(); i++) {
				_expand(jsonArray.get(i));
			}
		}
		else if (object instanceof JSONObject) {
			JSONObject jsonObject = (JSONObject)object;

			for (String key : jsonObject.keySet()) {
				_expand(jsonObject.get(key));
			}

			Object value = jsonObject.get(FOR_EACH_LANGUAGE_ID);

			if (!(value instanceof String)) {
				return;
			}

			jsonObject.remove(FOR_EACH_LANGUAGE_ID);

			for (Map.Entry<String, String> entry :
					_resolve(
						(String)value
					).entrySet()) {

				if (!jsonObject.has(entry.getKey())) {
					jsonObject.put(entry.getKey(), entry.getValue());
				}
			}
		}
		else if (object instanceof List) {
			for (Object value : (List<Object>)object) {
				_expand(value);
			}
		}
		else if (object instanceof Map) {
			Map<String, Object> map = (Map<String, Object>)object;

			for (Object value : map.values()) {
				_expand(value);
			}

			Object value = map.get(FOR_EACH_LANGUAGE_ID);

			if (!(value instanceof String)) {
				return;
			}

			map.remove(FOR_EACH_LANGUAGE_ID);

			for (Map.Entry<String, String> entry :
					_resolve(
						(String)value
					).entrySet()) {

				map.putIfAbsent(entry.getKey(), entry.getValue());
			}
		}
	}

	private Map<String, String> _resolve(String key) {
		Map<String, String> translations = new LinkedHashMap<>();

		for (Locale locale : _language.getAvailableLocales()) {
			String translation = _language.get(locale, key, null);

			if (Validator.isNull(translation)) {
				continue;
			}

			translations.put(LocaleUtil.toLanguageId(locale), translation);
		}

		if (translations.isEmpty() && _log.isWarnEnabled()) {
			_log.warn("Unable to resolve language key " + key);
		}

		return translations;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LanguageKeyResolverImpl.class);

	@Reference
	private Language _language;

}