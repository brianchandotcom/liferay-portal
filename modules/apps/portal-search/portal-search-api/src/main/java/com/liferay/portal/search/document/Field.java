/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.document;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author Michael C. Han
 * @author André de Oliveira
 */
public class Field {

	public Field(Field field) {
		_name = field._name;
		_values = new ArrayList(field._values);
	}

	public Field(String name, Collection<Object> values) {
		_name = name;
		_values = new ArrayList(values);
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
		return Collections.unmodifiableList(_values);
	}

	@Override
	public String toString() {
		if (_values.size() == 1) {
			return String.valueOf(_values.get(0));
		}

		return String.valueOf(_values);
	}

	private final String _name;
	private final List<Object> _values;

}