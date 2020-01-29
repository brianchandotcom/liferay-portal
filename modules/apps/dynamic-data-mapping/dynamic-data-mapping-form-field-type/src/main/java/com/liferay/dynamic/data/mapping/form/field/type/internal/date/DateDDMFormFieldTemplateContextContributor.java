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
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.CalendarUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleThreadLocal;

import java.time.DayOfWeek;
import java.time.temporal.WeekFields;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marcellus Tavares
 */
@Component(
	immediate = true, property = "ddm.form.field.type.name=date",
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
			"firstDayOfWeek", _getFirstDayOfWeek()
		).put(
			"months",
			Arrays.asList(
				CalendarUtil.getMonths(
					LocaleThreadLocal.getThemeDisplayLocale()))
		).put(
			"predefinedValue",
			_getPredefinedValue(ddmFormField, ddmFormFieldRenderingContext)
		).put(
			"weekdaysShort",
			Stream.of(
				CalendarUtil.DAYS_ABBREVIATION
			).map(
				day -> LanguageUtil.get(
					LocaleThreadLocal.getThemeDisplayLocale(), day)
			).collect(
				Collectors.toList()
			)
		).put(
			"years", _getYears()
		).build();
	}

	private int _getFirstDayOfWeek() {
		WeekFields weekFields = WeekFields.of(
			LocaleThreadLocal.getThemeDisplayLocale());

		DayOfWeek dayOfWeek = weekFields.getFirstDayOfWeek();

		return dayOfWeek.getValue() % 7;
	}

	private String _getPredefinedValue(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		LocalizedValue predefinedValue = ddmFormField.getPredefinedValue();

		if (predefinedValue == null) {
			return null;
		}

		return GetterUtil.getString(
			predefinedValue.getString(
				ddmFormFieldRenderingContext.getLocale()));
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

}