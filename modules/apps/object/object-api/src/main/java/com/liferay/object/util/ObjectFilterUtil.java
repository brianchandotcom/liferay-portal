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

import com.liferay.object.model.ObjectFilter;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.vulcan.util.ObjectMapperUtil;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

	public static List<String> getODataFilters(
		List<ObjectFilter> objectFilters) {

		// TODO Create filterParser classes for each one of filter types
		// and use tracker or registry to get them

		List<String> oDataFilters = new ArrayList<>();

		if (objectFilters == null) {
			return oDataFilters;
		}

		for (ObjectFilter objectFilter : objectFilters) {
			Map<String, Object> objectFilterMap = ObjectMapperUtil.readValue(
				Map.class, objectFilter.getJSON());

			if (objectFilterMap == null) {
				continue;
			}

			Set<String> operators = objectFilterMap.keySet();

			for (String operator : operators) {
				Object object = objectFilterMap.get(operator);

				if (object instanceof Object[]) {
					String[] values = TransformUtil.transform(
						(Object[])object,
						value -> StringBundler.concat(
							StringPool.APOSTROPHE, value,
							StringPool.APOSTROPHE),
						String.class);

					StringBundler sb = new StringBundler(values.length + 2);

					sb.append(StringPool.OPEN_PARENTHESIS);
					sb.append(ArrayUtil.toString(values, StringPool.BLANK));
					sb.append(StringPool.CLOSE_PARENTHESIS);

					object = sb.toString();
				}

				oDataFilters.add(
					StringBundler.concat(
						objectFilter.getFilterBy(), StringPool.SPACE, operator,
						StringPool.SPACE, object));
			}
		}

		return oDataFilters;
	}

}