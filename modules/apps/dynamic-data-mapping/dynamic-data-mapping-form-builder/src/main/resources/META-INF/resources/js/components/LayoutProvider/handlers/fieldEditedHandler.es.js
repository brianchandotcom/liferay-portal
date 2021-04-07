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

import {PagesVisitor} from 'data-engine-js-components-web/js/utils/visitors.es';

import {getField} from '../../../util/fieldSupport.es';
import {findInvalidFieldReference} from '../util/fields.es';
import {updateRulesReferences} from '../util/rules.es';
import {
	updateField,
	updateFieldReference,
	updateSettingsContextProperty,
} from '../util/settingsContext.es';

export const updatePages = (props, pages, previousFieldName, newField) => {
	let parentFieldName;
	const visitor = new PagesVisitor(pages);

	const {fieldName: newFieldName} = newField;

	let newPages = visitor.mapFields(
		(field, fieldIndex, columnIndex, rowIndex, pageIndex, parentField) => {
			if (field.fieldName === previousFieldName) {
				if (parentField) {
					parentFieldName = parentField.fieldName;
				}

				return newField;
			}

			return field;
		},
		true,
		true
	);

	if (parentFieldName && previousFieldName !== newFieldName) {
		visitor.setPages(newPages);

		newPages = visitor.mapFields(
			(field) => {
				if (parentFieldName === field.fieldName) {
					const visitor = new PagesVisitor([
						{
							rows: field.rows || [],
						},
					]);
					const layout = visitor.mapColumns((column) => {
						return {
							...column,
							fields: column.fields.map((fieldName) => {
								if (fieldName === previousFieldName) {
									return newFieldName;
								}

								return fieldName;
							}),
						};
					});
					const {rows} = layout[0];

					return {
						...field,
						rows,
						settingsContext: updateSettingsContextProperty(
							props.editingLanguageId,
							field.settingsContext,
							'rows',
							rows
						),
					};
				}

				return field;
			},
			true,
			true
		);
	}

	return newPages;
};

export const updateState = (props, state, propertyName, propertyValue) => {
	const {activePage, focusedField, pages, rules} = state;
	const {fieldName: previousFocusedFieldName} = focusedField;
	const newFocusedField = updateField(
		props,
		focusedField,
		propertyName,
		propertyValue
	);

	const visitor = new PagesVisitor(pages);

	let stateField = visitor.findField(
		(field) => field.fieldName === previousFocusedFieldName
	);

	stateField = updateField(props, stateField, propertyName, propertyValue);

	const newPages = updatePages(
		props,
		pages,
		previousFocusedFieldName,
		stateField
	);

	return {
		activePage,
		focusedField: newFocusedField,
		pages: newPages,
		rules: updateRulesReferences(
			rules || [],
			focusedField,
			newFocusedField
		),
	};
};

export const handleFieldEdited = (props, state, event) => {
	const {fieldName, propertyName, propertyValue} = event;
	let newState = {};

	if (propertyName !== 'name' || propertyValue !== '') {
		state = {
			...state,
			...(fieldName && {focusedField: getField(state.pages, fieldName)}),
		};

		if (
			propertyName === 'fieldReference' &&
			propertyValue !== '' &&
			propertyValue !== state.focusedField.fieldName
		) {
			state = {
				...state,
				focusedField: updateFieldReference(
					state.focusedField,
					findInvalidFieldReference(
						state.focusedField,
						state.pages,
						propertyValue
					),
					false
				),
			};
		}

		newState = updateState(props, state, propertyName, propertyValue);
	}

	return newState;
};

export default handleFieldEdited;
