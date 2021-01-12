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
	DataDefinitionUtils,
	DataLayoutBuilderActions,
} from 'data-engine-taglib';
import {PagesVisitor} from 'dynamic-data-mapping-form-renderer';
import {useContext} from 'react';

import FormViewContext from './FormViewContext.es';

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
		const definitionField = dataLayoutBuilder.getDataDefinitionField(field);

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
	const [{dataDefinition}, dispatch] = useContext(FormViewContext);

	return (event) => {
		dataLayoutBuilder.dispatch('fieldDuplicated', event);

		const dataDefinitionFields = getNewFields(dataLayoutBuilder).map(
			(newField) => {
				if (hasField(dataDefinition, newField.name)) {
					return {
						...newField,
						required: isRequiredField(
							dataDefinition,
							newField.name
						),
					};
				}

				return {
					...newField,
					required: isRequiredField(dataDefinition, event.fieldName),
				};
			}
		);

		dispatch({
			payload: {dataDefinitionFields},
			type: DataLayoutBuilderActions.UPDATE_DATA_DEFINITION_FIELDS,
		});
	};
};
