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
import {PagesVisitor} from 'dynamic-data-mapping-form-renderer/js/util/visitors.es';

import RulesSupport from '../../RuleBuilder/RulesSupport.es';

const formatRules = (state, pages) => {
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
			conditions
		};
	});

	return rules;
};

const removeEmptyRow = (pages, source) => {
	const {pageIndex, rowIndex} = source;

	if (!FormSupport.rowHasFields(pages, pageIndex, rowIndex)) {
		pages = FormSupport.removeRow(pages, pageIndex, rowIndex);
	}

	return pages;
};

export const handleFieldDeleted = (state, {indexes}) => {
	const {columnIndex, pageIndex, rowIndex} = indexes;
	const {pages} = state;
	let newContext = FormSupport.removeFields(
		pages,
		pageIndex,
		rowIndex,
		columnIndex
	);

	newContext = removeEmptyRow(newContext, {
		columnIndex,
		pageIndex,
		rowIndex
	});

	return {
		focusedField: {},
		pages: newContext,
		rules: formatRules(state, newContext)
	};
};

export default handleFieldDeleted;
