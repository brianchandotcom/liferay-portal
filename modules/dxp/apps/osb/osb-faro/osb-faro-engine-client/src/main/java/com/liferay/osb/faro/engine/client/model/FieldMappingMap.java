/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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
		String dataSourceFieldName, String name, String type) {

		_dataSourceFieldName = dataSourceFieldName;
		_name = name;
		_type = type;
	}

	public String getDataSourceFieldName() {
		return _dataSourceFieldName;
	}

	public String getName() {
		return _name;
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

	public void setType(String type) {
		_type = type;
	}

	private String _dataSourceFieldName;
	private String _name;
	private String _type;

}