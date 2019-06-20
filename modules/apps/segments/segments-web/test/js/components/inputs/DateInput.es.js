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

import dateFns from 'date-fns';
import DateInput from 'components/inputs/DateInput.es';
import React from 'react';
import {cleanup, render} from 'react-testing-library';
import {testControlledDateInput} from 'test/utils';

const DATE_INPUT_TESTID = 'date-input';

describe('DateInput', () => {
	afterEach(cleanup);

	it('should render type date', () => {
		const mockOnChange = jest.fn();

		const defaultNumberValue = '2019-01-23';

		const {asFragment, getByTestId} = render(
			<DateInput onChange={mockOnChange} value={defaultNumberValue} />
		);

		expect(asFragment()).toMatchSnapshot();

		const element = getByTestId(DATE_INPUT_TESTID);

		testControlledDateInput({
			element,
			mockOnChangeFunc: mockOnChange,
			newValue: '2019-01-24',
			newValueExpected: '2019-01-24',
			newValueOnChange: '2019-01-24',
			value: defaultNumberValue
		});
	});

	it('should render now with wrong date', () => {
		const mockOnChange = jest.fn();

		const defaultNumberValue = '2019-01-23';

		const {asFragment, getByTestId} = render(
			<DateInput onChange={mockOnChange} value={defaultNumberValue} />
		);

		expect(asFragment()).toMatchSnapshot();

		const element = getByTestId(DATE_INPUT_TESTID);

		const date = dateFns.format(new Date(), 'YYYY-MM-DD');

		testControlledDateInput({
			element,
			mockOnChangeFunc: mockOnChange,
			newValue: '2019-01-XX',
			newValueExpected: date,
			newValueOnChange: date,
			value: defaultNumberValue
		});
	});
});
