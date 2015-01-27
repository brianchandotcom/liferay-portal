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

/**
 * @author Marcellus Tavares
 */
public class DDMFormLayoutColumn {

	public static final int FULL = 12;

	public DDMFormLayoutColumn(DDMFormLayoutColumn ddmFormLayoutColumn) {
		this(ddmFormLayoutColumn._ddmFormFieldName, ddmFormLayoutColumn._size);
	}

	public DDMFormLayoutColumn(String ddmFormFieldName, int size) {
		_ddmFormFieldName = ddmFormFieldName;
		_size = size;
	}

	public String getDDMFormFieldName() {
		return _ddmFormFieldName;
	}

	public int getSize() {
		return _size;
	}

	private final String _ddmFormFieldName;
	private final int _size;

}