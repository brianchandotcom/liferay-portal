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

package com.liferay.object.util;

import com.google.gson.Gson;

import com.liferay.object.constants.ObjectFilterConstants;
import com.liferay.object.model.ObjectFilter;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;

import java.util.List;
import java.util.Map;

/**
 * @author Marcela Cunha
 */
public class ObjectFilterUtil {

	public static JSONArray getObjectFiltersJSONArray(
		List<ObjectFilter> objectFilters) {

		Gson gson = new Gson();
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (ObjectFilter objectFilter : objectFilters) {
			jsonArray.put(
				JSONUtil.put(
					ObjectFilterConstants.FILTER_BY, objectFilter.getFilterBy()
				).put(
					ObjectFilterConstants.FILTER_TYPE,
					objectFilter.getFilterType()
				).put(
					ObjectFilterConstants.JSON,
					gson.fromJson(objectFilter.getJson(), Map.class)
				));
		}

		return jsonArray;
	}

}