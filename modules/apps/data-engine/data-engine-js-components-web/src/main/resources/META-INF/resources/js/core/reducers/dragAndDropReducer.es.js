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
	findFieldByFieldName,
	removeEmptyRows,
} from '../../utils/FormSupport.es';
import {FIELD_TYPE_FIELDSET} from '../../utils/constants';
import {addFieldToPage, getParentField} from '../../utils/fieldSupport';
import {updateField} from '../../utils/settingsContext';
import {PagesVisitor} from '../../utils/visitors.es';
import {EVENT_TYPES} from '../actions/eventTypes.es';
import {
	getColumn,
	handleResizeLeft,
	handleResizeRight,
} from '../utils/columnResizedHandler';
import sectionAdded from '../utils/sectionAddedHandler';
import {deleteField} from './fieldEditableReducer.es';

/**
 * NOTE: This is a literal copy of the old LayoutProvider logic. Small changes
 * were made only to adapt to the reducer.
 */
export default (state, action, config) => {
	switch (action.type) {
		case EVENT_TYPES.DND.MOVE: {
			const {
				sourceFieldName,
				sourceFieldPage,
				targetFieldName,
				targetIndexes,
				targetParentFieldName,
			} = action.payload;
			const {
				activePage,
				availableLanguageIds,
				defaultLanguageId,
				editingLanguageId,
				pages,
				rules,
			} = state;
			const {
				fieldTypes,
				generateFieldNameUsingFieldLabel,
				getFieldNameGenerator,
			} = config;

			const fieldNameGenerator = getFieldNameGenerator(
				pages,
				generateFieldNameUsingFieldLabel
			);

			let {sourceParentField} = action.payload;

			const sourceField = findFieldByFieldName(pages, sourceFieldName);

			if (
				sourceParentField &&
				targetFieldName &&
				sourceParentField.fieldName === targetFieldName
			) {
				return state;
			}

			let updatedPages = deleteField({
				defaultLanguageId,
				editingLanguageId,
				fieldName: sourceFieldName,
				fieldNameGenerator,
				fieldPage: sourceFieldPage,
				generateFieldNameUsingFieldLabel,
				pages,
			});

			if (
				sourceParentField &&
				sourceParentField.type === FIELD_TYPE_FIELDSET &&
				sourceParentField.nestedFields.length === 1
			) {
				let sourceParentFieldName = sourceParentField
					? sourceParentField.fieldName
					: '';

				do {
					if (sourceParentField) {
						sourceParentFieldName = sourceParentField.fieldName;
					}

					sourceParentField = getParentField(
						pages,
						sourceParentField.fieldName
					);
				} while (
					sourceParentField &&
					sourceParentField.type === FIELD_TYPE_FIELDSET &&
					sourceParentField.fieldName !== targetParentFieldName &&
					sourceParentField.nestedFields.length === 1
				);

				if (
					sourceParentFieldName &&
					sourceParentFieldName !== targetParentFieldName
				) {
					updatedPages = deleteField({
						defaultLanguageId,
						editingLanguageId,
						fieldName: sourceParentFieldName,
						fieldNameGenerator,
						fieldPage: sourceFieldPage,
						generateFieldNameUsingFieldLabel,
						pages,
					});
				}
			}

			if (targetFieldName) {
				updatedPages = deleteField({
					clean: true,
					defaultLanguageId,
					editingLanguageId,
					fieldName: sourceFieldName,
					fieldNameGenerator,
					fieldPage: sourceFieldPage,
					generateFieldNameUsingFieldLabel,
					pages,
				});

				return sectionAdded(
					{
						availableLanguageIds,
						defaultLanguageId,
						editingLanguageId,
						fieldNameGenerator,
						fieldTypes,
						generateFieldNameUsingFieldLabel,
					},
					{
						activePage,
						pages: updatedPages,
						rules,
					},
					{
						data: {
							fieldName: targetFieldName,
							parentFieldName: targetParentFieldName,
						},
						indexes: targetIndexes,
						newField: sourceField,
					}
				);
			}

			updatedPages = addFieldToPage({
				defaultLanguageId,
				editingLanguageId,
				fieldNameGenerator,
				generateFieldNameUsingFieldLabel,
				indexes: targetIndexes,
				newField: sourceField,
				pages: updatedPages,
				parentFieldName: targetParentFieldName,
			});

			const visitor = new PagesVisitor(updatedPages);

			updatedPages = visitor.mapFields((field) => {
				if (field.type != 'grid' && field.rows) {
					return updateField(
						{
							availableLanguageIds,
							defaultLanguageId,
							fieldNameGenerator,
							generateFieldNameUsingFieldLabel,
						},
						field,
						'rows',
						removeEmptyRows([field], 0)
					);
				}

				return field;
			});

			updatedPages[sourceFieldPage].rows = removeEmptyRows(
				updatedPages,
				sourceFieldPage
			);

			return {pages: updatedPages};
		}
		case EVENT_TYPES.DND.RESIZE: {
			const {column, direction, loc} = action.payload;
			const {defaultLanguageId, editingLanguageId, pages} = state;
			const {
				generateFieldNameUsingFieldLabel,
				getFieldNameGenerator,
			} = config;

			const fieldNameGenerator = getFieldNameGenerator(
				pages,
				generateFieldNameUsingFieldLabel
			);

			let newPages = [...pages];

			const currentColumn = getColumn(pages, loc);

			if (currentColumn) {
				if (direction === 'left') {
					newPages = handleResizeLeft(
						{
							defaultLanguageId,
							editingLanguageId,
							fieldNameGenerator,
							generateFieldNameUsingFieldLabel,
						},
						{pages},
						loc,
						column
					);
				}
				else {
					newPages = handleResizeRight(
						{
							defaultLanguageId,
							editingLanguageId,
							fieldNameGenerator,
							generateFieldNameUsingFieldLabel,
						},
						{pages},
						loc,
						column + 1
					);
				}
			}

			return {
				pages: newPages,
			};
		}
		default:
			return state;
	}
};
