import DatePickerInput from '../DatePickerInput';
import React from 'react';
import {cleanup, render} from '@testing-library/react';

jest.unmock('react-dom');

describe('DatePickerInput', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(
			<DatePickerInput onChange={jest.fn()} value='2020-12-12' />
		);

		expect(container).toMatchSnapshot();
	});

	it('should render w/ DateRangeInput', () => {
		const {getByTestId} = render(
			<DatePickerInput
				isRange
				onChange={jest.fn()}
				value={{end: '2020-12-12', start: '2020-12-20'}}
			/>
		);

		expect(getByTestId('date-range-input')).toBeTruthy();
	});

	it('should render w/ error', () => {
		const {container} = render(
			<DatePickerInput
				isRange
				onChange={jest.fn()}
				touched
				valid={false}
				value={{end: '2020-12-12', start: '2020-12-20'}}
			/>
		);

		expect(container.querySelector('.has-error')).toBeTruthy();
	});
});
