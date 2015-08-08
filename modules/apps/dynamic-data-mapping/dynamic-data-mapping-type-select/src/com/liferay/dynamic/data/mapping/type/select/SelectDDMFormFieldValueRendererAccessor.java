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

package com.liferay.dynamic.data.mapping.type.select;

import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.registry.DDMFormFieldValueAccessor;
import com.liferay.dynamic.data.mapping.registry.DDMFormFieldValueRendererAccessor;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

/**
 * @author Renato Rego
 */
public class SelectDDMFormFieldValueRendererAccessor
	extends DDMFormFieldValueRendererAccessor {

	public SelectDDMFormFieldValueRendererAccessor(
		DDMFormFieldValueAccessor<JSONArray> ddmFormFieldValueAccessor) {

		_ddmFormFieldValueAccessor = ddmFormFieldValueAccessor;
	}

	@Override
	public String get(DDMFormFieldValue ddmFormFieldValue) {
		JSONArray optionsValuesJSONArray = _ddmFormFieldValueAccessor.get(
			ddmFormFieldValue);

		DDMFormFieldOptions ddmFormFieldOptions = getDDMFormFieldOptions(
			ddmFormFieldValue);

		StringBundler sb = new StringBundler(
			optionsValuesJSONArray.length() * 2);

		for (int i = 0; i < optionsValuesJSONArray.length(); i++) {
			if (i > 0) {
				sb.append(StringPool.COMMA_AND_SPACE);
			}

			LocalizedValue optionLabel = ddmFormFieldOptions.getOptionLabels(
				optionsValuesJSONArray.getString(i));

			sb.append(
				optionLabel.getString(_ddmFormFieldValueAccessor.getLocale()));
		}

		return sb.toString();
	}

	protected DDMFormFieldOptions getDDMFormFieldOptions(
		DDMFormFieldValue ddmFormFieldValue) {

		DDMFormField ddmFormField = ddmFormFieldValue.getDDMFormField();

		return ddmFormField.getDDMFormFieldOptions();
	}

	private final DDMFormFieldValueAccessor<JSONArray>
		_ddmFormFieldValueAccessor;

}