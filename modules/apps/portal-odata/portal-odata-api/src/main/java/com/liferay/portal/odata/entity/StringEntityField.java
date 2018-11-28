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

import java.util.Locale;
import java.util.function.Function;

/**
 * Models a string entity field. An Entity field with a type {@code
 * EntityField.Type.STRING}
 *
 * @author Cristina González
 * @review
 */
public class StringEntityField extends EntityField {

	/**
	 * Creates a new {@code StringEntityField} with a {@code Function} to
	 * convert the entity field's name to a filterable/sortable field name for a
	 * locale.
	 *
	 * @param  name the entity field's name
	 * @param  filterableAndSortableFieldNameFunction the {@code Function}
	 * @review
	 */
	public StringEntityField(
		String name,
		Function<Locale, String> filterableAndSortableFieldNameFunction) {

		super(
			name, Type.STRING, filterableAndSortableFieldNameFunction,
			filterableAndSortableFieldNameFunction, String::valueOf);
	}

}