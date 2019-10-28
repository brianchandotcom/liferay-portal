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

package com.liferay.dynamic.data.mapping.form.field.type.internal.grid;

import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.portal.kernel.util.HtmlUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Pedro Queiroz
 */
public class GridDDMFormFieldContextHelper {

	public GridDDMFormFieldContextHelper(
		DDMFormFieldOptions ddmFormFieldOptions, Locale locale) {

		_ddmFormFieldOptions = ddmFormFieldOptions;
		_locale = locale;
	}

	public List<Object> getOptions(
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		List<Object> options = new ArrayList<>();

		if (_ddmFormFieldOptions == null) {
			return options;
		}

		for (String optionValue : _ddmFormFieldOptions.getOptionsValues()) {
			LocalizedValue optionLabel = _ddmFormFieldOptions.getOptionLabels(
				optionValue);

			String optionLabelString = optionLabel.getString(_locale);

			if (ddmFormFieldRenderingContext.isViewMode()) {
				optionLabelString = HtmlUtil.extractText(optionLabelString);
			}

			Map<String, String> optionMap = new HashMap<>();

			optionMap.put("label", optionLabelString);

			optionMap.put("value", optionValue);

			options.add(optionMap);
		}

		return options;
	}

	private final DDMFormFieldOptions _ddmFormFieldOptions;
	private final Locale _locale;

}