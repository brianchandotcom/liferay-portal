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

package com.liferay.users.admin.web.internal.display.context;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.model.OrgLabor;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.CalendarUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.text.Format;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * @author Drew Brokke
 */
public class OrgLaborFormDisplay {

	public OrgLaborFormDisplay(Locale locale, OrgLabor orgLabor) {
		_timeFormat = FastDateFormatFactoryUtil.getSimpleDateFormat(
			"HH:mm", locale);

		_dayRowDisplays = _buildDayRowDisplays(locale, orgLabor);
	}

	public List<DayRowDisplay> getDayRowDisplays() {
		return _dayRowDisplays;
	}

	public class DayRowDisplay {

		public DayRowDisplay(
			String longDayName, String shortDayName,
			List<SelectOptionDisplay> closeSelectOptionDisplays,
			List<SelectOptionDisplay> openSelectOptionDisplays) {

			_longDayName = longDayName;
			_shortDayName = shortDayName;
			_closeSelectOptionDisplays = closeSelectOptionDisplays;
			_openSelectOptionDisplays = openSelectOptionDisplays;
		}

		public List<SelectOptionDisplay> getCloseSelectOptionDisplays() {
			return _closeSelectOptionDisplays;
		}

		public String getLongDayName() {
			return _longDayName;
		}

		public List<SelectOptionDisplay> getOpenSelectOptionDisplays() {
			return _openSelectOptionDisplays;
		}

		public String getShortDayName() {
			return _shortDayName;
		}

		private final List<SelectOptionDisplay> _closeSelectOptionDisplays;
		private final String _longDayName;
		private final List<SelectOptionDisplay> _openSelectOptionDisplays;
		private final String _shortDayName;

	}

	public class SelectOptionDisplay {

		public SelectOptionDisplay(String label, int value, boolean selected) {
			_label = label;
			_value = value;
			_selected = selected;
		}

		public String getLabel() {
			return _label;
		}

		public int getValue() {
			return _value;
		}

		public boolean isSelected() {
			return _selected;
		}

		private final String _label;
		private final boolean _selected;
		private final int _value;

	}

	private List<DayRowDisplay> _buildDayRowDisplays(
		Locale locale, OrgLabor orgLabor) {

		List<DayRowDisplay> dayRowDisplays = new ArrayList<>();

		String[] days = CalendarUtil.getDays(locale);

		for (int i = 0; i < _SHORT_DAY_NAMES.length; i++) {
			String longDayName = days[i];

			String shortDayName = _SHORT_DAY_NAMES[i];

			List<SelectOptionDisplay> closeSelectOptionDisplays =
				_buildSelectOptionDisplays(
					locale,
					BeanPropertiesUtil.getInteger(
						orgLabor, shortDayName + "Close", -1));
			List<SelectOptionDisplay> openSelectOptionDisplays =
				_buildSelectOptionDisplays(
					locale,
					BeanPropertiesUtil.getInteger(
						orgLabor, shortDayName + "Open", -1));

			dayRowDisplays.add(
				new DayRowDisplay(
					longDayName, shortDayName, closeSelectOptionDisplays,
					openSelectOptionDisplays));
		}

		return dayRowDisplays;
	}

	private List<SelectOptionDisplay> _buildSelectOptionDisplays(
		Locale locale, int time) {

		List<SelectOptionDisplay> selectOptionDisplays = new ArrayList<>();

		Format timeFormat = FastDateFormatFactoryUtil.getSimpleDateFormat(
			"HH:mm", locale);

		Calendar calendar = CalendarFactoryUtil.getCalendar();

		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		int today = calendar.get(Calendar.DATE);

		while (calendar.get(Calendar.DATE) == today) {
			int timeOfDayValue = GetterUtil.getInteger(
				StringUtil.replace(
					timeFormat.format(calendar.getTime()), CharPool.COLON,
					StringPool.BLANK));

			selectOptionDisplays.add(
				new SelectOptionDisplay(
					timeFormat.format(calendar.getTime()), timeOfDayValue,
					time == timeOfDayValue));

			calendar.add(Calendar.MINUTE, 30);
		}

		return selectOptionDisplays;
	}

	private static final String[] _SHORT_DAY_NAMES = {
		"sun", "mon", "tue", "wed", "thu", "fri", "sat"
	};

	private final List<DayRowDisplay> _dayRowDisplays;
	private final Format _timeFormat;

}