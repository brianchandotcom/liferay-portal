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

import {FormSupport, PagesVisitor} from 'data-engine-js-components-web';

import {FIELD_TYPE_FIELDSET} from '../../../util/constants.es';
import {addField, getParentField} from '../../../util/fieldSupport.es';
import {updateField} from '../util/settingsContext.es';
import handleFieldDeleted from './fieldDeletedHandler.es';
import handleSectionAdded from './sectionAddedHandler.es';

export default (props, state, event) => {
	const {
		sourceFieldName,
		sourceFieldPage,
		targetFieldName,
		targetIndexes,
		targetParentFieldName,
	} = event;

	const sourceField = FormSupport.findFieldByFieldName(
		state.pages,
		sourceFieldName
	);

	let mergedState = {
		...handleFieldDeleted(props, state, {
			activePage: sourceFieldPage,
			editRule: false,
			fieldName: sourceFieldName,
			removeEmptyRows: false,
		}),
	};
	let parentField = getParentField(state.pages, sourceFieldName);

	if (
		parentField &&
		targetFieldName &&
		parentField.fieldName === targetFieldName
	) {
		return state;
	}

	if (
		parentField &&
		parentField.type === FIELD_TYPE_FIELDSET &&
		parentField.nestedFields.length === 1
	) {
		let parentFieldName = parentField ? parentField.fieldName : '';

		do {
			if (parentField) {
				parentFieldName = parentField.fieldName;
			}

			parentField = getParentField(state.pages, parentField.fieldName);
		} while (
			parentField &&
			parentField.type === FIELD_TYPE_FIELDSET &&
			parentField.fieldName !== targetParentFieldName &&
			parentField.nestedFields.length === 1
		);

		if (parentFieldName && parentFieldName !== targetParentFieldName) {
			mergedState = {
				...handleFieldDeleted(props, state, {
					activePage: sourceFieldPage,
					editRule: false,
					fieldName: parentFieldName,
					removeEmptyRows: false,
				}),
			};
		}
	}

	if (targetFieldName) {
		const deletedState = handleFieldDeleted(props, state, {
			activePage: sourceFieldPage,
			editRule: false,
			fieldName: sourceFieldName,
		});

		return {
			...handleSectionAdded(
				props,
				{
					...state,
					pages: deletedState.pages,
				},
				{
					data: {
						fieldName: targetFieldName,
						parentFieldName: targetParentFieldName,
					},
					indexes: targetIndexes,
					newField: sourceField,
				}
			),
		};
	}

	const addedState = addField({
		...props,
		indexes: targetIndexes,
		newField: sourceField,
		pages: mergedState.pages,
		parentFieldName: targetParentFieldName,
	});

	const visitor = new PagesVisitor(addedState.pages);

	const pages = visitor.mapFields((field) => {
		if (field.type != 'grid' && field.rows) {
			return updateField(
				props,
				field,
				'rows',
				FormSupport.removeEmptyRows([field], 0)
			);
		}

		return field;
	});

	return {
		...addedState,
		pages: pages.map((page, pageIndex) => {
			if (sourceFieldPage === pageIndex) {
				return {
					...page,
					rows: FormSupport.removeEmptyRows(pages, pageIndex),
				};
			}

			return page;
		}),
	};
};
