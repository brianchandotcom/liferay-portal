import {PagesVisitor} from '../../util/visitors.es';

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

	actions.forEach(
		(action, index) => {
			let targetFieldExists = false;

			visitor.mapFields(
				({fieldName}) => {
					if (action.target === fieldName) {
						targetFieldExists = true;
					}
				}
			);

			if (!targetFieldExists) {
				actions = clearTargetValue(actions, index);
			}
		}
	);

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