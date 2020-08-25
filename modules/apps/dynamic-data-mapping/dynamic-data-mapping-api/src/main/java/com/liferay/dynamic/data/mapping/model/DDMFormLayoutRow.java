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

package com.liferay.dynamic.data.mapping.model;

import com.liferay.petra.lang.HashUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Marcellus Tavares
 */
public class DDMFormLayoutRow implements Serializable {

	public DDMFormLayoutRow() {
	}

	public DDMFormLayoutRow(DDMFormLayoutRow ddmFormLayoutRow) {
		for (DDMFormLayoutColumn ddmFormLayoutColumn :
				ddmFormLayoutRow._ddmFormLayoutColumns) {

			addDDMFormLayoutColumn(
				new DDMFormLayoutColumn(ddmFormLayoutColumn));
		}
	}

	public void addDDMFormLayoutColumn(
		DDMFormLayoutColumn ddmFormLayoutColumn) {

		_ddmFormLayoutColumns.add(ddmFormLayoutColumn);
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DDMFormLayoutRow)) {
			return false;
		}

		DDMFormLayoutRow ddmFormLayoutRow = (DDMFormLayoutRow)object;

		return Objects.equals(
			_ddmFormLayoutColumns, ddmFormLayoutRow._ddmFormLayoutColumns);
	}

	public DDMFormLayoutColumn getDDMFormLayoutColumn(int index) {
		return _ddmFormLayoutColumns.get(index);
	}

	public List<DDMFormLayoutColumn> getDDMFormLayoutColumns() {
		return _ddmFormLayoutColumns;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, _ddmFormLayoutColumns);
	}

	public void setDDMFormLayoutColumns(
		List<DDMFormLayoutColumn> ddmFormLayoutColumns) {

		_ddmFormLayoutColumns = ddmFormLayoutColumns;
	}

	private List<DDMFormLayoutColumn> _ddmFormLayoutColumns = new ArrayList<>();

}