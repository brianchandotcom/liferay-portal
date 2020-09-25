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

import {FIELD_TYPE_FIELDSET} from '../../../util/constants.es';
import RulesSupport from '../../RuleBuilder/RulesSupport.es';
import {updateField} from '../util/settingsContext.es';

export const removeField = (
	props,
	pages,
	fieldName,
	removeEmptyRows = true
) => {
	const visitor = new PagesVisitor(pages);

	const filter = (fields) =>
		fields
			.filter((field) => field.fieldName !== fieldName)
			.map((field) => {
				const nestedFields = field.nestedFields
					? filter(field.nestedFields)
					: [];

				field = updateField(props, field, 'nestedFields', nestedFields);

				if (field.type !== FIELD_TYPE_FIELDSET) {
					return {
						...field,
						nestedFields,
					};
				}

				let rows = [];

				if (field.rows) {
					const visitor = new PagesVisitor([
						{
							rows:
								typeof field.rows === 'string'
									? JSON.parse(field.rows)
									: field.rows || [],
						},
					]);

					const pages = visitor.mapColumns((column) => ({
						...column,
						fields: column.fields.filter(
							(nestedFieldName) => fieldName !== nestedFieldName
						),
					}));

					rows = removeEmptyRows
						? FormSupport.removeEmptyRows(pages, 0)
						: pages[0].rows;

					field = updateField(props, field, 'rows', rows);
				}

				return {
					...field,
					nestedFields,
					rows,
				};
			});

	return visitor.mapColumns((column) => ({
		...column,
		fields: filter(column.fields),
	}));
};

export const handleFieldDeleted = (
	props,
	state,
	{activePage, editRule = true, fieldName, removeEmptyRows = true}
) => {
	const {pages} = state;

	if (activePage === undefined) {
		activePage = state.activePage;
	}

	const newPages = pages.map((page, pageIndex) => {
		if (activePage === pageIndex) {
			const pagesWithFieldRemoved = removeField(
				props,
				pages,
				fieldName,
				removeEmptyRows
			);

			return {
				...page,
				rows: removeEmptyRows
					? FormSupport.removeEmptyRows(
							pagesWithFieldRemoved,
							pageIndex
					  )
					: pagesWithFieldRemoved[pageIndex].rows,
			};
		}

		return page;
	});

	return {
		focusedField: {},
		pages: newPages,
		rules: editRule
			? RulesSupport.formatRules(newPages, state.rules)
			: state.rules,
	};
};

export default handleFieldDeleted;
