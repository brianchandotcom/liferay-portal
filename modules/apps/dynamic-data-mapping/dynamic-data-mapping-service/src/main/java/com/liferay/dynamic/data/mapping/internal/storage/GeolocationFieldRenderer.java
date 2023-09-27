/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.storage;

import com.liferay.dynamic.data.mapping.storage.BaseFieldRenderer;
import com.liferay.dynamic.data.mapping.storage.Field;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.text.NumberFormat;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Sergio González
 */
public class GeolocationFieldRenderer extends BaseFieldRenderer {

	protected GeolocationFieldRenderer(
		JSONFactory jsonFactory, Language language) {

		_jsonFactory = jsonFactory;
		_language = language;
	}

	@Override
	protected String doRender(Field field, Locale locale) throws Exception {
		List<String> values = new ArrayList<>();

		for (Serializable value : field.getValues(locale)) {
			String valueString = String.valueOf(value);

			if (Validator.isNull(valueString)) {
				continue;
			}

			values.add(handleJSON(valueString, locale));
		}

		return StringUtil.merge(values, StringPool.COMMA_AND_SPACE);
	}

	@Override
	protected String doRender(Field field, Locale locale, int valueIndex) {
		Serializable value = field.getValue(locale, valueIndex);

		if (Validator.isNull(value)) {
			return StringPool.BLANK;
		}

		return handleJSON(String.valueOf(value), locale);
	}

	protected String handleJSON(String value, Locale locale) {
		JSONObject jsonObject = null;

		try {
			jsonObject = _jsonFactory.createJSONObject(value);
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to parse JSON", jsonException);
			}

			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(7);

		sb.append(_language.get(locale, "latitude"));
		sb.append(": ");

		double latitude = jsonObject.getDouble("latitude");

		NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);

		sb.append(numberFormat.format(latitude));

		sb.append(StringPool.COMMA_AND_SPACE);
		sb.append(_language.get(locale, "longitude"));
		sb.append(": ");

		double longitude = jsonObject.getDouble("longitude");

		sb.append(numberFormat.format(longitude));

		return sb.toString();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GeolocationFieldRenderer.class);

	private final JSONFactory _jsonFactory;
	private final Language _language;

}