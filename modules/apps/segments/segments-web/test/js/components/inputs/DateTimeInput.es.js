import dateFns from 'date-fns';
import DateTimeInput from 'components/inputs/DateTimeInput.es';
import React from 'react';
import {cleanup, render} from 'react-testing-library';
import {testControlledDateInput} from 'test/utils';

const DATE_INPUT_TESTID = 'date-input';

describe('DateTimeInput', () => {
	afterEach(cleanup);

	it('should render type date', () => {
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

	it('should render now with wrong date', () => {
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
