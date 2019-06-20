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

import React from 'react';
import StringInput from 'components/inputs/StringInput.es';
import {cleanup, render} from 'react-testing-library';
import {testControlledInput} from 'test/utils';

const OPTIONS_STRING_INPUT_TESTID = 'options-string';

const SIMPLE_STRING_INPUT_TESTID = 'simple-string';

const defaultValue = 'defaultValue';

describe('StringInput', () => {
	afterEach(cleanup);

	it('should render with type string', () => {
		const {asFragment} = render(<StringInput onChange={jest.fn()} />);

		expect(asFragment()).toMatchSnapshot();
	});

	it('should render type string with value', () => {
		const mockOnChange = jest.fn();

		const {asFragment, getByTestId} = render(
			<StringInput onChange={mockOnChange} value={defaultValue} />
		);

		expect(asFragment()).toMatchSnapshot();

		const element = getByTestId(SIMPLE_STRING_INPUT_TESTID);

		testControlledInput({
			element,
			mockFunc: mockOnChange,
			value: defaultValue
		});
	});

	it('should render type string with options', () => {
		const mockOnChange = jest.fn();

		const options = [
			{
				label: 'Default Value',
				value: 'defaultValue'
			},
			{
				label: 'LIFERAY',
				value: 'Liferay'
			}
		];

		const {asFragment, getByTestId} = render(
			<StringInput
				onChange={mockOnChange}
				options={options}
				value={defaultValue}
			/>
		);

		expect(asFragment()).toMatchSnapshot();

		const element = getByTestId(OPTIONS_STRING_INPUT_TESTID);

		testControlledInput({
			element,
			mockFunc: mockOnChange,
			value: defaultValue
		});
	});
});
