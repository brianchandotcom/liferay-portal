import Token from '../../../expressions/Token.es';
import Tokenizer from '../../../expressions/Tokenizer.es';
import {RulesVisitor} from '../../../util/visitors.es';

export const renameFieldInsideExpression = (
	expression,
	fieldName,
	newFieldName
) => {
	const tokens = Tokenizer.tokenize(expression);

	return Tokenizer.stringifyTokens(
		tokens.map(token => {
			if (token.type === Token.VARIABLE && token.value === fieldName) {
				token = new Token(Token.VARIABLE, newFieldName);
			}

			return token;
		})
	);
};

export const updateRulesFieldName = (rules, fieldName, newFieldName) => {
	const visitor = new RulesVisitor(rules);

	rules = visitor.mapActions(action => {
		if (action.target === fieldName) {
			action = {
				...action,
				target: newFieldName
			};
		}
		if (action.action === 'calculate') {
			action = {
				...action,
				expression: renameFieldInsideExpression(
					action.expression,
					fieldName,
					newFieldName
				)
			};
		}

		return action;
	});

	visitor.setRules(rules);

	return visitor.mapConditions(condition => {
		return {
			...condition,
			operands: condition.operands.map(operand => {
				if (operand.type === 'field' && operand.value === fieldName) {
					operand = {
						...operand,
						value: newFieldName
					};
				}

				return operand;
			})
		};
	});
};
