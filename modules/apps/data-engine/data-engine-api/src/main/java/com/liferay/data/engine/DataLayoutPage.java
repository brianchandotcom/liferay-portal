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

package com.liferay.data.engine;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

/**
 * @author Leonardo Barros
 */
public class DataLayoutPage implements Serializable {

	public List<DataLayoutRow> getDataLayoutRows() {
		return _dataLayoutRows;
	}

	public Map<String, Object> getDescription() {
		return _description;
	}

	public Map<String, Object> getTitle() {
		return _title;
	}

	public void setDataLayoutRows(List<DataLayoutRow> dataLayoutRows) {
		_dataLayoutRows = dataLayoutRows;
	}

	public void setDescription(Map<String, Object> description) {
		_description = description;
	}

	public void setTitle(Map<String, Object> title) {
		_title = title;
	}

	private List<DataLayoutRow> _dataLayoutRows;
	private Map<String, Object> _description;
	private Map<String, Object> _title;

}