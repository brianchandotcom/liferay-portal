/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';

import Calculator from '../../../../../../src/main/resources/META-INF/resources/data_layout_builder/js/components/rules/editor/Calculator.es';

const SIGNALS = ['+', '-', '*', '/'];
const PARENTHESIS = ['(', ')'];
const DOT = ['.'];

const fieldsNonRepeatable = [
	{
		dataType: 'integer',
		fieldName: 'number1',
		label: 'number1',
		name: 'number1',
		options: [],
		repeatable: false,
		title: 'number1',
		type: 'numeric',
		value: '',
	},
];

const fields = [
	{
		dataType: 'integer',
		fieldName: 'number1',
		label: 'number1',
		name: 'number1',
		options: [],
		repeatable: true,
		title: 'number1',
		type: 'numeric',
		value: '',
	},
	{
		dataType: 'integer',
		fieldName: 'number2',
		label: 'number2',
		name: 'number2',
		options: [],
		repeatable: false,
		title: 'number2',
		type: 'numeric',
		value: '',
	},
	{
		dataType: 'integer',
		fieldName: 'total',
		label: 'total',
		name: 'total',
		options: [],
		repeatable: false,
		title: 'total',
		type: 'numeric',
		value: '',
	},
];

const defaultProps = (fieldsList = fields) => {
	return {
		expression: '',
		fields: fieldsList,
		functions: [
			{
				label: 'sum',
				tooltip: '',
				value: 'sum',
			},
		],
		index: 0,
		options: fieldsList,
	};
};

const changeActionObject = (newExpression) => ({
	payload: {
		value: newExpression,
	},
	type: 'CHANGE_ACTION_CALCULATE',
});

let mockOnChange;

describe('Calculator', () => {
	afterEach(() => {
		cleanup();
		jest.restoreAllMocks();
	});

	beforeEach(() => {
		mockOnChange = jest.fn();
	});

	describe("clicking the calculator's signals, numbers and parethesis", () => {
		it('mounts an expression with numbers, signals and parenthesis', () => {
			const props = defaultProps();
			const {getByText} = render(
				<Calculator
					{...props}
					onChange={(newExpression) =>
						mockOnChange(changeActionObject(newExpression))
					}
				/>
			);

			fireEvent.click(getByText('('));
			fireEvent.click(getByText('3'));
			fireEvent.click(getByText('+'));
			fireEvent.click(getByText('5'));
			fireEvent.click(getByText(')'));

			expect(mockOnChange).toHaveBeenLastCalledWith(
				changeActionObject('(3+5)')
			);
		});

		it('does not allow to add consecutive operators', () => {
			const {getByText} = render(
				<Calculator
					{...defaultProps()}
					onChange={(newExpression) =>
						mockOnChange(changeActionObject(newExpression))
					}
				/>
			);

			let button = getByText('+');
			fireEvent.click(button);
			button = getByText('-');
			fireEvent.click(button);
			button = getByText('*');
			fireEvent.click(button);
			button = getByText('/');
			fireEvent.click(button);

			expect(mockOnChange).toHaveBeenLastCalledWith(
				changeActionObject('+')
			);
		});

		it('disables operators when last token is an operator', () => {
			const {getByText} = render(
				<Calculator
					{...defaultProps()}
					expression="3+3"
					onChange={(newExpression) =>
						mockOnChange(changeActionObject(newExpression))
					}
				/>
			);

			fireEvent.click(getByText('+'));

			const symbolsArray = SIGNALS.concat(DOT);

			symbolsArray.forEach((symbol) => {
				expect(getByText(symbol).disabled).toBe(true);
			});
		});
	});

	describe('clicking the Sum function', () => {
		it("don't disable functions, numbers and operators", () => {
			const {getByText} = render(
				<Calculator
					{...defaultProps()}
					onChange={(newExpression) =>
						mockOnChange(changeActionObject(newExpression))
					}
				/>
			);

			fireEvent.click(getByText('sum'));

			const symbolsArray = SIGNALS.concat(PARENTHESIS).concat(DOT);

			for (let i = 0; i < 1; i++) {
				expect(getByText(i.toString()).disabled).toBe(false);
			}

			symbolsArray.forEach((symbol) => {
				if (symbol === DOT.toString()) {
					expect(getByText(symbol).disabled).toBe(true);
				}
				else {
					expect(getByText(symbol).disabled).toBe(false);
				}
			});

			expect(
				document.querySelector('.calculator-add-operator-button-area')
					.disabled
			).toBe(false);
		});

		it("don't disable the add field button when there are repeatable fields ", () => {
			const {getByText} = render(
				<Calculator
					{...defaultProps()}
					onChange={(newExpression) =>
						mockOnChange(changeActionObject(newExpression))
					}
				/>
			);

			fireEvent.click(getByText('sum'));
			expect(getByText('add-field').disabled).toBe(false);
		});

		it("don't disable the add field button when there are no repeatable fields ", () => {
			const {getByText} = render(
				<Calculator
					{...defaultProps(fieldsNonRepeatable)}
					onChange={(newExpression) =>
						mockOnChange(changeActionObject(newExpression))
					}
				/>
			);

			fireEvent.click(getByText('sum'));
			expect(getByText('add-field').disabled).toBe(false);
		});
	});

	describe("clicking the calculator's backspace", () => {
		it('clears the last digit of an expression', () => {
			const {getByLabelText} = render(
				<Calculator
					{...defaultProps()}
					expression="2+3"
					onChange={(newExpression) =>
						mockOnChange(changeActionObject(newExpression))
					}
				/>
			);

			fireEvent.click(getByLabelText('backspace'));

			expect(mockOnChange).toHaveBeenLastCalledWith(
				changeActionObject('2+')
			);
		});

		it('removes both the left parenthesis and the function when last tokens are an openning of a function', () => {
			const {getByLabelText, getByText} = render(
				<Calculator
					{...defaultProps()}
					expression="2+3"
					onChange={(newExpression) =>
						mockOnChange(changeActionObject(newExpression))
					}
				/>
			);

			fireEvent.click(getByText('sum'));

			expect(mockOnChange).toHaveBeenNthCalledWith(
				1,
				changeActionObject('2+3*sum(')
			);

			fireEvent.click(getByLabelText('backspace'));

			expect(mockOnChange).toHaveBeenNthCalledWith(
				2,
				changeActionObject('2+3*')
			);
		});

		it('enables operators when last token before pressing backspace was an operator', () => {
			const {getByLabelText, getByText} = render(
				<Calculator
					{...defaultProps()}
					expression="3+3+"
					onChange={(newExpression) =>
						mockOnChange(changeActionObject(newExpression))
					}
				/>
			);

			const symbolsArray = SIGNALS.concat(DOT);

			symbolsArray.forEach((symbol) => {
				expect(getByText(symbol).disabled).toBe(true);
			});

			fireEvent.click(getByLabelText('backspace'));

			symbolsArray.forEach((symbol) => {
				expect(getByText(symbol).disabled).toBe(false);
			});
		});
	});

	describe("using the calculator's add field button", () => {
		it('shows the dropdown with the available field operands when click the add field button', async () => {
			const {getByText} = render(
				<Calculator
					{...defaultProps()}
					onChange={(newExpression) =>
						mockOnChange(changeActionObject(newExpression))
					}
				/>
			);

			fireEvent.click(getByText('add-field'));

			const dropdown = document.querySelector('.dropdown-menu');

			expect(dropdown.classList.contains('show')).toBe(true);
		});

		it('mounts the expression with the available field operands', () => {
			const {getByText} = render(
				<Calculator
					{...defaultProps()}
					onChange={(newExpression) =>
						mockOnChange(changeActionObject(newExpression))
					}
				/>
			);

			fireEvent.click(getByText('add-field'));
			fireEvent.click(getByText('number1'));

			expect(mockOnChange).toHaveBeenLastCalledWith(
				changeActionObject('[number1]')
			);
		});

		it('adds an implicit multiplication operator between consecutive field operands, number, function and left parenthesis', () => {
			const {getByText} = render(
				<Calculator
					{...defaultProps()}
					onChange={(newExpression) =>
						mockOnChange(changeActionObject(newExpression))
					}
				/>
			);

			fireEvent.click(getByText('3'));
			fireEvent.click(getByText('add-field'));
			fireEvent.click(getByText('number1'));
			fireEvent.click(getByText('5'));
			fireEvent.click(getByText('('));
			fireEvent.click(getByText('add-field'));
			fireEvent.click(getByText('number2'));
			fireEvent.click(getByText('total'));
			fireEvent.click(getByText('sum'));

			expect(mockOnChange).toHaveBeenLastCalledWith(
				changeActionObject('3*[number1]*5*([number2]*[total]*sum(')
			);
		});
	});
});
