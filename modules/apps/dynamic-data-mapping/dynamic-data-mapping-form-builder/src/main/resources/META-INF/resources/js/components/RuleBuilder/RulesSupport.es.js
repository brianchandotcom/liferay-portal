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

import {PagesVisitor} from 'dynamic-data-mapping-form-renderer';

const clearTargetValue = (actions, index) => {
	if (actions[index]) {
		actions[index].target = '';
	}

	return actions;
};

const clearFirstOperandValue = (condition) => {
	if (condition && condition.operands[0]) {
		condition.operands[0].type = '';
		condition.operands[0].value = '';
	}

	return condition;
};

const clearOperatorValue = (condition) => {
	if (condition) {
		condition.operator = '';
	}

	return condition;
};

const clearSecondOperandValue = (condition) => {
	if (condition && condition.operands[1]) {
		condition.operands[1].type = '';
		condition.operands[1].value = '';
	}

	return condition;
};

const clearAllConditionFieldValues = (condition) => {
	condition = clearFirstOperandValue(condition);
	condition = clearOperatorValue(condition);
	condition = clearSecondOperandValue(condition);

	return condition;
};

const formatRules = (pages, rules) => {
	const visitor = new PagesVisitor(pages);

	const formattedRules = (rules || []).map((rule) => {
		const {actions, conditions} = rule;

		conditions.forEach((condition) => {
			let firstOperandFieldExists = false;
			let secondOperandFieldExists = false;

			const secondOperand = condition.operands[1];

			visitor.mapFields(
				({fieldName}) => {
					if (condition.operands[0].value === fieldName) {
						firstOperandFieldExists = true;
					}

					if (secondOperand && secondOperand.value === fieldName) {
						secondOperandFieldExists = true;
					}
				},
				true,
				true
			);

			if (
				condition.operands.length < 2 &&
				condition.operands[0].type === 'list'
			) {
				condition.operands = [
					{
						label: 'user',
						repeatable: false,
						type: 'user',
						value: 'user',
					},
					{
						...condition.operands[0],
						label: condition.operands[0].value,
					},
				];
			}

			if (condition.operands[0].value === 'user') {
				firstOperandFieldExists = true;
			}

			if (!firstOperandFieldExists) {
				clearAllConditionFieldValues(condition);
			}

			if (
				!secondOperandFieldExists &&
				secondOperand &&
				secondOperand.type == 'field'
			) {
				clearSecondOperandValue(condition);
			}
		});

		return {
			...rule,
			actions: syncActions(pages, actions),
			conditions,
		};
	});

	return formattedRules;
};

const syncActions = (pages, actions) => {
	actions.forEach((action) => {
		if (action.action === 'auto-fill') {
			const {inputs, outputs} = action;

			Object.keys(inputs)
				.filter((key) => !targetFieldExists(inputs[key], pages))
				.map((key) => delete inputs[key]);

			Object.keys(outputs)
				.filter((key) => !targetFieldExists(outputs[key], pages))
				.map((key) => delete outputs[key]);
		}
		else if (action.action === 'jump-to-page') {
			const target = parseInt(action.target, 10) + 1;

			if (pages.length < 3 || target > pages.length) {
				action.target = '';
			}
		}
		else if (!targetFieldExists(action.target, pages)) {
			action.target = '';
		}
	});

	return actions;
};

const targetFieldExists = (target, pages) => {
	const visitor = new PagesVisitor(pages);

	let targetFieldExists = false;

	visitor.mapFields(
		({fieldName}) => {
			if (target === fieldName) {
				targetFieldExists = true;
			}
		},
		true,
		true
	);

	return targetFieldExists;
};

export default {
	clearAllConditionFieldValues,
	clearFirstOperandValue,
	clearOperatorValue,
	clearSecondOperandValue,
	clearTargetValue,
	formatRules,
	syncActions,
};
