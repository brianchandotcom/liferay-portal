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

package com.liferay.portal.kernel.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Miguel Angelo Caldas Gallindo
 */
public class FieldArray extends Field {

	public FieldArray(String name) {
		super(name);
	}

	public FieldArray(String name, Map<Locale, String> localizedValues) {
		super(name, localizedValues);
	}

	public FieldArray(String name, String value) {
		super(name, value);
	}

	public FieldArray(String name, String[] values) {
		super(name, values);
	}

	@Override
	public void addField(Field field) {
		_fields.add(field);
	}

	@Override
	public List<Field> getFields() {
		return _fields;
	}

	@Override
	public boolean isArray() {
		return true;
	}

	private final List<Field> _fields = new ArrayList<>();

}