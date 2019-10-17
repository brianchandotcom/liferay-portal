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

import {cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';

import DecimalInput from '../../../../src/main/resources/META-INF/resources/js/components/inputs/DecimalInput.es';
import {testControlledInput} from '../../utils';

const DECIMAL_NUMBER_INPUT_TESTID = 'decimal-number';

describe('DecimalInput', () => {
	afterEach(cleanup);

	it('renders type decimal number', () => {
		const mockOnChange = jest.fn();

		const defaultNumberValue = '1.23';
		const newNumberValue = '2.34';

		const {asFragment, getByTestId} = render(
			<DecimalInput onChange={mockOnChange} value={defaultNumberValue} />
		);

		expect(asFragment()).toMatchSnapshot();

		const element = getByTestId(DECIMAL_NUMBER_INPUT_TESTID);

		testControlledInput({
			element,
			mockFunc: mockOnChange,
			newValue: newNumberValue,
			value: defaultNumberValue
		});
	});

	it('formats the value after blur', () => {
		const mockOnChange = jest.fn();

		const {getByTestId} = render(<DecimalInput onChange={mockOnChange} />);

		const element = getByTestId(DECIMAL_NUMBER_INPUT_TESTID);

		fireEvent.change(element, {
			target: {value: '1.009'}
		});

		fireEvent.blur(element);

		expect(mockOnChange.mock.calls[1][0]).toMatchObject({value: '1.01'});
	});
});
