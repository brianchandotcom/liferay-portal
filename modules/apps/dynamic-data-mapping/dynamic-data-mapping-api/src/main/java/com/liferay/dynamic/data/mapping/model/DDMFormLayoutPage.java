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
public class DDMFormLayoutPage implements Serializable {

	public DDMFormLayoutPage() {
	}

	public DDMFormLayoutPage(DDMFormLayoutPage ddmFormLayoutPage) {
		for (DDMFormLayoutRow ddmFormLayoutRow :
				ddmFormLayoutPage._ddmFormLayoutRows) {

			addDDMFormLayoutRow(new DDMFormLayoutRow(ddmFormLayoutRow));
		}

		_description = new LocalizedValue(ddmFormLayoutPage._description);
		_title = new LocalizedValue(ddmFormLayoutPage._title);
	}

	public void addDDMFormLayoutRow(DDMFormLayoutRow ddmFormLayoutRow) {
		_ddmFormLayoutRows.add(ddmFormLayoutRow);
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DDMFormLayoutPage)) {
			return false;
		}

		DDMFormLayoutPage ddmFormLayoutPage = (DDMFormLayoutPage)object;

		if (Objects.equals(
				_ddmFormLayoutRows, ddmFormLayoutPage._ddmFormLayoutRows) &&
			Objects.equals(_description, ddmFormLayoutPage._description) &&
			Objects.equals(_title, ddmFormLayoutPage._title)) {

			return true;
		}

		return false;
	}

	public DDMFormLayoutRow getDDMFormLayoutRow(int index) {
		return _ddmFormLayoutRows.get(index);
	}

	public List<DDMFormLayoutRow> getDDMFormLayoutRows() {
		return _ddmFormLayoutRows;
	}

	public LocalizedValue getDescription() {
		return _description;
	}

	public LocalizedValue getTitle() {
		return _title;
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _ddmFormLayoutRows);

		hash = HashUtil.hash(hash, _description);

		return HashUtil.hash(hash, _title);
	}

	public void setDDMFormLayoutRows(List<DDMFormLayoutRow> ddmFormLayoutRows) {
		_ddmFormLayoutRows = ddmFormLayoutRows;
	}

	public void setDescription(LocalizedValue description) {
		_description = description;
	}

	public void setTitle(LocalizedValue title) {
		_title = title;
	}

	private List<DDMFormLayoutRow> _ddmFormLayoutRows = new ArrayList<>();
	private LocalizedValue _description = new LocalizedValue();
	private LocalizedValue _title = new LocalizedValue();

}