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

package com.liferay.jenkins.results.parser;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * @author Kenji Heigel
 */
public class OrderedJSONObject extends JSONObject {

	public OrderedJSONObject() {
	}

	public OrderedJSONObject(JSONObject jsonObject, String[] names) {
		super(jsonObject, names);
	}

	public OrderedJSONObject(JSONTokener jsonTokener) throws JSONException {
		super(jsonTokener);
	}

	public OrderedJSONObject(Map<?, ?> map) {
		super(map);
	}

	public OrderedJSONObject(Object bean) {
		super(bean);
	}

	public OrderedJSONObject(Object object, String[] names) {
		super(object, names);
	}

	public OrderedJSONObject(String source) throws JSONException {
		super(source);
	}

	public OrderedJSONObject(String baseName, Locale locale)
		throws JSONException {

		super(baseName, locale);
	}

	@Override
	public JSONObject put(String key, Object value) throws JSONException {
		_keyOrder.add(key);

		return super.put(key, value);
	}

	@Override
	protected Set<Map.Entry<String, Object>> entrySet() {
		Set<Map.Entry<String, Object>> originalSet = super.entrySet();
		Set<Map.Entry<String, Object>> orderedSet = new LinkedHashSet<>();

		for (String key : _keyOrder) {
			for (Map.Entry<String, Object> entry : originalSet) {
				if (key.equals(entry.getKey())) {
					orderedSet.add(entry);

					break;
				}
			}
		}

		return orderedSet;
	}

	private final List<String> _keyOrder = new ArrayList<>();

}