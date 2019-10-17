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

import {cleanup, render} from '@testing-library/react';
import dateFns from 'date-fns';
import React from 'react';

import DateTimeInput from '../../../../src/main/resources/META-INF/resources/js/components/inputs/DateTimeInput.es';
import {testControlledDateInput} from '../../utils';

const DATE_INPUT_TESTID = 'date-input';

describe('DateTimeInput', () => {
	afterEach(cleanup);

	it('renders type date', () => {
		const mockOnChange = jest.fn();

		const defaultValue = '2019-01-23';
		const isoDefaultDate = '2019-01-23T00:00:00.000Z';

		const {asFragment, getByTestId} = render(
			<DateTimeInput onChange={mockOnChange} value={isoDefaultDate} />
		);

		expect(asFragment()).toMatchSnapshot();

		const element = getByTestId(DATE_INPUT_TESTID);

		testControlledDateInput({
			element,
			mockOnChangeFunc: mockOnChange,
			newValue: '2019-01-24',
			newValueExpected: '2019-01-24',
			newValueOnChange: '2019-01-24T00:00:00.000Z',
			value: defaultValue
		});
	});

	it('renders now with wrong date', () => {
		const mockOnChange = jest.fn();

		const defaultValue = '2019-01-23';
		const isoDefaultDate = '2019-01-23T00:00:00.000Z';

		const {asFragment, getByTestId} = render(
			<DateTimeInput onChange={mockOnChange} value={isoDefaultDate} />
		);

		expect(asFragment()).toMatchSnapshot();

		const element = getByTestId(DATE_INPUT_TESTID);

		const date = dateFns.format(new Date(), 'YYYY-MM-DD');

		testControlledDateInput({
			element,
			mockOnChangeFunc: mockOnChange,
			newValue: '2019-01-XX',
			newValueExpected: date,
			newValueOnChange: dateFns.parse(date, 'YYYY-MM-DD').toISOString(),
			value: defaultValue
		});
	});
});
