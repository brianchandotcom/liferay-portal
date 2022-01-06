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
import {render, screen} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import moment from 'moment';
import React from 'react';

import DatePicker from '../../../src/main/resources/META-INF/resources/DatePicker/DatePicker.es';

describe('DatePicker', () => {
	it('renders the help text', () => {
		render(<DatePicker tip="Type something" />);

		expect(document.querySelector('.form-text')).toHaveTextContent(
			'Type something'
		);
	});

	it('renders the label', () => {
		render(<DatePicker label="Date picker" />);

		expect(screen.getByText('Date picker')).toBeInTheDocument();
	});

	it('renders the predefined value', () => {
		render(<DatePicker predefinedValue="2020-06-02" />);

		expect(screen.getByRole('textbox')).toHaveValue('06/02/2020');
	});

	it('expands the datepicker on calendar icon click', () => {
		render(<DatePicker />);

		userEvent.click(screen.getByLabelText('Choose date'));

		expect(
			document.body.querySelector('.date-picker-dropdown-menu.show')
		).toBeInTheDocument();
	});

	it('fills the input with the date selected on Date Picker', () => {
		render(<DatePicker onChange={() => {}} />);

		userEvent.click(screen.getByLabelText('Choose date'));
		userEvent.click(screen.getByLabelText('Select current date'));

		expect(screen.getByRole('textbox')).toHaveValue(
			moment().format('MM/DD/YYYY')
		);
	});

	it('calls the onChange callback with a valid date', () => {
		const onChange = jest.fn();

		render(<DatePicker onChange={onChange} />);

		userEvent.click(screen.getByLabelText('Choose date'));
		userEvent.click(screen.getByLabelText('Select current date'));

		expect(onChange).toHaveBeenCalledWith(
			{},
			moment().format('YYYY-MM-DD')
		);
	});

	it('fills the input date according to the locale', () => {
		render(<DatePicker locale="ja_JP" onChange={() => {}} />);

		userEvent.click(screen.getByLabelText('Choose date'));
		userEvent.click(screen.getByLabelText('Select current date'));

		expect(screen.getByRole('textbox')).toHaveValue(
			moment().format('YYYY/MM/DD')
		);
	});

	it('fills the input completely when last item of a date mask is a symbol', () => {
		render(<DatePicker locale="hu_HU" onChange={() => {}} />);

		const input = screen.getByRole('textbox');

		userEvent.type(input, '1111.11.11.');

		expect(input).toHaveValue('1111.11.11.');
	});

	it('sets the hidden input with occidental digits', () => {
		render(
			<DatePicker
				defaultLanguageId="ar_SA"
				name="test-date"
				onChange={() => {}}
				value="2021-01-01"
			/>
		);
		const input = screen.getByRole('textbox');
		const hiddenInput = document.querySelector('[name=test-date]');

		expect(input).toHaveValue('٠١/٠١/٢٠٢١');
		expect(hiddenInput).toHaveValue('2021-01-01');
	});

	/* TODO: remove skip after alow user to input arabic digits */
	it.skip('passes only occidental digits to the onChange callback', () => {
		const onChange = jest.fn();
		render(
			<DatePicker locale="ar_SA" name="test-date" onChange={onChange} />
		);

		const input = screen.getByRole('textbox');

		userEvent.type(input, '٠١/٠١/٢٠٢١');

		expect(onChange).toHaveBeenLastCalledWith('');
	});
});
