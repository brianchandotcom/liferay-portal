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

package com.liferay.structured.content.apio.architect.entity;

import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;
import java.util.function.Function;

/**
 * Models a <code>EntityField</code>.
 *
 * @author Cristina González
 * @review
 */
public class EntityField {

	/**
	 * Creates a new <code>EntityField</code>
	 *
	 * @param  name - the name of the EntityField
	 * @param  type - the {@link Type}
	 * @param  sortableFieldNameFunction - the {@link Function} to convert the
	 *         entity field name to a searchable/sortable field name given a
	 *         locale
	 * @review
	 */
	public EntityField(
		String name, Type type,
		Function<Locale, String> sortableFieldNameFunction) {

		if (Validator.isNull(name)) {
			throw new IllegalArgumentException("Name is null");
		}

		if (type == null) {
			throw new IllegalArgumentException("Type is null");
		}

		if (sortableFieldNameFunction == null) {
			throw new IllegalArgumentException(
				"sortableFieldNameFunction is null");
		}

		_name = name;
		_type = type;
		_sortableNameFunction = sortableFieldNameFunction;
	}

	/**
	 * Returns the name of the <code>EntityField</code>
	 *
	 * @return the name of the <code>EntityField</code>
	 */
	public String getName() {
		return _name;
	}

	/**
	 * Returns the sortable name of the <code>EntityField</code>
	 *
	 * @param  locale
	 * @return the sortable name of the <code>EntityField</code>
	 * @review
	 */
	public String getSortableName(Locale locale) {
		return _sortableNameFunction.apply(locale);
	}

	/**
	 * Returns the {@link Type} of the <code>EntityField</code>
	 *
	 * @return the {@link Type}
	 */
	public Type getType() {
		return _type;
	}

	public enum Type {

		DATE, STRING

	}

	private final String _name;
	private final Function<Locale, String> _sortableNameFunction;
	private final Type _type;

}