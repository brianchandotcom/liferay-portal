/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	/**
	 * Creates a new {@code StringEntityField} with a {@code Function} to
	 * convert the entity field's name to a filterable/sortable field name for a
	 * locale.
	 *
	 * @param  name the entity field's name
	 * @param  sortableFieldNameFunction the sortable field name {@code
	 *         Function}
	 * @param  filterableFieldNameFunction the filterable field name {@code
	 *         Function}
	 * @review
	 */
	public StringEntityField(
		String name, Function<Locale, String> sortableFieldNameFunction,
		Function<Locale, String> filterableFieldNameFunction) {

		super(
			name, Type.STRING, sortableFieldNameFunction,
			filterableFieldNameFunction, String::valueOf);
	}

}