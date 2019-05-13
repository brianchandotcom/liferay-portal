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

package com.liferay.data.engine.spi.definition;

import java.util.Map;

/**
 * @author Leonardo Barros
 */
public class SPIDataDefinition {

	public SPIDataDefinitionField[] getDataDefinitionFields() {
		return _dataDefinitionFields;
	}

	public Map<String, Object> getDescription() {
		return _description;
	}

	public Map<String, Object> getName() {
		return _name;
	}

	public String getStorageType() {
		return _storageType;
	}

	public void setDataDefinitionFields(
		SPIDataDefinitionField[] dataDefinitionFields) {

		_dataDefinitionFields = dataDefinitionFields;
	}

	public void setDescription(Map<String, Object> description) {
		_description = description;
	}

	public void setName(Map<String, Object> name) {
		_name = name;
	}

	public void setStorageType(String storageType) {
		_storageType = storageType;
	}

	private SPIDataDefinitionField[] _dataDefinitionFields;
	private Map<String, Object> _description;
	private Map<String, Object> _name;
	private String _storageType;

}