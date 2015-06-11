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

package com.liferay.portlet.dynamicdatamapping.model;

import com.liferay.portal.kernel.util.ListUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Marcellus Tavares
 */
public class DDMFormLayoutColumn {

	public static final int FULL = 12;

	public DDMFormLayoutColumn(DDMFormLayoutColumn ddmFormLayoutColumn) {
		_ddmFormFieldNames = new ArrayList<>(
			ddmFormLayoutColumn._ddmFormFieldNames);
		_size = ddmFormLayoutColumn._size;
	}

	public DDMFormLayoutColumn(int size, String... ddmFormFieldNames) {
		_size = size;
		_ddmFormFieldNames = ListUtil.toList(ddmFormFieldNames);
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

	public void setDDMFormFieldNames(List<String> ddmFormFieldNames) {
		_ddmFormFieldNames = ddmFormFieldNames;
	}

	private List<String> _ddmFormFieldNames = new ArrayList<>();
	private final int _size;

}