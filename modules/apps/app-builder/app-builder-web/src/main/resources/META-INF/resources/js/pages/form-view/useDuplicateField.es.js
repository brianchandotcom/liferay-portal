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

import {
	DataConverter,
	DataDefinitionUtils,
	DataLayoutBuilderActions,
} from 'data-engine-taglib';
import {PagesVisitor} from 'dynamic-data-mapping-form-renderer';
import {useContext} from 'react';

import {isLabelAtFormViewLevel} from '../../components/form-renderer-custom-fields/shared/index.es';
import {sub} from '../../utils/lang.es';
import FormViewContext from './FormViewContext.es';

/**
 * Get all language properties and values from dataDefinitionField
 * and add a prepend with copy of.
 * @param {object} languageKeys
 */

function getCopyOfLanguageKeys(languageKeys) {
	const newLanguageKeys = {};

	Object.keys(languageKeys).forEach((key) => {
		newLanguageKeys[key] = sub(Liferay.Language.get('copy-of-x'), [
			languageKeys[key],
		]);
	});

	return newLanguageKeys;
}

/**
 * Get the FieldLabel from DataDefinitionField or Form Field
 * @param {object} dataDefinition
 * @param {object} field
 * @param {object} dataLayoutFields
 */

function getFieldLabel(dataDefinition, field, dataLayoutFields) {
	const dataDefinitionField = DataDefinitionUtils.getDataDefinitionField(
		dataDefinition,
		field.name
	);

	const isLabelAtFormView = isLabelAtFormViewLevel({
		dataLayoutFields,
		fieldName: field.name,
	});

	if (dataDefinitionField && isLabelAtFormView) {
		return dataDefinitionField.label;
	}

	return field.label;
}

/**
 * Get new fields with from forms with merged
 * properties by using dataLayoutBuilder
 * @param {object} dataLayoutBuilder
 */
function getNewFields(dataLayoutBuilder) {
	const {pages} = dataLayoutBuilder.getStore();
	const visitor = new PagesVisitor(pages);

	const fields = [];

	visitor.mapFields((field) => {
		const definitionField = DataConverter.getDataDefinitionField(field);

		fields.push(definitionField);
	});

	return fields;
}

/**
 * Check if has field whitin dataDefinitionFields
 * @param {Array} dataDefinition
 * @param {string} fieldName
 */
function hasField({dataDefinitionFields}, fieldName) {
	const findByName = ({name}) => fieldName === name;

	return !!dataDefinitionFields.find(findByName);
}

/**
 * Check if field is required
 * @param {object} dataDefinition
 * @param {string} fieldName
 */
function isRequiredField(dataDefinition, fieldName) {
	const field = DataDefinitionUtils.getDataDefinitionField(
		dataDefinition,
		fieldName
	);

	return field?.required ?? false;
}

export default ({dataLayoutBuilder}) => {
	const [
		{
			dataDefinition,
			dataLayout: {dataLayoutFields},
		},
		dispatch,
	] = useContext(FormViewContext);

	return (event) => {
		dataLayoutBuilder.dispatch('fieldDuplicated', event);

		const {fieldName} = event;

		const dataDefinitionFields = getNewFields(dataLayoutBuilder).map(
			(field) => {
				if (hasField(dataDefinition, field.name)) {
					return {
						...field,
						label: getFieldLabel(
							dataDefinition,
							field,
							dataLayoutFields
						),
						required: isRequiredField(dataDefinition, field.name),
					};
				}

				const isPrimaryFieldAtFormViewLevel = isLabelAtFormViewLevel({
					dataLayoutFields,
					fieldName,
				});

				let label = field.label;

				if (isPrimaryFieldAtFormViewLevel) {
					const dataDefinitionField = DataDefinitionUtils.getDataDefinitionField(
						dataDefinition,
						fieldName
					);

					dataLayoutFields[field.name] = {
						...dataLayoutFields[field.name],
						label,
					};

					label = getCopyOfLanguageKeys(dataDefinitionField.label);
				}

				return {
					...field,
					label,
					required: isRequiredField(dataDefinition, fieldName),
				};
			}
		);

		dispatch({
			payload: {dataLayoutFields},
			type: DataLayoutBuilderActions.UPDATE_DATA_LAYOUT_FIELDS,
		});

		dispatch({
			payload: {dataDefinitionFields},
			type: DataLayoutBuilderActions.UPDATE_DATA_DEFINITION_FIELDS,
		});
	};
};
