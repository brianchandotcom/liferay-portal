/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.odata.entity;

import java.util.Locale;
import java.util.function.Function;

/**
 * Models an date entity field. A Entity field with a type {@code
 * EntityField.Type.DOUBLE}
 *
 * @author Cristina González
 * @review
 */
public class DoubleEntityField extends EntityField {

	/**
	 * Creates a new {@code DoubleEntityField} with a {@code Function} to
	 * convert the entity field's name to a filterable/sortable field name for a
	 * locale.
	 *
	 * @param  name the entity field's name
	 * @param  filterableAndSortableFieldNameFunction the {@code Function}
	 * @review
	 */
	public DoubleEntityField(
		String name,
		Function<Locale, String> filterableAndSortableFieldNameFunction) {

		super(
			name, Type.DOUBLE, filterableAndSortableFieldNameFunction,
			filterableAndSortableFieldNameFunction, String::valueOf);
	}

}