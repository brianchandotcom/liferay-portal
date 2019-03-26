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

package com.liferay.portal.kernel.json;

import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Shuyang Zhou
 */
public class JSONObjectUtil {

	public static String toOrderedJSONString(JSONObject jsonObject) {
		return toOrderedJSONString(jsonObject.toString());
	}

	public static String toOrderedJSONString(String jsonString) {
		org.json.JSONObject jsonObject = new org.json.JSONObject(jsonString) {

			@Override
			protected Set<Map.Entry<String, Object>> entrySet() {
				Set<Map.Entry<String, Object>> entrySet = new TreeSet<>(
					Comparator.comparing(Map.Entry::getKey));

				entrySet.addAll(super.entrySet());

				return entrySet;
			}

		};

		return jsonObject.toString();
	}

}