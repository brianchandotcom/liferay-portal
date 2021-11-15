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

import com.liferay.frontend.data.set.filter.BaseDateRangeFrontendDataSetFilter;
import com.liferay.frontend.data.set.filter.DateFrontendDataSetFilterItem;
import com.liferay.frontend.data.set.filter.FrontendDataSetFilter;
import com.liferay.frontend.data.set.filter.FrontendDataSetFilterContextContributor;
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
	service = FrontendDataSetFilterContextContributor.class
)
public class DateRangeFrontendDataSetFilterContextContributor
	implements FrontendDataSetFilterContextContributor {

	@Override
	public Map<String, Object> getFrontendDataSetFilterContext(
		FrontendDataSetFilter frontendDataSetFilter, Locale locale) {

		if (frontendDataSetFilter instanceof
				BaseDateRangeFrontendDataSetFilter) {

			return _serialize(
				(BaseDateRangeFrontendDataSetFilter)frontendDataSetFilter);
		}

		return Collections.emptyMap();
	}

	private JSONObject _getJSONObject(
		DateFrontendDataSetFilterItem dateFrontendDataSetFilterItem) {

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		jsonObject.put(
			"day", dateFrontendDataSetFilterItem.getDay()
		).put(
			"month", dateFrontendDataSetFilterItem.getMonth()
		).put(
			"year", dateFrontendDataSetFilterItem.getYear()
		);

		return jsonObject;
	}

	private Map<String, Object> _serialize(
		BaseDateRangeFrontendDataSetFilter baseDateRangeFrontendDataSetFilter) {

		return HashMapBuilder.<String, Object>put(
			"max",
			_getJSONObject(
				baseDateRangeFrontendDataSetFilter.
					getMaxDateFrontendDataSetFilterItem())
		).put(
			"min",
			_getJSONObject(
				baseDateRangeFrontendDataSetFilter.
					getMinDateFrontendDataSetFilterItem())
		).build();
	}

	@Reference
	private JSONFactory _jsonFactory;

}