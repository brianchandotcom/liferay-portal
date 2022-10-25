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

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTemplateContextContributor;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.form.field.type.internal.util.DDMFormFieldTypeUtil;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.CalendarUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.time.DayOfWeek;
import java.time.temporal.WeekFields;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(
	property = {
		"ddm.form.field.type.name=" + DDMFormFieldTypeConstants.DATE,
		"ddm.form.field.type.name=" + DDMFormFieldTypeConstants.DATE_TIME
	},
	service = {
		DateDDMFormFieldTemplateContextContributor.class,
		DDMFormFieldTemplateContextContributor.class
	}
)
public class DateDDMFormFieldTemplateContextContributor
	implements DDMFormFieldTemplateContextContributor {

	@Override
	public Map<String, Object> getParameters(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		return HashMapBuilder.<String, Object>put(
			"firstDayOfWeek",
			_getFirstDayOfWeek(ddmFormFieldRenderingContext.getLocale())
		).put(
			"months",
			Arrays.asList(
				CalendarUtil.getMonths(
					ddmFormFieldRenderingContext.getLocale()))
		).put(
			"predefinedValue",
			DDMFormFieldTypeUtil.getPropertyValue(
				ddmFormField, ddmFormFieldRenderingContext.getLocale(),
				"predefinedValue")
		).put(
			"tooltip",
			DDMFormFieldTypeUtil.getPropertyValue(
				ddmFormField, ddmFormFieldRenderingContext.getLocale(),
				"tooltip")
		).put(
			"weekdaysShort",
			Stream.of(
				CalendarUtil.DAYS_ABBREVIATION
			).map(
				day -> _language.get(
					ddmFormFieldRenderingContext.getLocale(), day)
			).collect(
				Collectors.toList()
			)
		).put(
			"years", _getYears()
		).build();
	}

	private int _getFirstDayOfWeek(Locale locale) {
		WeekFields weekFields = WeekFields.of(locale);

		DayOfWeek dayOfWeek = weekFields.getFirstDayOfWeek();

		return dayOfWeek.getValue() % 7;
	}

	private List<Integer> _getYears() {
		List<Integer> years = new ArrayList<>();

		Calendar calendar = Calendar.getInstance();

		calendar.add(Calendar.YEAR, -4);

		for (int i = 0; i < 5; i++) {
			years.add(calendar.get(Calendar.YEAR));

			calendar.add(Calendar.YEAR, 1);
		}

		return years;
	}

	@Reference
	private Language _language;

}