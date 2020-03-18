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

package com.liferay.data.engine.rest.internal.dto.v2_0.builder;

import com.liferay.data.engine.rest.dto.v2_0.DataDefinition;
import com.liferay.data.engine.rest.dto.v2_0.builder.DataDefinitionBuilder;
import com.liferay.data.engine.rest.internal.dto.v2_0.util.DataDefinitionUtil;
import com.liferay.data.engine.rest.internal.dto.v2_0.util.DataLayoutUtil;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutDeserializerDeserializeResponse;

import java.util.Map;

/**
 * @author Leonardo Barros
 */
public class DataDefinitionBuilderImpl implements DataDefinitionBuilder {

	public DataDefinitionBuilderImpl(
		DDMFormDeserializer ddmFormDeserializer,
		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker,
		DDMFormLayoutDeserializer ddmFormLayoutDeserializer) {

		_ddmFormDeserializer = ddmFormDeserializer;
		_ddmFormFieldTypeServicesTracker = ddmFormFieldTypeServicesTracker;
		_ddmFormLayoutDeserializer = ddmFormLayoutDeserializer;
	}

	@Override
	public DataDefinition build() {
		return _dataDefinition;
	}

	@Override
	public DataDefinitionBuilder dataDefinitionKey(String dataDefinitionKey) {
		_dataDefinition.setDataDefinitionKey(dataDefinitionKey);

		return this;
	}

	@Override
	public DataDefinitionBuilder dataLayoutDefinition(String definition) {
		DDMFormLayoutDeserializerDeserializeResponse
			ddmFormLayoutDeserializerDeserializeResponse =
				_ddmFormLayoutDeserializer.deserialize(
					DDMFormLayoutDeserializerDeserializeRequest.Builder.
						newBuilder(
							definition
						).build());

		_dataDefinition.setDefaultDataLayout(
			DataLayoutUtil.toDataLayout(
				ddmFormLayoutDeserializerDeserializeResponse.
					getDDMFormLayout()));

		return this;
	}

	@Override
	public DataDefinitionBuilder definition(String definition) {
		DDMFormDeserializerDeserializeResponse
			ddmFormDeserializerDeserializeResponse =
				_ddmFormDeserializer.deserialize(
					DDMFormDeserializerDeserializeRequest.Builder.newBuilder(
						definition
					).build());

		DataDefinition dataDefinition = DataDefinitionUtil.toDataDefinition(
			ddmFormDeserializerDeserializeResponse.getDDMForm(),
			_ddmFormFieldTypeServicesTracker);

		_dataDefinition.setAvailableLanguageIds(
			dataDefinition.getAvailableLanguageIds());
		_dataDefinition.setDataDefinitionFields(
			dataDefinition.getDataDefinitionFields());
		_dataDefinition.setDefaultLanguageId(
			dataDefinition.getDefaultLanguageId());

		return this;
	}

	@Override
	public DataDefinitionBuilder description(Map<String, Object> description) {
		_dataDefinition.setDescription(description);

		return this;
	}

	@Override
	public DataDefinitionBuilder id(Long id) {
		_dataDefinition.setId(id);

		return this;
	}

	@Override
	public DataDefinitionBuilder name(Map<String, Object> name) {
		_dataDefinition.setName(name);

		return this;
	}

	private final DataDefinition _dataDefinition = new DataDefinition();
	private final DDMFormDeserializer _ddmFormDeserializer;
	private final DDMFormFieldTypeServicesTracker
		_ddmFormFieldTypeServicesTracker;
	private final DDMFormLayoutDeserializer _ddmFormLayoutDeserializer;

}