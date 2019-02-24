import {FormSupport} from '../../Form/index.es';
import {PagesVisitor} from '../../../util/visitors.es';
import RulesSupport from '../../RuleBuilder/RulesSupport.es';

const formatRules = (state, pages) => {
	const visitor = new PagesVisitor(pages);

	const rules = state.rules.map(
		rule => {
			const {actions, conditions} = rule;

			conditions.forEach(
				condition => {
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
						}
					);

					if (condition.operands[0].value === 'user') {
						firstOperandFieldExists = true;
					}

					if (!firstOperandFieldExists) {
						RulesSupport.clearAllConditionFieldValues(condition);
					}

					if (!secondOperandFieldExists && secondOperand && secondOperand.type == 'field') {
						RulesSupport.clearSecondOperandValue(condition);
					}
				}
			);

			return {
				...rule,
				actions: RulesSupport.syncActions(pages, actions),
				conditions
			};
		}
	);

	return rules;
};

/**
 * @param {!Array} pages
 * @param {!Object} source
 * @private
 * @return {Object}
 */
const removeEmptyRow = (pages, source) => {
	const {pageIndex, rowIndex} = source;

	if (!FormSupport.rowHasFields(pages, pageIndex, rowIndex)) {
		pages = FormSupport.removeRow(pages, pageIndex, rowIndex);
	}

	return pages;
};

export const handleFieldDeleted = (state, event) => {
	const {columnIndex, pageIndex, rowIndex} = event;
	const {pages} = state;
	let newContext = FormSupport.removeFields(
		pages,
		pageIndex,
		rowIndex,
		columnIndex
	);

	newContext = removeEmptyRow(
		newContext,
		{
			columnIndex,
			pageIndex,
			rowIndex
		}
	);

	return {
		focusedField: {},
		pages: newContext,
		rules: formatRules(state, newContext)
	};
};

export default handleFieldDeleted;