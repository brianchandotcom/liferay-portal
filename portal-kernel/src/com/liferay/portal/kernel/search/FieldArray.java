/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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