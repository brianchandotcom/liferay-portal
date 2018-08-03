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

package com.liferay.dynamic.data.mapping.internal.render;

import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.render.BaseDDMFormFieldValueRenderer;
import com.liferay.dynamic.data.mapping.render.ValueAccessor;
import com.liferay.dynamic.data.mapping.render.ValueAccessorException;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;

import java.util.Locale;

/**
 * @author Bruno Basto
 * @author Marcellus Tavares
 */
public abstract class BaseListDDMFormFieldValueRenderer
	extends BaseDDMFormFieldValueRenderer {

	@Override
	protected ValueAccessor getValueAcessor(Locale locale) {
		return new ValueAccessor(locale) {

			@Override
			public String get(DDMFormFieldValue ddmFormFieldValue) {
				Value value = ddmFormFieldValue.getValue();

				JSONArray jsonArray = createJSONArray(value.getString(locale));

				if (jsonArray.length() == 0) {
					return StringPool.BLANK;
				}

				StringBundler sb = new StringBundler(jsonArray.length() * 2);

				for (int i = 0; i < jsonArray.length(); i++) {
					LocalizedValue label = getDDMFormFieldOptionLabel(
						ddmFormFieldValue, jsonArray.getString(i));

					if (label == null) {
						continue;
					}

					sb.append(label.getString(locale));
					sb.append(StringPool.COMMA_AND_SPACE);
				}

				if (sb.length() == 0) {
					return StringPool.BLANK;
				}

				sb.setIndex(sb.index() - 1);

				return sb.toString();
			}

			protected JSONArray createJSONArray(String json) {
				try {
					return JSONFactoryUtil.createJSONArray(json);
				}
				catch (JSONException jsone) {
					throw new ValueAccessorException(jsone);
				}
			}

			protected LocalizedValue getDDMFormFieldOptionLabel(
				DDMFormFieldValue ddmFormFieldValue, String optionValue) {

				DDMFormField ddmFormField = getDDMFormField(ddmFormFieldValue);

				DDMFormFieldOptions ddmFormFieldOptions =
					ddmFormField.getDDMFormFieldOptions();

				return ddmFormFieldOptions.getOptionLabels(optionValue);
			}

		};
	}

}