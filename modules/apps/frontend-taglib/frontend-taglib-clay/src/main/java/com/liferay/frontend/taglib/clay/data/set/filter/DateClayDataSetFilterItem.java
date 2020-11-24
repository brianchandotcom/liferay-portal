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

package com.liferay.frontend.taglib.clay.data.set.filter;

/**
 * @author Luca Pellizzon
 */
public class DateClayDataSetFilterItem {

	public DateClayDataSetFilterItem(int day, int month, int year) {
		_day = day;
		_month = month;
		_year = year;
	}

	public int getDay() {
		return _day;
	}

	public int getMonth() {
		return _month;
	}

	public int getYear() {
		return _year;
	}

	private final int _day;
	private final int _month;
	private final int _year;

}