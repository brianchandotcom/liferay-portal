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
public class SPIDataLayoutRow {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof SPIDataLayoutRow)) {
			return false;
		}

		SPIDataLayoutRow spiDataLayoutRow = (SPIDataLayoutRow)obj;

		if (Objects.equals(
				_dataLayoutColums, spiDataLayoutRow._dataLayoutColums)) {

			return true;
		}

		return false;
	}

	public SPIDataLayoutColumn[] getDataLayoutColums() {
		return _dataLayoutColums;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, _dataLayoutColums);
	}

	public void setDataLayoutColums(SPIDataLayoutColumn[] dataLayoutColums) {
		_dataLayoutColums = dataLayoutColums;
	}

	private SPIDataLayoutColumn[] _dataLayoutColums;

}