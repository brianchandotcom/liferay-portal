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
	 * Creates a new mixed data column.
	 *
	 * @param id the column's ID
	 * @param values the column's values. Each value can be an instance, array,
	 *        or {@link Collection} of {@link Number}s.
	 */
	public MixedDataColumn(String id, Collection<?> values) {
		super(id);

		addValues(values);
	}

	/**
	 * Creates a new mixed data column.
	 *
	 * @param id the column's ID
	 * @param values the column's values. Each value can be an instance or array
	 *        of {@link Number}s.
	 */
	public MixedDataColumn(String id, Object... values) {
		super(id);

		addValues(values);
	}

	/**
	 * Adds a value to the column.
	 *
	 * @param value an array of {@link Number}s for the column values
	 */
	public void addValue(Object value) {
		List<Object> data = getData();

		Class<?> clazz = value.getClass();

		if (value instanceof Number) {
			data.add(value);
		}
		else if (value instanceof Collection) {
			Collection<?> collection = (Collection<?>)value;

			data.add(collection.toArray());
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
	 * Adds one or several values to the column.
	 *
	 * @param values the {@link Number}s for the column values
	 */
	public void addValues(Collection<?> values) {
		for (Object value : values) {
			addValue(value);
		}
	}

	/**
	 * Adds one or several values to the column.
	 *
	 * @param values an array of {@link Number}s for the column values
	 */
	public void addValues(Object... values) {
		for (Object value : values) {
			addValue(value);
		}
	}

}