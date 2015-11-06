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

package com.liferay.dynamic.data.mapping.type.date;

import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.registry.DDMFormFieldValueRenderer;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.text.Format;

import java.util.Locale;

import javax.xml.bind.DatatypeConverter;

import org.osgi.service.component.annotations.Component;

/**
 * @author Bruno Basto
 */
@Component(immediate = true, property = {"ddm.form.field.type.name=date"})
public class DateDDMFormFieldValueRenderer
	implements DDMFormFieldValueRenderer {

	@Override
	public String render(DDMFormFieldValue ddmFormFieldValue, Locale locale) {
		DDMFormField ddmFormField = ddmFormFieldValue.getDDMFormField();

		String formattedDate = StringPool.BLANK;

		String mask = StringUtil.toLowerCase(
			(String)ddmFormField.getProperty("mask"));

		Value value = ddmFormFieldValue.getValue();

		String valueString = value.getString(locale);

		if (Validator.isNotNull(valueString)) {
			String simpleDateFormatPattern = _SIMPLE_DATE_FORMAT_PATTERN_MDY;

			if (mask.indexOf("%y") == 0) {
				simpleDateFormatPattern = _SIMPLE_DATE_FORMAT_PATTERN_YMD;
			}
			else if (mask.indexOf("%d") == 0) {
				simpleDateFormatPattern = _SIMPLE_DATE_FORMAT_PATTERN_DMY;
			}

			Format format = FastDateFormatFactoryUtil.getSimpleDateFormat(
				simpleDateFormatPattern, locale);

			formattedDate = format.format(
				DatatypeConverter.parseDateTime(valueString));
		}

		return formattedDate;
	}

	private static final String _SIMPLE_DATE_FORMAT_PATTERN_DMY = "dd/MM/yyyy";

	private static final String _SIMPLE_DATE_FORMAT_PATTERN_MDY = "MM/dd/yyyy";

	private static final String _SIMPLE_DATE_FORMAT_PATTERN_YMD = "yyyy/MM/dd";

}