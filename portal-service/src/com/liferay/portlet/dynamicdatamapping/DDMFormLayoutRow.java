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

package com.liferay.portlet.dynamicdatamapping;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Leonardo Barros
 */
public class DDMFormLayoutRow implements Serializable {

	public List<DDMFormLayoutColumn> getDDMFormLayoutColumns() {
		return _ddmFormLayoutColumns;
	}

	public void setDDMFormLayoutColumns(
		List<DDMFormLayoutColumn> ddmFormLayoutColumns) {

		_ddmFormLayoutColumns = ddmFormLayoutColumns;
	}

	private List<DDMFormLayoutColumn> _ddmFormLayoutColumns = new ArrayList<>();

}