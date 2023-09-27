/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.model;

import com.liferay.petra.lang.HashUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Marcellus Tavares
 */
public class DDMFormLayoutColumn implements Serializable {

	public static final int FULL = 12;

	public DDMFormLayoutColumn() {
	}

	public DDMFormLayoutColumn(DDMFormLayoutColumn ddmFormLayoutColumn) {
		_ddmFormFieldNames = new ArrayList<>(
			ddmFormLayoutColumn._ddmFormFieldNames);
		_size = ddmFormLayoutColumn._size;
	}

	public DDMFormLayoutColumn(int size, String... ddmFormFieldNames) {
		_size = size;

		_ddmFormFieldNames = ListUtil.fromArray(ddmFormFieldNames);
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DDMFormLayoutColumn)) {
			return false;
		}

		DDMFormLayoutColumn ddmFormLayoutColumn = (DDMFormLayoutColumn)object;

		if (Objects.equals(_size, ddmFormLayoutColumn._size) &&
			Objects.equals(
				_ddmFormFieldNames, ddmFormLayoutColumn._ddmFormFieldNames)) {

			return true;
		}

		return false;
	}

	public String getDDMFormFieldName(int index) {
		return _ddmFormFieldNames.get(index);
	}

	public List<String> getDDMFormFieldNames() {
		return _ddmFormFieldNames;
	}

	public int getSize() {
		return _size;
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _ddmFormFieldNames);

		return HashUtil.hash(hash, _size);
	}

	public void setDDMFormFieldNames(List<String> ddmFormFieldNames) {
		_ddmFormFieldNames = ddmFormFieldNames;
	}

	public void setSize(int size) {
		_size = size;
	}

	private List<String> _ddmFormFieldNames = new ArrayList<>();
	private int _size;

}