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

import com.liferay.frontend.data.set.view.table.FDSTableSchema;
import com.liferay.frontend.data.set.view.table.FDSTableSchemaBuilder;
import com.liferay.frontend.data.set.view.table.FDSTableSchemaField;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.reflect.ReflectionUtil;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Marco Leo
 */
public class FDSTableSchemaBuilderImpl implements FDSTableSchemaBuilder {

	@Override
	public FDSTableSchemaBuilder add(FDSTableSchemaField fdsTableSchemaField) {
		_fdsTableSchemaFieldsMap.put(
			fdsTableSchemaField.getFieldName(), fdsTableSchemaField);

		return this;
	}

	@Override
	public FDSTableSchemaBuilder add(String fieldName) {
		_addFDSTableSchemaField(fieldName);

		return this;
	}

	@Override
	public FDSTableSchemaBuilder add(String fieldName, String label) {
		_addFDSTableSchemaField(fieldName, label);

		return this;
	}

	@Override
	public FDSTableSchemaBuilder add(
		String fieldName, String label,
		UnsafeConsumer<FDSTableSchemaField, Throwable> unsafeConsumer) {

		FDSTableSchemaField fdsTableSchemaField = _addFDSTableSchemaField(
			fieldName, label);

		try {
			unsafeConsumer.accept(fdsTableSchemaField);
		}
		catch (Throwable throwable) {
			return ReflectionUtil.throwException(throwable);
		}

		return this;
	}

	@Override
	public FDSTableSchemaBuilder add(
		String fieldName,
		UnsafeConsumer<FDSTableSchemaField, Throwable> unsafeConsumer) {

		FDSTableSchemaField fdsTableSchemaField = _addFDSTableSchemaField(
			fieldName);

		try {
			unsafeConsumer.accept(fdsTableSchemaField);
		}
		catch (Throwable throwable) {
			return ReflectionUtil.throwException(throwable);
		}

		return this;
	}

	@Override
	public FDSTableSchema build() {
		_fdsTableSchema.setFDSTableSchemaFieldsMap(_fdsTableSchemaFieldsMap);

		return _fdsTableSchema;
	}

	private FDSTableSchemaField _addFDSTableSchemaField(String fieldName) {
		FDSTableSchemaField fdsTableSchemaField = new FDSTableSchemaField();

		fdsTableSchemaField.setFieldName(fieldName);

		_fdsTableSchemaFieldsMap.put(fieldName, fdsTableSchemaField);

		return fdsTableSchemaField;
	}

	private FDSTableSchemaField _addFDSTableSchemaField(
		String fieldName, String label) {

		FDSTableSchemaField fdsTableSchemaField = _addFDSTableSchemaField(
			fieldName);

		fdsTableSchemaField.setLabel(label);

		return fdsTableSchemaField;
	}

	private final FDSTableSchema _fdsTableSchema = new FDSTableSchema();
	private final Map<String, FDSTableSchemaField> _fdsTableSchemaFieldsMap =
		new LinkedHashMap<>();

}