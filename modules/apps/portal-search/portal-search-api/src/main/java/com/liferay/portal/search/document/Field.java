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

package com.liferay.portal.search.document;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael C. Han
 */
public class Field {

	public Field(String name) {
		_name = name;
	}

	public void addValue(Object value) {
		_values.add(value);
	}

	public void addValues(List<Object> values) {
		_values.addAll(values);
	}

	public String getName() {
		return _name;
	}

	public Object getValue() {
		if (_values.isEmpty()) {
			return null;
		}

		return _values.get(0);
	}

	public List<Object> getValues() {
		return _values;
	}

	private final String _name;
	private List<Object> _values = new ArrayList<>();

}