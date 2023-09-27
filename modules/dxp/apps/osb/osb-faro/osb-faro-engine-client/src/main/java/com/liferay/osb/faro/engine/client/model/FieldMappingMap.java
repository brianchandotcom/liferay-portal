/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.engine.client.model;

/**
 * @author Matthew Kong
 */
public class FieldMappingMap {

	public FieldMappingMap() {
	}

	public FieldMappingMap(String dataSourceFieldName, String name) {
		_dataSourceFieldName = dataSourceFieldName;
		_name = name;
	}

	public FieldMappingMap(
		String dataSourceFieldName, String name, Boolean repeatable,
		String type) {

		_dataSourceFieldName = dataSourceFieldName;
		_name = name;
		_repeatable = repeatable;
		_type = type;
	}

	public FieldMappingMap(
		String dataSourceFieldName, String name, String type) {

		this(dataSourceFieldName, name, false, type);
	}

	public String getDataSourceFieldName() {
		return _dataSourceFieldName;
	}

	public String getName() {
		return _name;
	}

	public Boolean getRepeatable() {
		return _repeatable;
	}

	public String getType() {
		return _type;
	}

	public void setDataSourceFieldName(String dataSourceFieldName) {
		_dataSourceFieldName = dataSourceFieldName;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setRepeatable(Boolean repeatable) {
		_repeatable = repeatable;
	}

	public void setType(String type) {
		_type = type;
	}

	private String _dataSourceFieldName;
	private String _name;
	private Boolean _repeatable;
	private String _type;

}