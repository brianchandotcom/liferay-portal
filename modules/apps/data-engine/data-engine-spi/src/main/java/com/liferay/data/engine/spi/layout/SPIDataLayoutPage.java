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

import java.util.Map;
import java.util.Objects;

/**
 * @author Leonardo Barros
 */
public class SPIDataLayoutPage {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof SPIDataLayoutPage)) {
			return false;
		}

		SPIDataLayoutPage spiDataLayoutPage = (SPIDataLayoutPage)obj;

		if (Objects.equals(
				_dataLayoutRows, spiDataLayoutPage._dataLayoutRows) &&
			Objects.equals(_description, spiDataLayoutPage._description) &&
			Objects.equals(_title, spiDataLayoutPage._title)) {

			return true;
		}

		return false;
	}

	public SPIDataLayoutRow[] getDataLayoutRows() {
		return _dataLayoutRows;
	}

	public Map<String, Object> getDescription() {
		return _description;
	}

	public Map<String, Object> getTitle() {
		return _title;
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _dataLayoutRows);

		hash = HashUtil.hash(hash, _description);

		return HashUtil.hash(hash, _title);
	}

	public void setDataLayoutRows(SPIDataLayoutRow[] dataLayoutRows) {
		_dataLayoutRows = dataLayoutRows;
	}

	public void setDescription(Map<String, Object> description) {
		_description = description;
	}

	public void setTitle(Map<String, Object> title) {
		_title = title;
	}

	private SPIDataLayoutRow[] _dataLayoutRows;
	private Map<String, Object> _description;
	private Map<String, Object> _title;

}