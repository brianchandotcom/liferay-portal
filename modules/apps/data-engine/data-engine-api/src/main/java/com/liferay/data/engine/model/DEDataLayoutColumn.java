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

package com.liferay.data.engine.model;

import com.liferay.petra.lang.HashUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class represents a column in a {@link DEDataLayoutRow}
 * @review
 * @author Jeyvison Nascimento
 */
public class DEDataLayoutColumn {

	/**
	 * Overriden equals method
	 * @param obj
	 * @return
	 * @review
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DEDataLayoutColumn)) {
			return false;
		}

		DEDataLayoutColumn deDataLayoutColumn = (DEDataLayoutColumn)obj;

		if (Objects.equals(_columnSize, deDataLayoutColumn._columnSize) &&
			Objects.equals(_fieldsName, deDataLayoutColumn._fieldsName)) {

			return true;
		}

		return false;
	}

	/**
	 * Get the column size from 1 to 12. Default value is 12.
	 * @return the column size
	 * @review
	 */
	public int getColumnSize() {
		return _columnSize;
	}

	/**
	 * Return the fields name. Ideally there should be only one field per column
	 * but for compatibility purposes with forms, we keep a list of them.
	 * @return A list of the fields name
	 * @review
	 */
	public List<String> getFieldsName() {
		return _fieldsName;
	}

	/**
	 * Overriden hashCode method
	 * @return
	 * @review
	 */
	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _columnSize);

		return HashUtil.hash(hash, _fieldsName);
	}

	/**
	 * Set the column size. Must be between 1 and 12.
	 * @param columnSize the column size integer
	 * @review
	 */
	public void setColumnSize(int columnSize) {
		if ((columnSize >= 1) && (columnSize <= 12)) {
			_columnSize = columnSize;
		}
	}

	/**
	 * Set the fields name. If the parameter is null, a new list is created.
	 * Ideally there should be only one field per column
	 * but for compatibility purposes with forms, we keep a list of them.
	 * @param fieldsName the list of fields name.
	 * @review
	 */
	public void setFieldsName(List<String> fieldsName) {
		if (fieldsName == null) {
			fieldsName = new ArrayList<>();
		}

		_fieldsName = fieldsName;
	}

	private int _columnSize = 12;
	private List<String> _fieldsName = new ArrayList<>();

}