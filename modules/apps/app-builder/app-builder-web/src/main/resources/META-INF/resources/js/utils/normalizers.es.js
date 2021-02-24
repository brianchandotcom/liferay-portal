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

import {DataConverter} from 'data-engine-taglib';

import {getValidName} from '../utils/utils.es';

export const normalizeNames = ({
	allowEmptyKeys = true,
	defaultName = '',
	localizableValue,
}) => {
	const name = {};

	Object.keys(localizableValue).forEach((languageId) => {
		const value = localizableValue[languageId];
		const normalizedValue = getValidName(defaultName, value)?.trim();

		if (!allowEmptyKeys && !normalizedValue) {
			return;
		}

		name[languageId] = normalizedValue;
	});

	return name;
};

export const normalizeDataDefinition = (dataDefinition) => ({
	...dataDefinition,
	dataDefinitionFields: dataDefinition.dataDefinitionFields.map(
		(dataDefinitionField) => ({
			...dataDefinitionField,

			// Actually showLabel property will be always true
			// because the same property can be controlled by dataLayoutFields

			showLabel: true,
		})
	),
});

export const normalizeDataLayout = ({
	dataDefinition,
	dataLayout,
	dataLayoutBuilder,
	defaultLanguageId,
	editingLanguageId,
}) => {
	const {dataDefinitionFields = []} = dataDefinition;
	const dataLayoutFields = {...dataLayout.dataLayoutFields};

	Object.keys(dataLayoutFields).forEach((field) => {
		if (!dataDefinitionFields.find((item) => item.name === field)) {
			delete dataLayoutFields[field];
		}
	});

	dataDefinitionFields.forEach((dataDefinitionField) => {
		const {
			appContext: [{editingLanguageId}],
			fieldTypes,
		} = dataLayoutBuilder.props;
		const fieldProperties = DataConverter.getDDMSettingsContextWithVisualProperties(
			{
				dataDefinitionField,
				editingLanguageId,
				fieldTypes,
			}
		);

		// Ignore this visual properties because it is treated differently

		delete fieldProperties['label'];
		delete fieldProperties['required'];

		dataLayoutFields[dataDefinitionField.name] = {
			...dataLayoutFields[dataDefinitionField.name],
			...fieldProperties,
		};
	});

	const name = normalizeNames({
		defaultName: Liferay.Language.get('untitled-form-view'),
		localizableValue: dataLayout.name,
	});

	if (!name[defaultLanguageId]) {
		name[defaultLanguageId] = name[editingLanguageId];
	}

	return {
		...dataLayout,
		dataLayoutFields,
		name,
	};
};
