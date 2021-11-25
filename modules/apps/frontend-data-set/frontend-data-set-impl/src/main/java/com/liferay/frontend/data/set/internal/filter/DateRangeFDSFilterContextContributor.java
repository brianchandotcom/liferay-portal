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

package com.liferay.frontend.data.set.internal.filter;

import com.liferay.frontend.data.set.filter.BaseDateRangeFDSFilter;
import com.liferay.frontend.data.set.filter.DateFDSFilterItem;
import com.liferay.frontend.data.set.filter.FDSFilter;
import com.liferay.frontend.data.set.filter.FDSFilterContextContributor;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luca Pellizzon
 */
@Component(
	property = "frontend.data.set.filter.type=dateRange",
	service = FDSFilterContextContributor.class
)
public class DateRangeFDSFilterContextContributor
	implements FDSFilterContextContributor {

	@Override
	public Map<String, Object> getFDSFilterContext(
		FDSFilter fdsFilter, Locale locale) {

		if (fdsFilter instanceof BaseDateRangeFDSFilter) {
			return _serialize((BaseDateRangeFDSFilter)fdsFilter);
		}

		return Collections.emptyMap();
	}

	private JSONObject _getJSONObject(DateFDSFilterItem dateFDSFilterItem) {
		JSONObject jsonObject = _jsonFactory.createJSONObject();

		jsonObject.put(
			"day", dateFDSFilterItem.getDay()
		).put(
			"month", dateFDSFilterItem.getMonth()
		).put(
			"year", dateFDSFilterItem.getYear()
		);

		return jsonObject;
	}

	private Map<String, Object> _serialize(
		BaseDateRangeFDSFilter baseDateRangeFDSFilter) {

		return HashMapBuilder.<String, Object>put(
			"max",
			_getJSONObject(baseDateRangeFDSFilter.getMaxDateFDSFilterItem())
		).put(
			"min",
			_getJSONObject(baseDateRangeFDSFilter.getMinDateFDSFilterItem())
		).build();
	}

	@Reference
	private JSONFactory _jsonFactory;

}