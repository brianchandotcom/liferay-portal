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