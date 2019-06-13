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

package com.liferay.portal.odata.sort;

import com.liferay.portal.odata.entity.EntityField;

import java.io.Serializable;

import java.util.Locale;

/**
 * Models a sort field.
 *
 * @author Cristina González
 * @review
 */
public class SortField implements Serializable {

	/**
	 * Creates a new sort field.
	 *
	 * @param  entityField the entity field
	 * @param  asc whether the sort should be ascending
	 * @review
	 */
	public SortField(EntityField entityField, boolean asc) {
		if (entityField == null) {
			throw new IllegalArgumentException("Entity field is null");
		}

		_entityField = entityField;
		_asc = asc;
		_fieldName = entityField.getName();
	}

	/**
	 * Creates a new sort field not linked to a entityField
	 *
	 * @param  fieldName the entity field name
	 * @param  asc whether the sort should be ascending
	 * @review
	 */
	public SortField(String fieldName, boolean asc) {
		_fieldName = fieldName;
		_asc = asc;
		_entityField = null;
	}

	/**
	 * Returns the field's name.
	 *
	 * @param  locale the locale
	 * @return the field's name
	 * @review
	 */
	public String getSortableFieldName(Locale locale) {
		if (_entityField == null) {
			return _fieldName;
		}

		return _entityField.getSortableName(locale);
	}

	/**
	 * Returns {@code true} if the sort field is ascending.
	 *
	 * @return {@code true} if the sort field is ascending; {@code false}
	 *         otherwise
	 * @review
	 */
	public boolean isAscending() {
		return _asc;
	}

	private final boolean _asc;
	private final EntityField _entityField;
	private final String _fieldName;

}