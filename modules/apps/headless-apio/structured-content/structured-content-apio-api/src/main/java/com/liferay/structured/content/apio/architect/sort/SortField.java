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

package com.liferay.structured.content.apio.architect.sort;

import java.io.Serializable;

/**
 * Models a Sort Field.
 *
 * @author Cristina González
 * @review
 */
public class SortField implements Serializable {

	/**
	 * Creates a new sort field.
	 *
	 * @param  fieldName - the name of the field
	 * @param  asc - if the sort should be ascending
	 * @review
	 */
	public SortField(String fieldName, boolean asc) {
		_fieldName = fieldName;
		_asc = asc;
	}

	/**
	 * Returns the name of the field.
	 *
	 * @return - the name of the field
	 * @review
	 */
	public String getFieldName() {
		return _fieldName;
	}

	/**
	 * Returns if the sort field is ascending or not.
	 *
	 * @return - if the sort field is ascending or not
	 * @review
	 */
	public boolean isAscending() {
		return _asc;
	}

	private final boolean _asc;
	private final String _fieldName;

}