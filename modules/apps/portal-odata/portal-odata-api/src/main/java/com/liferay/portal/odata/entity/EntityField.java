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

package com.liferay.portal.odata.entity;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;
import java.util.function.Function;

/**
 * Models an entity field.
 *
 * @author Cristina González
 * @review
 */
public class EntityField {

	/**
	 * Creates a new {@code EntityField} with separate functions for converting
	 * the entity field's name to a sortable and filterable field name for a
	 * locale.
	 *
	 * @param  name the entity field's name
	 * @param  type the type
	 * @param  sortableFieldNameFunction the sortable field name {@code
	 *         Function}
	 * @param  filterableFieldNameFunction the filterable field name {@code
	 *         Function}
	 * @param  filterableFieldValueFunction the filterable field value {@code
	 *         Function}
	 * @review
	 */
	public EntityField(
		String name, Type type,
		Function<Locale, String> sortableFieldNameFunction,
		Function<Locale, String> filterableFieldNameFunction,
		Function<Object, String> filterableFieldValueFunction) {

		if (Validator.isNull(name)) {
			throw new IllegalArgumentException("Name is null");
		}

		if (type == null) {
			throw new IllegalArgumentException("Type is null");
		}

		if (sortableFieldNameFunction == null) {
			throw new IllegalArgumentException(
				"Sortable field name function is null");
		}

		if (filterableFieldNameFunction == null) {
			throw new IllegalArgumentException(
				"Filterable field name function is null");
		}

		if (filterableFieldValueFunction == null) {
			throw new IllegalArgumentException(
				"Filterable field value function is null");
		}

		_name = name;
		_type = type;
		_sortableFieldNameFunction = sortableFieldNameFunction;
		_filterableFieldNameFunction = filterableFieldNameFunction;
		_filterableFieldValueFunction = filterableFieldValueFunction;
	}

	/**
	 * Returns the entity field's filterable name.
	 *
	 * @param  locale the locale
	 * @return the filterable name
	 * @review
	 */
	public String getFilterableName(Locale locale) {
		return _filterableFieldNameFunction.apply(locale);
	}

	/**
	 * Returns the entity field's filterable value.
	 *
	 * @param  fieldValue the field value
	 * @return the filterable field value
	 * @review
	 */
	public String getFilterableValue(Object fieldValue) {
		return _filterableFieldValueFunction.apply(fieldValue);
	}

	/**
	 * Returns the entity field's name.
	 *
	 * @return the name
	 * @review
	 */
	public String getName() {
		return _name;
	}

	/**
	 * Returns the entity field's sortable name for a locale.
	 *
	 * @param  locale the locale
	 * @return the sortable name
	 * @review
	 */
	public String getSortableName(Locale locale) {
		return _sortableFieldNameFunction.apply(locale);
	}

	/**
	 * Returns the entity field's type.
	 *
	 * @return the type
	 * @review
	 */
	public Type getType() {
		return _type;
	}

	@Override
	public String toString() {
		return StringBundler.concat(
			"{name:", _name, ", type:", _type.name(), "}");
	}

	public enum Type {

		BOOLEAN, COLLECTION, COMPLEX, DATE, DATE_TIME, DOUBLE, ID, INTEGER,
		STRING

	}

	private final Function<Locale, String> _filterableFieldNameFunction;
	private final Function<Object, String> _filterableFieldValueFunction;
	private final String _name;
	private final Function<Locale, String> _sortableFieldNameFunction;
	private final Type _type;

}