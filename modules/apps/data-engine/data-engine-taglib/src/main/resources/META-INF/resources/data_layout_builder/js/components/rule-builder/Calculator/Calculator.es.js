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

import ClayButton from '@clayui/button';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import ClayLayout from '@clayui/layout';
import {Token, Tokenizer} from 'dynamic-data-mapping-form-builder';
import React, {forwardRef, useCallback, useMemo, useState} from 'react';

import CalculatorButtonArea from './CalculatorButtonArea.es';
import CalculatorDisplay from './CalculatorDisplay.es';

function getRepeatableFields(fields) {
	return fields.filter(({repeatable}) => repeatable === true);
}

function getStateBasedOnExpression(expression) {
	let disableDot = false;
	let disableFunctions = false;
	let disableNumbers = false;
	let disableOperators = false;
	let showOnlyRepeatableFields = false;
	const tokens = Tokenizer.tokenize(expression);

	if (
		tokens.length > 1 &&
		tokens[tokens.length - 1].type === Token.LEFT_PARENTHESIS &&
		tokens[tokens.length - 2].type === Token.FUNCTION &&
		tokens[tokens.length - 2].value === 'sum'
	) {
		disableFunctions = true;
		disableNumbers = true;
		disableOperators = true;
		showOnlyRepeatableFields = true;
	}

	if (
		tokens.length === 0 ||
		(tokens.length > 0 && tokens[tokens.length - 1].type !== Token.LITERAL)
	) {
		disableDot = true;
	}

	if (
		tokens.length > 0 &&
		tokens[tokens.length - 1].type === Token.OPERATOR
	) {
		disableOperators = true;
	}

	return {
		disableDot,
		disableFunctions,
		disableNumbers,
		disableOperators,
		showOnlyRepeatableFields,
	};
}

function shouldAddImplicitMultiplication(tokens, newToken) {
	const lastToken = tokens[tokens.length - 1];

	return (
		lastToken !== undefined &&
		((newToken.type === Token.LEFT_PARENTHESIS &&
			lastToken.type !== Token.OPERATOR &&
			lastToken.type !== Token.FUNCTION &&
			lastToken.type !== Token.LEFT_PARENTHESIS) ||
			(newToken.type === Token.FUNCTION &&
				lastToken.type !== Token.OPERATOR &&
				lastToken.type !== Token.LEFT_PARENTHESIS) ||
			(newToken.type === Token.VARIABLE &&
				(lastToken.type === Token.VARIABLE ||
					lastToken.type === Token.LITERAL)) ||
			(newToken.type === Token.LITERAL &&
				(lastToken.type === Token.VARIABLE ||
					lastToken.type === Token.FUNCTION)))
	);
}

function addTokenToExpression({expression, tokenType, tokenValue}) {
	const newToken = new Token(tokenType, tokenValue);
	const tokens = Tokenizer.tokenize(expression);

	if (shouldAddImplicitMultiplication(tokens, newToken)) {
		tokens.push(new Token(Token.OPERATOR, '*'));
	}

	tokens.push(newToken);

	return Tokenizer.stringifyTokens(tokens);
}

function removeTokenFromExpression({expression}) {
	const tokens = Tokenizer.tokenize(expression);

	const removedToken = tokens.pop();

	if (
		removedToken &&
		removedToken.type === Token.LEFT_PARENTHESIS &&
		tokens.length &&
		tokens[tokens.length - 1].type === Token.FUNCTION
	) {
		tokens.pop();
	}

	return Tokenizer.stringifyTokens(tokens);
}

const Calculator = forwardRef(
	(
		{
			expression: initialExpression,
			fields,
			functions,
			index,
			onEditExpression,
			resultSelected,
		},
		ref
	) => {
		const [expression, setExpression] = useState(initialExpression);

		const {
			disableDot,
			disableFunctions,
			disableNumbers,
			disableOperators,
			showOnlyRepeatableFields,
		} = useMemo(() => getStateBasedOnExpression(expression), [expression]);

		const repeatableFields = useMemo(() => getRepeatableFields(fields), [
			fields,
		]);

		const dropdownItems = showOnlyRepeatableFields
			? repeatableFields
			: fields;

		const updateExpression = useCallback(
			({index, newExpression}) => {
				const newMaskedExpression = newExpression.replace(/[[\]]/g, '');

				setExpression(newMaskedExpression);

				onEditExpression({
					index,
					newMaskedExpression,
				});
			},
			[onEditExpression, setExpression]
		);

		const handleButtonClick = ({tokenType, tokenValue}) => {
			if (tokenValue === 'backspace') {
				const newExpression = removeTokenFromExpression({expression});

				updateExpression({index, newExpression});
			}
			else {
				const newExpression = addTokenToExpression({
					expression,
					tokenType,
					tokenValue,
				});

				updateExpression({index, newExpression});
			}
		};

		const handleFunctionSelected = ({value}) => {
			const expressionWithFunction = addTokenToExpression({
				expression,
				tokenType: Token.FUNCTION,
				tokenValue: value,
			});
			const newExpression = addTokenToExpression({
				expression: expressionWithFunction,
				tokenType: Token.LEFT_PARENTHESIS,
				tokenValue: '(',
			});

			updateExpression({index, newExpression});
		};

		const handleFieldSelected = ({fieldName}) => {
			const newExpression = addTokenToExpression({
				expression,
				tokenType: Token.VARIABLE,
				tokenValue: fieldName,
			});

			updateExpression({index, newExpression});
		};

		return (
			<ClayLayout.Row ref={ref}>
				<ClayLayout.Col className="no-padding" md={12}>
					<div className="calculate-container">
						<ClayLayout.Col
							className="calculate-container-calculator-component"
							md={3}
						>
							<div className="liferay-ddm-form-builder-calculator">
								<ClayDropDownWithItems
									className="calculator-add-field-button-container"
									items={dropdownItems.map((item) => ({
										label: item.label,
										onClick: () =>
											handleFieldSelected(item),
									}))}
									trigger={
										<ClayButton
											className="btn-lg calculator-add-field-button ddm-button"
											disabled={!dropdownItems.length}
										>
											{Liferay.Language.get('add-field')}
										</ClayButton>
									}
								/>
								<CalculatorButtonArea
									disableDot={disableDot}
									disableFunctions={disableFunctions}
									disableNumbers={disableNumbers}
									disableOperators={disableOperators}
									functions={functions}
									onClick={handleButtonClick}
									onFunctionSelected={handleFunctionSelected}
									resultSelected={resultSelected}
								/>
							</div>
						</ClayLayout.Col>
						<ClayLayout.Col
							className="calculate-container-fields"
							md="9"
						>
							<CalculatorDisplay expression={expression} />
						</ClayLayout.Col>
					</div>
				</ClayLayout.Col>
			</ClayLayout.Row>
		);
	}
);

Calculator.displayName = 'Calculator';

export default Calculator;
