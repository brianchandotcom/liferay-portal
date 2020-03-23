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

import RulesSupport from '../../RuleBuilder/RulesSupport.es';
import {updateField} from '../util/settingsContext.es';

export const formatRules = (state, pages) => {
	const visitor = new PagesVisitor(pages);

	const rules = (state.rules || []).map(rule => {
		const {actions, conditions} = rule;

		conditions.forEach(condition => {
			let firstOperandFieldExists = false;
			let secondOperandFieldExists = false;

			const secondOperand = condition.operands[1];

			visitor.mapFields(({fieldName}) => {
				if (condition.operands[0].value === fieldName) {
					firstOperandFieldExists = true;
				}

				if (secondOperand && secondOperand.value === fieldName) {
					secondOperandFieldExists = true;
				}
			});

			if (condition.operands[0].value === 'user') {
				firstOperandFieldExists = true;
			}

			if (!firstOperandFieldExists) {
				RulesSupport.clearAllConditionFieldValues(condition);
			}

			if (
				!secondOperandFieldExists &&
				secondOperand &&
				secondOperand.type == 'field'
			) {
				RulesSupport.clearSecondOperandValue(condition);
			}
		});

		return {
			...rule,
			actions: RulesSupport.syncActions(pages, actions),
			conditions,
		};
	});

	return rules;
};

export const removeField = (props, pages, fieldName) => {
	const visitor = new PagesVisitor(pages);

	const filter = fields =>
		fields
			.filter(field => field.fieldName !== fieldName)
			.map(field => {
				const nestedFields = field.nestedFields
					? filter(field.nestedFields)
					: [];

				field = updateField(props, field, 'nestedFields', nestedFields);

				const visitor = new PagesVisitor([
					{
						rows:
							typeof field.rows === 'string'
								? JSON.parse(field.rows)
								: field.rows || [],
					},
				]);

				const rows = field.rows
					? FormSupport.removeEmptyRows(
							visitor.mapColumns(column => ({
								...column,
								fields: column.fields.filter(
									nestedFieldName =>
										fieldName !== nestedFieldName
								),
							})),
							0
					  )
					: [];

				field = updateField(props, field, 'rows', rows);

				return {
					...field,
					nestedFields,
					rows,
				};
			});

	return visitor.mapColumns(column => ({
		...column,
		fields: filter(column.fields),
	}));
};

export const handleFieldDeleted = (props, state, {fieldName}) => {
	const {activePage, pages} = state;
	const newPages = pages.map((page, pageIndex) => {
		if (activePage === pageIndex) {
			return {
				...page,
				rows: FormSupport.removeEmptyRows(
					removeField(props, pages, fieldName),
					pageIndex
				),
			};
		}

		return page;
	});

	return {
		focusedField: {},
		pages: newPages,
		rules: formatRules(state, newPages),
	};
};

export default handleFieldDeleted;
