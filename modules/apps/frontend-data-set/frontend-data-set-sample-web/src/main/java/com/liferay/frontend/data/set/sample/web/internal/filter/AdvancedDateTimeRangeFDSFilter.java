/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.sample.web.internal.filter;

import com.liferay.frontend.data.set.filter.BaseDateTimeRangeFDSFilter;
import com.liferay.frontend.data.set.filter.DateTimeFDSFilterItem;
import com.liferay.frontend.data.set.filter.FDSFilter;
import com.liferay.frontend.data.set.sample.web.internal.constants.FDSSampleFDSNames;

import org.osgi.service.component.annotations.Component;

/**
 * @author Mikel Lorza
 */
@Component(
	property = "frontend.data.set.name=" + FDSSampleFDSNames.ADVANCED,
	service = FDSFilter.class
)
public class AdvancedDateTimeRangeFDSFilter extends BaseDateTimeRangeFDSFilter {

	@Override
	public String getId() {
		return "dateTime";
	}

	@Override
	public String getLabel() {
		return "date-time-range";
	}

	@Override
	public DateTimeFDSFilterItem getMaxDateTimeFDSFilterItem() {
		return new DateTimeFDSFilterItem(0, 0, 0, 0, 0);
	}

	@Override
	public DateTimeFDSFilterItem getMinDateTimeFDSFilterItem() {
		return new DateTimeFDSFilterItem(0, 0, 0, 0, 0);
	}

}