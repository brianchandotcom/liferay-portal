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

import {PagesVisitor} from 'data-engine-js-components-web';
import {
	DataConverter,
	DataDefinitionUtils,
	DataLayoutBuilderActions,
} from 'data-engine-taglib';
import {useContext} from 'react';

import {sub} from '../../utils/lang.es';
import FormViewContext from './FormViewContext.es';

/**
 * Get new fields with from forms with merged
 * properties by using dataLayoutBuilder
 * @param {object} dataLayoutBuilder
 */
function getDDMFormFields(dataLayoutBuilder) {
	const {
		pages,
	} = dataLayoutBuilder.formBuilderWithLayoutProvider.refs.layoutProvider.state;
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
function hasFieldWithinDefinitionFields({dataDefinitionFields}, fieldName) {
	const findByName = ({name}) => fieldName === name;

	return !!dataDefinitionFields.find(findByName);
}

/**
 * Get label property from definitionField
 * @param {Array} dataDefinition
 * @param {string} fieldName
 */
function getLabelProperty(dataDefinition, fieldName) {
	const definitionField = DataDefinitionUtils.getDataDefinitionField(
		dataDefinition,
		fieldName
	);

	return definitionField.label;
}

/**
 * Get required property from definitionField
 * @param {object} dataDefinition
 * @param {string} fieldName
 */
function getRequiredProperty(dataDefinition, fieldName) {
	const definitionField = DataDefinitionUtils.getDataDefinitionField(
		dataDefinition,
		fieldName
	);

	return !!definitionField?.required;
}

/**
 * Generate language key copy-of-x
 * @param {Object} label
 */
function generateCopyOfLanguageKeys(label = {}) {
	return Object.keys(label).reduce(
		(acc, cur) => ({
			...acc,
			[cur]: sub(Liferay.Language.get('copy-of-x'), [label[cur]]),
		}),
		{}
	);
}

export default function useDuplicateField({dataLayoutBuilder}) {
	const [{dataDefinition, dataLayout}, dispatch] = useContext(
		FormViewContext
	);

	return (event) => {
		dataLayoutBuilder.formBuilderWithLayoutProvider.refs.layoutProvider?.dispatch?.(
			'fieldDuplicated',
			event
		);

		const dataLayoutFields = {...dataLayout.dataLayoutFields};

		const dataDefinitionFields = getDDMFormFields(dataLayoutBuilder).map(
			(ddmFormField) => {
				if (
					hasFieldWithinDefinitionFields(
						dataDefinition,
						ddmFormField.name
					)
				) {
					return {
						...ddmFormField,
						label: getLabelProperty(
							dataDefinition,
							ddmFormField.name
						),
						required: getRequiredProperty(
							dataDefinition,
							ddmFormField.name
						),
					};
				}

				dataLayoutFields[ddmFormField.name] = {
					...dataLayoutFields[ddmFormField.fieldName],
					label: generateCopyOfLanguageKeys(
						dataLayoutFields[event.fieldName].label
					),
				};

				return {
					...ddmFormField,
					label: generateCopyOfLanguageKeys(
						getLabelProperty(dataDefinition, event.fieldName)
					),
					required: getRequiredProperty(
						dataDefinition,
						event.fieldName
					),
				};
			}
		);

		dispatch({
			payload: {dataDefinitionFields},
			type: DataLayoutBuilderActions.UPDATE_DATA_DEFINITION_FIELDS,
		});

		dispatch({
			payload: {dataLayoutFields},
			type: DataLayoutBuilderActions.UPDATE_DATA_LAYOUT_FIELDS,
		});
	};
}
