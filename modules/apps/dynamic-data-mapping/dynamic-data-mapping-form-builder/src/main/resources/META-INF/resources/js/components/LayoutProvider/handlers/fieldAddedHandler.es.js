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

import * as FormSupport from 'dynamic-data-mapping-form-renderer/js/components/FormRenderer/FormSupport.es';
import {
	generateInstanceId,
	getFieldProperties
} from '../../../util/fieldSupport.es';
import {generateFieldName} from '../util/fields.es';
import {normalizeSettingsContextPages} from '../../../util/fieldSupport.es';

const handleFieldAdded = (props, state, event) => {
	const {addedToPlaceholder, fieldType, indexes} = event;
	const {defaultLanguageId, editingLanguageId, spritemap} = props;
	const newFieldName = generateFieldName(state.pages, fieldType.label);

	const focusedField = {
		...fieldType,
		fieldName: newFieldName,
		settingsContext: {
			...fieldType.settingsContext,
			pages: normalizeSettingsContextPages(
				fieldType.settingsContext.pages,
				editingLanguageId,
				fieldType,
				newFieldName
			),
			type: fieldType.name
		}
	};

	const {fieldName, name, settingsContext} = focusedField;
	const {pageIndex, rowIndex} = indexes;
	let {pages} = state;
	let {columnIndex} = indexes;

	const fieldProperties = {
		...getFieldProperties(
			settingsContext,
			defaultLanguageId,
			editingLanguageId
		),
		fieldName,
		instanceId: generateInstanceId(8),
		name,
		settingsContext,
		spritemap,
		type: name
	};

	if (addedToPlaceholder) {
		pages = FormSupport.addRow(pages, rowIndex, pageIndex);

		columnIndex = 0;
	}

	return {
		focusedField: {
			...fieldProperties,
			columnIndex,
			pageIndex,
			rowIndex
		},
		pages: FormSupport.addFieldToColumn(
			pages,
			pageIndex,
			rowIndex,
			columnIndex,
			fieldProperties
		),
		previousFocusedField: fieldProperties
	};
};

export default handleFieldAdded;
