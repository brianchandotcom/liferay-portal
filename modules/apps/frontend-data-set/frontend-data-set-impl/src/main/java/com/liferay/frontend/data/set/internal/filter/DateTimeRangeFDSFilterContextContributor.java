/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.filter;

import com.liferay.frontend.data.set.filter.BaseDateTimeRangeFDSFilter;
import com.liferay.frontend.data.set.filter.DateTimeFDSFilterItem;
import com.liferay.frontend.data.set.filter.FDSFilter;
import com.liferay.frontend.data.set.filter.FDSFilterContextContributor;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Mikel Lorza
 */
@Component(
	property = "frontend.data.set.filter.type=dateTimeRange",
	service = FDSFilterContextContributor.class
)
public class DateTimeRangeFDSFilterContextContributor
	implements FDSFilterContextContributor {

	@Override
	public Map<String, Object> getFDSFilterContext(
		FDSFilter fdsFilter, Locale locale) {

		if (fdsFilter instanceof BaseDateTimeRangeFDSFilter) {
			return _serialize((BaseDateTimeRangeFDSFilter)fdsFilter);
		}

		return Collections.emptyMap();
	}

	private JSONObject _getJSONObject(
		DateTimeFDSFilterItem dateTimeFDSFilterItem) {

		return JSONUtil.put(
			"day", dateTimeFDSFilterItem.getDay()
		).put(
			"hour", dateTimeFDSFilterItem.getHour()
		).put(
			"minute", dateTimeFDSFilterItem.getMinute()
		).put(
			"month", dateTimeFDSFilterItem.getMonth()
		).put(
			"year", dateTimeFDSFilterItem.getYear()
		);
	}

	private Map<String, Object> _serialize(
		BaseDateTimeRangeFDSFilter baseDateTimeRangeFDSFilter) {

		return HashMapBuilder.<String, Object>put(
			"max",
			_getJSONObject(
				baseDateTimeRangeFDSFilter.getMaxDateTimeFDSFilterItem())
		).put(
			"min",
			_getJSONObject(
				baseDateTimeRangeFDSFilter.getMinDateTimeFDSFilterItem())
		).build();
	}

}