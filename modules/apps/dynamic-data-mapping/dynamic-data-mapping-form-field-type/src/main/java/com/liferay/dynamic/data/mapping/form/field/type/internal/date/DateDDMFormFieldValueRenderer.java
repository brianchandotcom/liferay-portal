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

package com.liferay.dynamic.data.mapping.form.field.type.internal.date;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueRenderer;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.form.validation.util.DateParameterUtil;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DecimalStyle;

import java.util.Locale;
import java.util.Optional;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import org.osgi.service.component.annotations.Component;

/**
 * @author Bruno Basto
 */
@Component(
	immediate = true,
	property = {
		"ddm.form.field.type.name=" + DDMFormFieldTypeConstants.DATE,
		"ddm.form.field.type.name=" + DDMFormFieldTypeConstants.DATE_TIME
	},
	service = {
		DateDDMFormFieldValueRenderer.class, DDMFormFieldValueRenderer.class
	}
)
public class DateDDMFormFieldValueRenderer
	implements DDMFormFieldValueRenderer {

	@Override
	public String render(DDMFormFieldValue ddmFormFieldValue, Locale locale) {
		Value value = ddmFormFieldValue.getValue();

		return _render(locale, value.getString(locale));
	}

	private String _render(Locale defaultLocale, String valueString) {
		if (Validator.isNull(valueString)) {
			return StringPool.BLANK;
		}

		Locale locale = Optional.ofNullable(
			LocaleThreadLocal.getThemeDisplayLocale()
		).orElse(
			defaultLocale
		);

		SimpleDateFormat simpleDateFormat = null;

		boolean dateTime = Pattern.matches(
			"^\\d{4}-\\d{2}-\\d{2} \\d{1,2}:\\d{2}$", valueString);

		if (dateTime) {
			simpleDateFormat = (SimpleDateFormat)DateFormat.getDateTimeInstance(
				DateFormat.SHORT, DateFormat.SHORT, locale);
		}
		else {
			simpleDateFormat = (SimpleDateFormat)DateFormat.getDateInstance(
				DateFormat.SHORT, locale);
		}

		String pattern = simpleDateFormat.toPattern();

		if (StringUtils.countMatches(pattern, "d") == 1) {
			pattern = StringUtil.replace(pattern, 'd', "dd");
		}

		if (StringUtils.countMatches(pattern, "h") == 1) {
			pattern = StringUtil.replace(pattern, 'h', "hh");
		}

		if (StringUtils.countMatches(pattern, "H") == 1) {
			pattern = StringUtil.replace(pattern, 'H', "HH");
		}

		if (StringUtils.countMatches(pattern, "M") == 1) {
			pattern = StringUtil.replace(pattern, 'M', "MM");
		}

		if (StringUtils.countMatches(pattern, "y") == 2) {
			pattern = StringUtil.replace(pattern, 'y', "yy");
		}

		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(
			pattern, locale);

		if (dateTime) {
			LocalDateTime localDateTime = DateParameterUtil.getLocalDateTime(
				valueString);

			return localDateTime.format(
				dateTimeFormatter.withDecimalStyle(DecimalStyle.of(locale)));
		}

		LocalDate localDate = DateParameterUtil.getLocalDate(valueString);

		return localDate.format(
			dateTimeFormatter.withDecimalStyle(DecimalStyle.of(locale)));
	}

}