/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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