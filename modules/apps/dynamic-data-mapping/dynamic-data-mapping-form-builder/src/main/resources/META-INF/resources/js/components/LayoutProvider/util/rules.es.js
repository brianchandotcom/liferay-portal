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

import Token from '../../../expressions/Token.es';
import Tokenizer from '../../../expressions/Tokenizer.es';
import {RulesVisitor} from 'dynamic-data-mapping-form-renderer/js/util/visitors.es';

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
