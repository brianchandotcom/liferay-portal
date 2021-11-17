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

package com.liferay.frontend.data.set.internal.view.table;

import com.liferay.frontend.data.set.view.table.FrontendDataSetTableSchema;
import com.liferay.frontend.data.set.view.table.FrontendDataSetTableSchemaBuilder;
import com.liferay.frontend.data.set.view.table.FrontendDataSetTableSchemaField;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Marco Leo
 */
public class FrontendDataSetTableSchemaBuilderImpl
	implements FrontendDataSetTableSchemaBuilder {

	@Override
	public void addFrontendDataSetTableSchemaField(
		FrontendDataSetTableSchemaField frontendDataSetTableSchemaField) {

		_frontendDataSetTableSchemaFieldsMap.put(
			frontendDataSetTableSchemaField.getFieldName(),
			frontendDataSetTableSchemaField);
	}

	@Override
	public FrontendDataSetTableSchemaField addFrontendDataSetTableSchemaField(
		String fieldName) {

		FrontendDataSetTableSchemaField frontendDataSetTableSchemaField =
			new FrontendDataSetTableSchemaField();

		frontendDataSetTableSchemaField.setFieldName(fieldName);

		_frontendDataSetTableSchemaFieldsMap.put(
			fieldName, frontendDataSetTableSchemaField);

		return frontendDataSetTableSchemaField;
	}

	@Override
	public FrontendDataSetTableSchemaField addFrontendDataSetTableSchemaField(
		String fieldName, String label) {

		FrontendDataSetTableSchemaField frontendDataSetTableSchemaField =
			addFrontendDataSetTableSchemaField(fieldName);

		frontendDataSetTableSchemaField.setLabel(label);

		return frontendDataSetTableSchemaField;
	}

	@Override
	public FrontendDataSetTableSchema build() {
		_frontendDataSetTableSchema.setFrontendDataSetTableSchemaFieldsMap(
			_frontendDataSetTableSchemaFieldsMap);

		return _frontendDataSetTableSchema;
	}

	@Override
	public void removeFrontendDataSetTableSchemaField(String fieldName) {
		_frontendDataSetTableSchemaFieldsMap.remove(fieldName);
	}

	@Override
	public void setFrontendDataSetTableSchema(
		FrontendDataSetTableSchema frontendDataSetTableSchema) {

		_frontendDataSetTableSchema = frontendDataSetTableSchema;
	}

	private FrontendDataSetTableSchema _frontendDataSetTableSchema =
		new FrontendDataSetTableSchema();
	private final Map<String, FrontendDataSetTableSchemaField>
		_frontendDataSetTableSchemaFieldsMap = new LinkedHashMap<>();

}