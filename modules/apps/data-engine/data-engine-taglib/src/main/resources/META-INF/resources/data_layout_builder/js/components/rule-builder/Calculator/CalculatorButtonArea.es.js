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

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import React from 'react';

import Dropdown from './Dropdown.es';

const ONE_TO_NINE = ['1', '2', '3', '4', '5', '6', '7', '8', '9'];

const OPERATORS = {
	ADD: {label: Liferay.Language.get('add'), value: '+'},
	DIVIDE: {label: Liferay.Language.get('divide'), value: '/'},
	MULTIPLY: {label: Liferay.Language.get('multiply'), value: '*'},
	SUBTRACT: {label: Liferay.Language.get('subtract'), value: '-'},
};

const TOKEN_TYPES = {
	BACKSPACE: 'Backspace',
	LITERAL: 'Literal',
	OPERATOR: 'Operator',
	PARENTHESIS_LEFT: 'Left Parenthesis',
	PARENTHESIS_RIGHT: 'Parenthesis Right',
};

function CalculatorButtonArea({
	disableDot,
	disableFunctions,
	disableNumbers,
	disableOperators,
	functions,
	onCalculatorInput,
	onFunctionSelected,
	resultSelected,
}) {
	return (
		<div className="calculator-button-area">
			<div className="calculator-buttons calculator-buttons-numbers">
				<ClayButtonWithIcon
					aria-label={Liferay.Language.get('backspace')}
					className="border-top-left calculator-button"
					displayType="secondary"
					onClick={() =>
						onCalculatorInput({
							tokenType: TOKEN_TYPES.BACKSPACE,
							tokenValue: 'backspace',
						})
					}
					symbol="angle-left"
					title={Liferay.Language.get('backspace')}
				/>
				<ClayButton
					aria-label={Liferay.Language.get('left-parenthesis')}
					className="calculator-button"
					disabled={disableNumbers}
					displayType="secondary"
					monospaced
					onClick={() =>
						onCalculatorInput({
							tokenType: TOKEN_TYPES.PARENTHESIS_LEFT,
							tokenValue: '(',
						})
					}
					title={Liferay.Language.get('left-parenthesis')}
				>
					{'('}
				</ClayButton>

				<ClayButton
					aria-label={Liferay.Language.get('right-parenthesis')}
					className="border-top-right calculator-button"
					disabled={disableNumbers}
					displayType="secondary"
					monospaced
					onClick={() =>
						onCalculatorInput({
							tokenType: TOKEN_TYPES.PARENTHESIS_RIGHT,
							tokenValue: ')',
						})
					}
					title={Liferay.Language.get('right-parenthesis')}
				>
					{')'}
				</ClayButton>
				{ONE_TO_NINE.map((value) => (
					<ClayButton
						aria-label={value}
						className="calculator-button"
						disabled={disableNumbers}
						displayType="secondary"
						key={value}
						monospaced
						onClick={() =>
							onCalculatorInput({
								tokenType: TOKEN_TYPES.LITERAL,
								tokenValue: value,
							})
						}
					>
						{value}
					</ClayButton>
				))}
				<ClayButton
					aria-label={Liferay.Language.get('zero')}
					className="border-bottom-left button-two-columns calculator-button"
					disabled={disableNumbers}
					displayType="secondary"
					monospaced
					onClick={() =>
						onCalculatorInput({
							tokenType: TOKEN_TYPES.LITERAL,
							tokenValue: '0',
						})
					}
				>
					{'0'}
				</ClayButton>
				<ClayButton
					aria-label={Liferay.Language.get('period')}
					className="border-bottom-right calculator-button"
					disabled={disableDot || disableNumbers}
					displayType="secondary"
					monospaced
					onClick={() =>
						onCalculatorInput({
							tokenType: TOKEN_TYPES.LITERAL,
							tokenValue: '.',
						})
					}
				>
					{'.'}
				</ClayButton>
			</div>

			<div className="calculator-buttons calculator-buttons-operators">
				<div className="btn-group-vertical" role="group">
					<Dropdown
						className="dropdown-action"
						items={functions}
						onItemSelected={onFunctionSelected}
						resultSelected={resultSelected}
						trigger={
							<ClayButtonWithIcon
								aria-label={Liferay.Language.get('actions')}
								className="calculator-add-operator-button-area"
								disabled={disableFunctions}
								symbol="ellipsis-v"
								title={Liferay.Language.get('actions')}
							/>
						}
					/>
					<ClayButton
						aria-label={OPERATORS.ADD.label}
						className="calculator-button"
						disabled={disableOperators}
						displayType="secondary"
						monospaced
						onClick={() =>
							onCalculatorInput({
								tokenType: TOKEN_TYPES.OPERATOR,
								tokenValue: OPERATORS.ADD.value,
							})
						}
					>
						{OPERATORS.ADD.value}
					</ClayButton>
					<ClayButton
						aria-label={OPERATORS.SUBTRACT.label}
						className="calculator-button"
						disabled={disableOperators}
						displayType="secondary"
						monospaced
						onClick={() =>
							onCalculatorInput({
								tokenType: TOKEN_TYPES.OPERATOR,
								tokenValue: OPERATORS.SUBTRACT.value,
							})
						}
					>
						{OPERATORS.SUBTRACT.value}
					</ClayButton>
					<ClayButton
						aria-label={OPERATORS.MULTIPLY.label}
						className="calculator-button"
						disabled={disableOperators}
						displayType="secondary"
						monospaced
						onClick={() =>
							onCalculatorInput({
								tokenType: TOKEN_TYPES.OPERATOR,
								tokenValue: OPERATORS.MULTIPLY.value,
							})
						}
					>
						{OPERATORS.MULTIPLY.value}
					</ClayButton>
					<ClayButton
						aria-label={OPERATORS.DIVIDE.label}
						className="border-bottom-left border-bottom-right calculator-button"
						disabled={disableOperators}
						displayType="secondary"
						monospaced
						onClick={() =>
							onCalculatorInput({
								tokenType: TOKEN_TYPES.OPERATOR,
								tokenValue: OPERATORS.DIVIDE.value,
							})
						}
					>
						{OPERATORS.DIVIDE.value}
					</ClayButton>
				</div>
			</div>
		</div>
	);
}

export default CalculatorButtonArea;
