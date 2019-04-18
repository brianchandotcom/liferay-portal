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

package com.liferay.data.engine.rest.client.dto.v1_0;

import com.liferay.data.engine.rest.client.function.UnsafeSupplier;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Jeyvison Nascimento
 * @generated
 */
@Generated("")
public class DataLayoutColumn {

	public Integer getColumnSize() {
		return columnSize;
	}

	public void setColumnSize(Integer columnSize) {
		this.columnSize = columnSize;
	}

	public void setColumnSize(
		UnsafeSupplier<Integer, Exception> columnSizeUnsafeSupplier) {

		try {
			columnSize = columnSizeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Integer columnSize;

	public String[] getFieldNames() {
		return fieldNames;
	}

	public void setFieldNames(String[] fieldNames) {
		this.fieldNames = fieldNames;
	}

	public void setFieldNames(
		UnsafeSupplier<String[], Exception> fieldNamesUnsafeSupplier) {

		try {
			fieldNames = fieldNamesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String[] fieldNames;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DataLayoutColumn)) {
			return false;
		}

		DataLayoutColumn dataLayoutColumn = (DataLayoutColumn)object;

		return Objects.equals(toString(), dataLayoutColumn.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"columnSize\": ");

		if (columnSize == null) {
			sb.append("null");
		}
		else {
			sb.append(columnSize);
		}

		sb.append(", ");

		sb.append("\"fieldNames\": ");

		if (fieldNames == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < fieldNames.length; i++) {
				sb.append("\"");
				sb.append(fieldNames[i]);
				sb.append("\"");

				if ((i + 1) < fieldNames.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

}