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

package com.liferay.data.engine.web.internal.servlet.data.fetcher;

import com.liferay.data.engine.model.DEDataDefinition;
import com.liferay.data.engine.model.DEDataDefinitionField;
import com.liferay.data.engine.web.internal.graphql.model.DataDefinition;
import com.liferay.data.engine.web.internal.graphql.model.DataDefinitionFieldType;
import com.liferay.data.engine.web.internal.graphql.model.DataDefinitionType;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Leonardo Barros
 */
public abstract class DEBaseDataDefinitionDataFetcher
	extends DEBaseDataFetcher {

	protected DataDefinition createDataDefinition(
		long deDataDefinitionId, DEDataDefinition deDataDefinition) {

		DataDefinition dataDefinition = new DataDefinitionType();

		dataDefinition.setDataDefinitionId(String.valueOf(deDataDefinitionId));

		if (deDataDefinition.getDescription() != null) {
			dataDefinition.setDescriptions(
				getLocalizedValuesType(deDataDefinition.getDescription()));
		}

		if (deDataDefinition.getDEDataDefinitionFields() != null) {
			dataDefinition.setFields(
				createDataDefinitionFieldTypes(
					deDataDefinition.getDEDataDefinitionFields()));
		}

		if (deDataDefinition.getName() != null) {
			dataDefinition.setNames(
				getLocalizedValuesType(deDataDefinition.getName()));
		}

		dataDefinition.setStorageType(deDataDefinition.getStorageType());

		return dataDefinition;
	}

	protected List<DataDefinitionFieldType> createDataDefinitionFieldTypes(
		List<DEDataDefinitionField> deDataDefinitionFields) {

		Stream<DEDataDefinitionField> stream = deDataDefinitionFields.stream();

		return stream.map(
			this::map
		).collect(
			Collectors.toList()
		);
	}

	protected DataDefinitionFieldType map(
		DEDataDefinitionField deDataDefinitionField) {

		DataDefinitionFieldType dataDefinitionFieldType =
			new DataDefinitionFieldType(
				deDataDefinitionField.getName(),
				deDataDefinitionField.getType());

		Object defaultValue = deDataDefinitionField.getDefaultValue();

		if (defaultValue != null) {
			dataDefinitionFieldType.setDefaultValue(defaultValue.toString());
		}

		dataDefinitionFieldType.setIndexable(
			deDataDefinitionField.isIndexable());
		dataDefinitionFieldType.setLabels(
			getLocalizedValuesType(deDataDefinitionField.getLabel()));
		dataDefinitionFieldType.setLocalizable(
			deDataDefinitionField.isLocalizable());
		dataDefinitionFieldType.setRepeatable(
			deDataDefinitionField.isRepeatable());
		dataDefinitionFieldType.setRequired(deDataDefinitionField.isRequired());
		dataDefinitionFieldType.setTips(
			getLocalizedValuesType(deDataDefinitionField.getTip()));

		return dataDefinitionFieldType;
	}

}