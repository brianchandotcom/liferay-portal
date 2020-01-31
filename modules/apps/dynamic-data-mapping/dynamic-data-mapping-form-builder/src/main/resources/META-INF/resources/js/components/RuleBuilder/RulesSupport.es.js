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

const clearFirstOperandValue = condition => {
	if (condition && condition.operands[0]) {
		condition.operands[0].type = '';
		condition.operands[0].value = '';
	}

	return condition;
};

const clearOperatorValue = condition => {
	if (condition) {
		condition.operator = '';
	}

	return condition;
};

const clearSecondOperandValue = condition => {
	if (condition && condition.operands[1]) {
		condition.operands[1].type = '';
		condition.operands[1].value = '';
	}

	return condition;
};

const clearAllConditionFieldValues = condition => {
	condition = clearFirstOperandValue(condition);
	condition = clearOperatorValue(condition);
	condition = clearSecondOperandValue(condition);

	return condition;
};

const syncActions = (pages, actions) => {
	const visitor = new PagesVisitor(pages);

	actions.forEach((action, index) => {
		let targetFieldExists = false;

		visitor.mapFields(({fieldName}) => {
			if (action.target === fieldName) {
				targetFieldExists = true;
			}
		});

		if (!targetFieldExists) {
			actions = clearTargetValue(actions, index);
		}
	});

	return actions;
};

export default {
	clearAllConditionFieldValues,
	clearFirstOperandValue,
	clearOperatorValue,
	clearSecondOperandValue,
	clearTargetValue,
	syncActions
};
