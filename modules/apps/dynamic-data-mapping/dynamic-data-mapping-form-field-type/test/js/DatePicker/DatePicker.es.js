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

import '@testing-library/jest-dom/extend-expect';
import {render} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import moment from 'moment';
import React from 'react';

import DatePicker from '../../../src/main/resources/META-INF/resources/DatePicker/DatePicker.es';

describe('DatePicker', () => {
	it('renders the help text', () => {
		const {container} = render(<DatePicker tip="Type something" />);

		expect(container.querySelector('.form-text')).toHaveTextContent(
			'Type something'
		);
	});

	it('renders the label', () => {
		const {getByText} = render(<DatePicker label="Date picker" />);

		expect(getByText('Date picker')).toBeInTheDocument();
	});

	it('renders the predefined value', () => {
		const {container} = render(<DatePicker predefinedValue="2020-06-02" />);

		expect(container.querySelector('[type=text]')).toHaveValue(
			'06/02/2020'
		);
	});

	it('expands the datepicker on calendar icon click', async () => {
		const {getByLabelText} = render(<DatePicker />);

		userEvent.click(getByLabelText('Choose date'));

		expect(
			document.body.querySelector('.date-picker-dropdown-menu.show')
		).toBeInTheDocument();
	});

	it('fills the input with the date selected on Date Picker', async () => {
		const {container, getByLabelText} = render(
			<DatePicker onChange={() => {}} />
		);

		userEvent.click(getByLabelText('Choose date'));
		userEvent.click(getByLabelText('Select current date'));

		expect(container.querySelector('[type=text]')).toHaveValue(
			moment().format('MM/DD/YYYY')
		);
	});

	it('calls the onChange callback with a valid date', async () => {
		const onChange = jest.fn();

		const {getByLabelText} = render(<DatePicker onChange={onChange} />);

		userEvent.click(getByLabelText('Choose date'));
		userEvent.click(getByLabelText('Select current date'));

		expect(onChange).toHaveBeenCalledWith(
			{},
			moment().format('YYYY-MM-DD')
		);
	});

	it('fills the input date according to the locale', () => {
		const {container, getByLabelText} = render(
			<DatePicker locale="ja_JP" onChange={() => {}} />
		);

		userEvent.click(getByLabelText('Choose date'));
		userEvent.click(getByLabelText('Select current date'));

		expect(container.querySelector('[type=text]')).toHaveValue(
			moment().format('YYYY/MM/DD')
		);
	});

	it('fills the input completely when last item of a date mask is a symbol', () => {
		const {container} = render(
			<DatePicker locale="hu_HU" onChange={() => {}} />
		);

		const input = container.querySelector('[type=text]');

		userEvent.type(input, '1111.11.11.');

		expect(input).toHaveValue('1111.11.11.');
	});

	it('uses only occidental digits into the hidden input', () => {
		const {container} = render(
			<DatePicker
				defaultLanguageId="ar_SA"
				locale="ar_SA"
				name="test-date"
				onChange={() => {}}
				predefinedValue="٠١/٠١/٢٠٢١"
			/>
		);

		const hiddenInput = container.querySelector('[name=test-date]');

		expect(hiddenInput).toHaveValue('2021-01-01');
	});
});
