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

package com.liferay.frontend.taglib.chart.model;

import java.util.Collection;
import java.util.List;

/**
 * @author Julien Castelain
 */
public class MixedDataColumn extends Column {

	/**
	 * Create a new MixedDataColumn.
	 *
	 * @param id The id {@link String} of the column
	 * @param values each value can be an instance, array, or {@link Collection} of {@link Number}s
	 * @review
	 */
	public MixedDataColumn(String id, Collection<?> values) {
		super(id);

		addValues(values);
	}

	/**
	 * Create a new MixedDataColumn.
	 *
	 * @param id The id {@link String} of the column
	 * @param values each value can be an instance, array, or {@link Collection} of {@link Number}s
	 * @review
	 */
	public MixedDataColumn(String id, Object... values) {
		super(id);

		addValues(values);
	}

	/**
	 * Add a value to this column.
	 *
	 * @param value can be an instance, array, or {@link Collection} of {@link Number}s
	 * @review
	 */
	public void addValue(Object value) {
		List<Object> data = getData();

		Class<?> clazz = value.getClass();

		if (value instanceof Number) {
			data.add(value);
		}
		else if (value instanceof Collection) {
			data.add(((Collection)value).toArray());
		}
		else if (clazz.isArray()) {
			data.add(value);
		}
		else {
			throw new IllegalArgumentException(
				"Value can only be an instance, array, or collection of " +
					"numbers");
		}
	}

	/**
	 * Add one or several values to this column.
	 *
	 * @param values a {@link Collection} of {@link Number}s
	 * @review
	 */
	public void addValues(Collection<?> values) {
		for (Object value : values) {
			addValue(value);
		}
	}

	/**
	 * Add one or several values to this column.
	 *
	 * @param values an array of {@link Number}s
	 * @review
	 */
	public void addValues(Object... values) {
		for (Object value : values) {
			addValue(value);
		}
	}

}