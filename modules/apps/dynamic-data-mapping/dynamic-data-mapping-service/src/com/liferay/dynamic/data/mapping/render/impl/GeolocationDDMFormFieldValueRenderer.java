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

package com.liferay.portlet.dynamicdatamapping.render;

import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormFieldType;
import com.liferay.portlet.dynamicdatamapping.model.Value;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormFieldValue;

import java.text.NumberFormat;

import java.util.Locale;

/**
 * @author Sergio González
 * @author Marcellus Tavares
 */
public class GeolocationDDMFormFieldValueRenderer
	extends BaseDDMFormFieldValueRenderer {

	@Override
	public String getSupportedDDMFormFieldType() {
		return DDMFormFieldType.GEOLOCATION;
	}

	@Override
	protected ValueAccessor getValueAcessor(Locale locale) {
		return new ValueAccessor(locale) {

			@Override
			public String get(DDMFormFieldValue ddmFormFieldValue) {
				Value value = ddmFormFieldValue.getValue();

				JSONObject jsonObject = createJSONObject(
					value.getString(locale));

				StringBundler sb = new StringBundler(7);

				sb.append(LanguageUtil.get(locale, "latitude"));
				sb.append(": ");

				double latitude = jsonObject.getDouble("latitude");

				NumberFormat numberFormat = NumberFormat.getNumberInstance(
					locale);

				sb.append(numberFormat.format(latitude));

				sb.append(StringPool.COMMA_AND_SPACE);
				sb.append(LanguageUtil.get(locale, "longitude"));
				sb.append(": ");

				double longitude = jsonObject.getDouble("longitude");

				sb.append(numberFormat.format(longitude));

				return sb.toString();
			}

			protected JSONObject createJSONObject(String json) {
				try {
					return JSONFactoryUtil.createJSONObject(json);
				}
				catch (JSONException jsone) {
					throw new ValueAccessorException(jsone);
				}
			}

		};
	}

}