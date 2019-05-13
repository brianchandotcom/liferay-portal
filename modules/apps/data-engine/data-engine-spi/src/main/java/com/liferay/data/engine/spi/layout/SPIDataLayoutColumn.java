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

package com.liferay.data.engine.spi.layout;

import com.liferay.petra.lang.HashUtil;

import java.util.Objects;

/**
 * @author Leonardo Barros
 */
public class SPIDataLayoutColumn {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof SPIDataLayoutColumn)) {
			return false;
		}

		SPIDataLayoutColumn spiDataLayoutColumn = (SPIDataLayoutColumn)obj;

		if (Objects.equals(_columnSize, spiDataLayoutColumn._columnSize) &&
			Objects.equals(_fieldNames, spiDataLayoutColumn._fieldNames)) {

			return true;
		}

		return false;
	}

	public int getColumnSize() {
		return _columnSize;
	}

	public String[] getFieldNames() {
		return _fieldNames;
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _columnSize);

		return HashUtil.hash(hash, _fieldNames);
	}

	public void setColumnSize(int columnSize) {
		_columnSize = columnSize;
	}

	public void setFieldNames(String[] fieldNames) {
		_fieldNames = fieldNames;
	}

	private int _columnSize = 12;
	private String[] _fieldNames;

}