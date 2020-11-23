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
import {ClayDropDownWithItems} from '@clayui/drop-down';
import Token from 'dynamic-data-mapping-form-builder/js/expressions/Token.es';
import React from 'react';

const ONE_TO_NINE = ['1', '2', '3', '4', '5', '6', '7', '8', '9'];

const OPERATORS = [
	{label: Liferay.Language.get('add'), value: '+'},
	{label: Liferay.Language.get('divide'), value: '/'},
	{label: Liferay.Language.get('multiply'), value: '*'},
	{label: Liferay.Language.get('subtract'), value: '-'},
];

const TOKEN_TYPES = {
	BACKSPACE: 'Backspace',
	LITERAL: Token.LITERAL,
	OPERATOR: Token.OPERATOR,
	PARENTHESIS_LEFT: Token.LEFT_PARENTHESIS,
	PARENTHESIS_RIGHT: Token.RIGHT_PARENTHESIS,
};
function CalculatorButtonArea({
	disableDot,
	disableFunctions,
	disableNumbers,
	disableOperators,
	functions,
	onClick,
	onFunctionSelected,
}) {
	return (
		<div className="calculator-button-area">
			<div className="calculator-buttons calculator-buttons-numbers">
				<ClayButtonWithIcon
					aria-label={Liferay.Language.get('backspace')}
					className="border-top-left calculator-button"
					displayType="secondary"
					onClick={() =>
						onClick({
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
						onClick({
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
						onClick({
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
						className="calculator-button"
						disabled={disableNumbers}
						displayType="secondary"
						key={value}
						monospaced
						onClick={() =>
							onClick({
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
						onClick({
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
						onClick({
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
					<ClayDropDownWithItems
						className="dropdown-action"
						items={functions.map((item) => ({
							label: item.label,
							onClick: () => onFunctionSelected(item),
						}))}
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
					{OPERATORS.map(({label, value}) => (
						<ClayButton
							aria-label={label}
							className="calculator-button"
							disabled={disableOperators}
							displayType="secondary"
							key={label}
							monospaced
							onClick={() =>
								onClick({
									tokenType: TOKEN_TYPES.OPERATOR,
									tokenValue: value,
								})
							}
						>
							{value}
						</ClayButton>
					))}
				</div>
			</div>
		</div>
	);
}

export default CalculatorButtonArea;
