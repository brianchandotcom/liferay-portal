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

import {FormSupport, PagesVisitor} from 'dynamic-data-mapping-form-renderer';

import {sub} from '../../../util/strings.es';
import {getFieldLocalizedValue} from '../util/fields.es';

const handleFieldDuplicated = (
	{editingLanguageId, fieldNameGenerator},
	state,
	event
) => {
	const {columnIndex, pageIndex, rowIndex} = event.indexes;
	const {pages} = state;

	const field = FormSupport.getField(pages, pageIndex, rowIndex, columnIndex);

	const localizedLabel = getFieldLocalizedValue(
		field.settingsContext.pages,
		'label',
		editingLanguageId
	);

	const label = sub(Liferay.Language.get('copy-of-x'), [localizedLabel]);
	const newFieldName = fieldNameGenerator(label);
	const oldFieldName = field.fieldName;
	const visitor = new PagesVisitor(field.settingsContext.pages);

	const duplicatedField = {
		...field,
		fieldName: newFieldName,
		label,
		name: newFieldName,
		settingsContext: {
			...field.settingsContext,
			pages: visitor.mapFields(field => {
				if (field.fieldName === 'name') {
					field = {
						...field,
						value: newFieldName
					};
				} else if (field.fieldName === 'label') {
					field = {
						...field,
						localizedValue: {
							...field.localizedValue,
							[editingLanguageId]: label
						},
						value: label
					};
				} else if (field.fieldName === 'validation') {
					const expression = field.value.expression;

					if (expression && expression.value) {
						field = {
							...field,
							value: {
								...field.value,
								expression: {
									...field.value.expression,
									value: expression.value.replace(
										oldFieldName,
										newFieldName
									)
								}
							}
						};
					}
				}
				return {
					...field
				};
			})
		}
	};
	const newRowIndex = rowIndex + 1;

	const newRow = FormSupport.implAddRow(12, [duplicatedField]);

	return {
		focusedField: {
			...duplicatedField,
			columnIndex,
			pageIndex,
			rowIndex: newRowIndex
		},
		pages: FormSupport.addRow(pages, newRowIndex, pageIndex, newRow)
	};
};

export default handleFieldDuplicated;
