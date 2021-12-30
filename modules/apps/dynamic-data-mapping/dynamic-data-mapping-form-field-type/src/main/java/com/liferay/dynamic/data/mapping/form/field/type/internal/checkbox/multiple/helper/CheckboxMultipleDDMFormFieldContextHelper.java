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

package com.liferay.dynamic.data.mapping.form.field.type.internal.checkbox.multiple.helper;

import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Dylan Rebelak
 */
public class CheckboxMultipleDDMFormFieldContextHelper {

	public CheckboxMultipleDDMFormFieldContextHelper(
		JSONFactory jsonFactory, DDMFormFieldOptions ddmFormFieldOptions,
		Locale locale) {

		_jsonFactory = jsonFactory;
		_ddmFormFieldOptions = ddmFormFieldOptions;
		_locale = locale;
	}

	public List<Object> getOptions(
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		List<Object> options = new ArrayList<>();

		for (String optionValue : _ddmFormFieldOptions.getOptionsValues()) {
			options.add(
				HashMapBuilder.put(
					"label",
					() -> {
						LocalizedValue optionLabel =
							_ddmFormFieldOptions.getOptionLabels(optionValue);

						return optionLabel.getString(_locale);
					}
				).put(
					"reference",
					_ddmFormFieldOptions.getOptionReference(optionValue)
				).put(
					"value", optionValue
				).build());
		}

		return options;
	}

	private final DDMFormFieldOptions _ddmFormFieldOptions;
	private final JSONFactory _jsonFactory;
	private final Locale _locale;

}