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

package com.liferay.frontend.taglib.data.set.display.sample.web.internal.filter;

import com.liferay.frontend.taglib.clay.data.set.filter.BaseDateRangeClayDataSetFilter;
import com.liferay.frontend.taglib.clay.data.set.filter.ClayDataSetFilter;
import com.liferay.frontend.taglib.clay.data.set.filter.DateClayDataSetFilterItem;

import java.util.Calendar;

import org.osgi.service.component.annotations.Component;

/**
 * @author Javier de Arcos
 */
@Component(
	property = "clay.data.set.display.name=dataSetDisplay",
	service = ClayDataSetFilter.class
)
public class DateRangeClayTableDataSetFilter
	extends BaseDateRangeClayDataSetFilter {

	@Override
	public String getId() {
		return "date";
	}

	@Override
	public String getLabel() {
		return "Date Range";
	}

	public DateClayDataSetFilterItem getMaxDateClayDataSetFilterItem() {
		Calendar calendar = Calendar.getInstance();

		return new DateClayDataSetFilterItem(
			calendar.get(Calendar.DAY_OF_MONTH),
			calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR));
	}

	@Override
	public DateClayDataSetFilterItem getMinDateClayDataSetFilterItem() {
		return new DateClayDataSetFilterItem(0, 0, 0);
	}

}