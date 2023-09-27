/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';

import DecimalInput from '../../../../src/main/resources/META-INF/resources/js/components/inputs/DecimalInput';
import {testControlledInput} from '../../utils';

const OPTIONS_DECIMAL_NUMBER_INPUT_TESTID = 'options-decimal';

const SIMPLE_DECIMAL_NUMBER_INPUT_TESTID = 'decimal-number';

const defaultNumberValue = '1.00';

describe('DecimalInput', () => {
	afterEach(cleanup);

	it('renders type decimal number', () => {
		const mockOnChange = jest.fn();

		const defaultNumberValue = '1.23';
		const newNumberValue = '2.34';

		const {asFragment, getByTestId} = render(
			<DecimalInput
				onChange={mockOnChange}
				propertyLabel="Test label"
				value={defaultNumberValue}
			/>
		);

		expect(asFragment()).toMatchSnapshot();

		const element = getByTestId(SIMPLE_DECIMAL_NUMBER_INPUT_TESTID);

		testControlledInput({
			element,
			mockFunc: mockOnChange,
			newValue: newNumberValue,
			value: defaultNumberValue,
		});
	});

	it('formats the value after blur', () => {
		const mockOnChange = jest.fn();

		const {getByTestId} = render(
			<DecimalInput onChange={mockOnChange} propertyLabel="Test label" />
		);

		const element = getByTestId(SIMPLE_DECIMAL_NUMBER_INPUT_TESTID);

		fireEvent.change(element, {
			target: {value: '1.009'},
		});

		fireEvent.blur(element);

		expect(mockOnChange.mock.calls[1][0]).toMatchObject({value: '1.01'});
	});

	it('renders type decimal number with options', () => {
		const mockOnChange = jest.fn();

		const options = [
			{
				label: '1.00',
				value: '1.00',
			},
			{
				label: '2.00',
				value: '2.00',
			},
		];

		const {asFragment, getByTestId} = render(
			<DecimalInput
				onChange={mockOnChange}
				options={options}
				propertyLabel="Test label"
				value={defaultNumberValue}
			/>
		);

		expect(asFragment()).toMatchSnapshot();

		const element = getByTestId(OPTIONS_DECIMAL_NUMBER_INPUT_TESTID);

		testControlledInput({
			element,
			mockFunc: mockOnChange,
			newValue: '2.00',
			value: defaultNumberValue,
		});
	});

	it('renders type decimal number with options disabled', () => {
		const mockOnChange = jest.fn();

		const options = [
			{
				disabled: true,
				label: '1.00',
				value: '1.00',
			},
			{
				label: '2.00',
				value: '2.00',
			},
		];

		const {asFragment, getByTestId} = render(
			<DecimalInput
				onChange={mockOnChange}
				options={options}
				propertyLabel="Test label"
				value={defaultNumberValue}
			/>
		);

		expect(asFragment()).toMatchSnapshot();

		const element = getByTestId(OPTIONS_DECIMAL_NUMBER_INPUT_TESTID);

		testControlledInput({
			element,
			mockFunc: mockOnChange,
			newValue: '2.00',
			value: defaultNumberValue,
		});
	});
});
