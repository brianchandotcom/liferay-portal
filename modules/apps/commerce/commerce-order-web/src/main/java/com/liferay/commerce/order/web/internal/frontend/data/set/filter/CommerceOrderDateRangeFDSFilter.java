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

package com.liferay.commerce.order.web.internal.frontend.data.set.filter;

import com.liferay.commerce.order.web.internal.constants.CommerceOrderFDSNames;
import com.liferay.frontend.data.set.filter.BaseDateRangeFDSFilter;
import com.liferay.frontend.data.set.filter.DateFDSFilterItem;
import com.liferay.frontend.data.set.filter.FDSFilter;

import java.util.Calendar;

import org.osgi.service.component.annotations.Component;

/**
 * @author Luca Pellizzon
 */
@Component(
	enabled = false, immediate = true,
	property = "frontend.data.set.name=" + CommerceOrderFDSNames.ALL_ORDERS,
	service = FDSFilter.class
)
public class CommerceOrderDateRangeFDSFilter extends BaseDateRangeFDSFilter {

	@Override
	public String getId() {
		return "createDate";
	}

	@Override
	public String getLabel() {
		return "order-date-range";
	}

	@Override
	public DateFDSFilterItem getMaxDateFDSFilterItem() {
		Calendar calendar = Calendar.getInstance();

		return new DateFDSFilterItem(
			calendar.get(Calendar.DAY_OF_MONTH),
			calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR));
	}

	@Override
	public DateFDSFilterItem getMinDateFDSFilterItem() {
		return new DateFDSFilterItem(0, 0, 0);
	}

}