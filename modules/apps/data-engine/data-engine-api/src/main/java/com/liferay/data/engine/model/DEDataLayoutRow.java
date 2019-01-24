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

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

/**
 * This class represents a row inside a {@link DEDataLayoutPage}
 * @review
 * @author Jeyvison Nascimento
 */
public class DEDataLayoutRow {

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

		if (!(obj instanceof DEDataLayoutRow)) {
			return false;
		}

		DEDataLayoutRow deDataLayoutRow = (DEDataLayoutRow)obj;

		if (Arrays.equals(
				_deDataLayoutColumns.toArray(),
				deDataLayoutRow._deDataLayoutColumns.toArray())) {

			return true;
		}

		return false;
	}

	/**
	 * Return a queue of all the  {@link DEDataLayoutColumn} that belongs to this
	 * row.
	 * @return a queue of {@link DEDataLayoutColumn}, filled or empty.
	 * @review
	 */
	public Queue<DEDataLayoutColumn> getDEDataLayoutColumns() {
		return _deDataLayoutColumns;
	}

	/**
	 * Overriden hashCode method
	 * @review
	 * @return
	 */
	@Override
	public int hashCode() {
		return HashUtil.hash(0, _deDataLayoutColumns);
	}

	/**
	 * Sets a Queue of {@link DEDataLayoutColumn} to the row. if the parameter
	 * is null then a new queue is created.
	 * @param deDataLayoutColumns a Queue of {@link DEDataLayoutColumn}
	 * @review
	 */
	public void setDEDataLayoutColumns(
		Queue<DEDataLayoutColumn> deDataLayoutColumns) {

		if (deDataLayoutColumns == null) {
			_deDataLayoutColumns = new ArrayDeque<>();
		}

		_deDataLayoutColumns = deDataLayoutColumns;
	}

	private Queue<DEDataLayoutColumn> _deDataLayoutColumns = new ArrayDeque<>();

}