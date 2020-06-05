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

package com.liferay.frontend.taglib.clay.internal.data.set.view.table;

import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchema;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchemaBuilder;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchemaField;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Marco Leo
 */
public class ClayTableSchemaBuilderImpl implements ClayTableSchemaBuilder {

	public ClayTableSchemaBuilderImpl() {
		_clayTableSchema = new ClayTableSchema();
		_clayTableSchemaFieldsMap = new LinkedHashMap<>();
	}

	@Override
	public void addClayTableSchemaField(
		ClayTableSchemaField clayTableSchemaField) {

		_clayTableSchemaFieldsMap.put(
			clayTableSchemaField.getFieldName(), clayTableSchemaField);
	}

	@Override
	public ClayTableSchemaField addClayTableSchemaField(String fieldName) {
		ClayTableSchemaField clayTableSchemaField = new ClayTableSchemaField();

		clayTableSchemaField.setFieldName(fieldName);

		_clayTableSchemaFieldsMap.put(fieldName, clayTableSchemaField);

		return clayTableSchemaField;
	}

	@Override
	public ClayTableSchemaField addClayTableSchemaField(
		String fieldName, String label) {

		ClayTableSchemaField clayTableSchemaField = addClayTableSchemaField(
			fieldName);

		clayTableSchemaField.setLabel(label);

		return clayTableSchemaField;
	}

	@Override
	public ClayTableSchema build() {
		_clayTableSchema.setClayTableSchemaFieldsMap(_clayTableSchemaFieldsMap);

		return _clayTableSchema;
	}

	@Override
	public void removeClayTableSchemaField(String fieldName) {
		_clayTableSchemaFieldsMap.remove(fieldName);
	}

	@Override
	public void setClayTableSchema(ClayTableSchema clayTableSchema) {
		_clayTableSchema = clayTableSchema;
	}

	private ClayTableSchema _clayTableSchema;
	private final Map<String, ClayTableSchemaField> _clayTableSchemaFieldsMap;

}