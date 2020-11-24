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

package com.liferay.frontend.taglib.clay.internal.data.set.filter;

import com.liferay.frontend.taglib.clay.data.set.filter.BaseDateRangeClayDataSetFilter;
import com.liferay.frontend.taglib.clay.data.set.filter.ClayDataSetFilter;
import com.liferay.frontend.taglib.clay.data.set.filter.ClayDataSetFilterContextContributor;
import com.liferay.frontend.taglib.clay.data.set.filter.DateClayDataSetFilterItem;
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
	property = "clay.data.set.filter.type=dateRange",
	service = ClayDataSetFilterContextContributor.class
)
public class DateRangeClayDataSetFilterContextContributor
	implements ClayDataSetFilterContextContributor {

	@Override
	public Map<String, Object> getClayDataSetFilterContext(
		ClayDataSetFilter clayDataSetFilter, Locale locale) {

		if (clayDataSetFilter instanceof BaseDateRangeClayDataSetFilter) {
			return _serialize(
				(BaseDateRangeClayDataSetFilter)clayDataSetFilter);
		}

		return Collections.emptyMap();
	}

	private JSONObject _getJSONObject(
		DateClayDataSetFilterItem dateClayDataSetFilterItem) {

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		jsonObject.put(
			"day", dateClayDataSetFilterItem.getDay()
		).put(
			"month", dateClayDataSetFilterItem.getMonth()
		).put(
			"year", dateClayDataSetFilterItem.getYear()
		);

		return jsonObject;
	}

	private Map<String, Object> _serialize(
		BaseDateRangeClayDataSetFilter baseDateRangeClayDataSetFilter) {

		return HashMapBuilder.<String, Object>put(
			"max", _getJSONObject(baseDateRangeClayDataSetFilter.getMax())
		).put(
			"min", _getJSONObject(baseDateRangeClayDataSetFilter.getMin())
		).build();
	}

	@Reference
	private JSONFactory _jsonFactory;

}