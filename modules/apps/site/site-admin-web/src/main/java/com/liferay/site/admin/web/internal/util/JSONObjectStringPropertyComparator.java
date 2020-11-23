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

package com.liferay.site.admin.web.internal.util;

import com.liferay.portal.kernel.json.JSONObject;

import java.util.Comparator;

/**
 * @author Jürgen Kappler
 */
public class JSONObjectStringPropertyComparator
	implements Comparator<JSONObject> {

	public JSONObjectStringPropertyComparator(
		String property, boolean ascending) {

		_property = property;
		_ascending = ascending;
	}

	@Override
	public int compare(JSONObject jsonObject1, JSONObject jsonObject2) {
		String value1 = jsonObject1.getString(_property);
		String value2 = jsonObject2.getString(_property);

		if (_ascending) {
			return value1.compareTo(value2);
		}

		return -value1.compareTo(value2);
	}

	private final boolean _ascending;
	private final String _property;

}