/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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