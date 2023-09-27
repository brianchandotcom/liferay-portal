/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.filter.util;

import com.liferay.object.model.ObjectFilter;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.vulcan.util.ObjectMapperUtil;

import java.util.List;
import java.util.Map;

/**
 * @author Marcela Cunha
 */
public class ObjectFilterUtil {

	public static JSONArray getObjectFiltersJSONArray(
		List<ObjectFilter> objectFilters) {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (ObjectFilter objectFilter : objectFilters) {
			jsonArray.put(
				JSONUtil.put(
					"filterBy", objectFilter.getFilterBy()
				).put(
					"filterType", objectFilter.getFilterType()
				).put(
					"json",
					(Map)ObjectMapperUtil.readValue(
						Map.class, objectFilter.getJSON())
				));
		}

		return jsonArray;
	}

}