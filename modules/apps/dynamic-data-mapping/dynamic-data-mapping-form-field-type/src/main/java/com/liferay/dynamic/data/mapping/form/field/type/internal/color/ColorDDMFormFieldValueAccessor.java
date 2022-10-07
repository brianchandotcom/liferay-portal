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

package com.liferay.dynamic.data.mapping.form.field.type.internal.color;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueAccessor;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Locale;
import java.util.function.IntFunction;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = "ddm.form.field.type.name=" + DDMFormFieldTypeConstants.COLOR,
	service = {
		ColorDDMFormFieldValueAccessor.class, DDMFormFieldValueAccessor.class
	}
)
public class ColorDDMFormFieldValueAccessor
	implements DDMFormFieldValueAccessor<String> {

	@Override
	public IntFunction<String[]> getArrayGeneratorIntFunction() {
		return String[]::new;
	}

	@Override
	public String getValue(DDMFormFieldValue ddmFormFieldValue, Locale locale) {
		return _getValue(ddmFormFieldValue, locale);
	}

	@Override
	public String getValueForEvaluation(
		DDMFormFieldValue ddmFormFieldValue, Locale locale) {

		return _getValue(ddmFormFieldValue, locale);
	}

	private String _getValue(
		DDMFormFieldValue ddmFormFieldValue, Locale locale) {

		Value value = ddmFormFieldValue.getValue();

		if (value == null) {
			return StringPool.BLANK;
		}

		return StringUtil.removeSubstring(
			value.getString(locale), StringPool.POUND);
	}

}