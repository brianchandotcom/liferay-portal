/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.render;

import com.liferay.dynamic.data.mapping.model.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.render.BaseDDMFormFieldValueRenderer;
import com.liferay.dynamic.data.mapping.render.ValueAccessor;
import com.liferay.dynamic.data.mapping.render.ValueAccessorException;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;

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
	protected ValueAccessor getValueAccessor(Locale locale) {
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
				catch (JSONException jsonException) {
					throw new ValueAccessorException(jsonException);
				}
			}

		};
	}

}