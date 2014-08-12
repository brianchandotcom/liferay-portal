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

package com.liferay.portlet.dynamicdatamapping.storage;

import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
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

	@Override
	protected String doRender(Field field, Locale locale) throws Exception {
		List<String> values = new ArrayList<String>();

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
			jsonObject = JSONFactoryUtil.createJSONObject(value);
		}
		catch (JSONException jsone) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to parse JSON", jsone);
			}

			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(7);

		sb.append(LanguageUtil.get(locale, "latitude"));
		sb.append(": ");

		double latitude = jsonObject.getDouble("latitude");

		NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);

		sb.append(numberFormat.format(latitude));

		sb.append(StringPool.COMMA_AND_SPACE);
		sb.append(LanguageUtil.get(locale, "longitude"));
		sb.append(": ");

		double longitude = jsonObject.getDouble("longitude");

		sb.append(numberFormat.format(longitude));

		return sb.toString();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GeolocationFieldRenderer.class);

}