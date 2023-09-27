/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import IntegerInput from '../../../../src/main/resources/META-INF/resources/js/components/inputs/IntegerInput';
import {testControlledInput} from '../../utils';

const OPTIONS_INTEGER_NUMBER_INPUT_TESTID = 'options-integer';

const SIMPLE_INTEGER_NUMBER_INPUT_TESTID = 'integer-number';

const defaultNumberValue = '1';

describe('IntegerInput', () => {
	afterEach(cleanup);

	it('renders type integer number', () => {
		const mockOnChange = jest.fn();

		const {asFragment, getByTestId} = render(
			<IntegerInput onChange={mockOnChange} value={defaultNumberValue} />
		);

		expect(asFragment()).toMatchSnapshot();

		const element = getByTestId(SIMPLE_INTEGER_NUMBER_INPUT_TESTID);

		testControlledInput({
			element,
			mockFunc: mockOnChange,
			newValue: '2',
			value: defaultNumberValue,
		});
	});

	it('renders type integer number with options', () => {
		const mockOnChange = jest.fn();

		const options = [
			{
				label: '1',
				value: '1',
			},
			{
				label: '2',
				value: '2',
			},
		];

		const {asFragment, getByTestId} = render(
			<IntegerInput
				onChange={mockOnChange}
				options={options}
				value={defaultNumberValue}
			/>
		);

		expect(asFragment()).toMatchSnapshot();

		const element = getByTestId(OPTIONS_INTEGER_NUMBER_INPUT_TESTID);

		testControlledInput({
			element,
			mockFunc: mockOnChange,
			newValue: '2',
			value: defaultNumberValue,
		});
	});

	it('renders type integer number with options disabled', () => {
		const mockOnChange = jest.fn();

		const options = [
			{
				disabled: true,
				label: '1',
				value: '1',
			},
			{
				label: '2',
				value: '2',
			},
		];

		const {asFragment, getByTestId} = render(
			<IntegerInput
				onChange={mockOnChange}
				options={options}
				value={defaultNumberValue}
			/>
		);

		expect(asFragment()).toMatchSnapshot();

		const element = getByTestId(OPTIONS_INTEGER_NUMBER_INPUT_TESTID);

		testControlledInput({
			element,
			mockFunc: mockOnChange,
			newValue: '2',
			value: defaultNumberValue,
		});
	});
});
