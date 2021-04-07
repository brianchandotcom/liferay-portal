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

import './Calculator.scss';

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayForm from '@clayui/form';
import ClayLayout from '@clayui/layout';
import {FieldStateless} from 'data-engine-js-components-web';
import RulesSupport from 'dynamic-data-mapping-form-builder/js/components/RuleBuilder/RulesSupport.es';
import Token from 'dynamic-data-mapping-form-builder/js/expressions/Token.es';
import Tokenizer from 'dynamic-data-mapping-form-builder/js/expressions/Tokenizer.es';
import React, {forwardRef, useMemo, useState} from 'react';

import CalculatorButtonArea from './CalculatorButtonArea.es';

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

/**
 * A Token can be have one of the following types:
 *
 * Token.FUNCTION = 'Function';
 * Token.LEFT_PARENTHESIS = 'Left Parenthesis';
 * Token.LITERAL = 'Literal';
 * Token.OPERATOR = 'Operator';
 * Token.RIGHT_PARENTHESIS = 'Right Parenthesis';
 * Token.VARIABLE = 'Variable';
 *
 * See https://github.com/liferay/liferay-portal/blob/e066954b019e5fcf42ca45b69fd4da595ad58029/modules/apps/dynamic-data-mapping/dynamic-data-mapping-form-builder/src/main/resources/META-INF/resources/js/expressions/Token.es.js#L26
 * for more details.
 *
 * Each key in the following dictionary represents the type of a token in which the
 * value is matched with the possible tokens that can add an implicit multiplication.
 */
const TOKEN_TYPE_ACCEPTS_IMPLICIT_MULTIPLICATION = {
	[Token.LEFT_PARENTHESIS]: [
		Token.LEFT_PARENTHESIS,
		Token.LITERAL,
		Token.RIGHT_PARENTHESIS,
		Token.VARIABLE,
	],
	[Token.FUNCTION]: [
		Token.FUNCTION,
		Token.LITERAL,
		Token.RIGHT_PARENTHESIS,
		Token.VARIABLE,
	],
	[Token.VARIABLE]: [Token.VARIABLE, Token.LITERAL],
	[Token.LITERAL]: [Token.VARIABLE, Token.FUNCTION],
	[Token.OPERATOR]: [],
	[Token.RIGHT_PARENTHESIS]: [],
};

function isImplicitMultiplication(lastToken, newToken) {
	return TOKEN_TYPE_ACCEPTS_IMPLICIT_MULTIPLICATION[newToken.type].includes(
		lastToken.type
	);
}

function shouldAddImplicitMultiplication(tokens, newToken) {
	const lastToken = tokens[tokens.length - 1];

	return (
		lastToken !== undefined && isImplicitMultiplication(lastToken, newToken)
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

function FieldsDropdown({items, onFieldSelected = () => {}, ...otherProps}) {
	const [active, setActive] = useState(false);

	return (
		<ClayDropDown
			active={active}
			onActiveChange={setActive}
			{...otherProps}
		>
			<ClayDropDown.ItemList>
				{items.map((item, i) => {
					if (!item.separator) {
						return (
							<ClayDropDown.Item
								aria-label={item.label}
								key={item.fieldReference}
								onClick={() => {
									onFieldSelected(item);
									setActive(false);
								}}
							>
								{item.label}
								{item.fieldReference && (
									<span className="calculate-field-reference">
										{` ${Liferay.Language.get(
											'field-reference'
										)}: ${item.fieldReference}`}
									</span>
								)}
							</ClayDropDown.Item>
						);
					}

					return <ClayDropDown.Divider key={i} />;
				})}
			</ClayDropDown.ItemList>
		</ClayDropDown>
	);
}

const Calculator = forwardRef(
	(
		{expression: initialExpression, fields, functions, onChange, options},
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

		const repeatableFields = useMemo(() => getRepeatableFields(options), [
			options,
		]);

		const value = useMemo(() => {
			const newMaskedExpression = expression.replace(/[[\]]/g, '');

			return (
				RulesSupport.replaceFieldNameByFieldLabel(
					newMaskedExpression,
					fields
				) ?? newMaskedExpression
			);
		}, [expression, fields]);

		const dropdownItems = showOnlyRepeatableFields
			? repeatableFields
			: options;

		const updateExpression = (newExpression) => {
			setExpression(newExpression);
			onChange(newExpression);
		};

		const handleButtonClick = ({tokenType, tokenValue}) => {
			if (tokenValue === 'backspace') {
				const newExpression = removeTokenFromExpression({expression});

				updateExpression(newExpression);
			}
			else {
				const newExpression = addTokenToExpression({
					expression,
					tokenType,
					tokenValue,
				});

				updateExpression(newExpression);
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

			updateExpression(newExpression);
		};

		const handleFieldSelected = ({fieldName}) => {
			const newExpression = addTokenToExpression({
				expression,
				tokenType: Token.VARIABLE,
				tokenValue: fieldName,
			});

			updateExpression(newExpression);
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
								<FieldsDropdown
									className="calculator-add-field-button-container"
									items={dropdownItems}
									onFieldSelected={handleFieldSelected}
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
								/>
							</div>
						</ClayLayout.Col>
						<ClayLayout.Col
							className="calculate-container-fields"
							md="9"
						>
							<ClayForm.Group>
								<FieldStateless
									displayStyle="multiline"
									name="calculator-expression"
									placeholder={Liferay.Language.get(
										'the-expression-will-be-displayed-here'
									)}
									readOnly
									showLabel={false}
									type="text"
									value={value}
								/>
							</ClayForm.Group>
						</ClayLayout.Col>
					</div>
				</ClayLayout.Col>
			</ClayLayout.Row>
		);
	}
);

Calculator.displayName = 'Calculator';

export default Calculator;
