import DateFilterConjunctionInput from '../DateFilterConjunctionInput';
import React from 'react';
import {cleanup, fireEvent, render} from '@testing-library/react';
import {
	FunctionalOperators,
	RelationalOperators
} from '../../../utils/constants';

jest.unmock('react-dom');

describe('DateFilterConjunctionInput', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container, getAllByText, getByText} = render(
			<DateFilterConjunctionInput
				conjunctionCriterion={{propertyName: 'date'}}
				onChange={jest.fn()}
			/>
		);
		fireEvent.click(getByText('ever'));

		expect(getByText('since')).toBeTruthy();
		expect(getByText('after')).toBeTruthy();
		expect(getByText('before')).toBeTruthy();
		expect(getByText('between')).toBeTruthy();
		expect(getAllByText('ever')[1]).toBeTruthy();
		expect(getByText('on')).toBeTruthy();

		expect(container).toMatchSnapshot();
	});

	it('should render w/ TimePeriodInput', () => {
		const {getByTestId} = render(
			<DateFilterConjunctionInput
				conjunctionCriterion={{
					operatorName: RelationalOperators.GT,
					propertyName: 'date',
					touched: false,
					valid: false,
					value: 'last90Days'
				}}
				onChange={jest.fn()}
			/>
		);

		expect(getByTestId('clay-select')).toBeTruthy();
	});

	it('should render w/ DateInput', () => {
		const {getByTestId} = render(
			<DateFilterConjunctionInput
				conjunctionCriterion={{
					operatorName: RelationalOperators.EQ,
					propertyName: 'date',
					touched: false,
					valid: false,
					value: '2020-12-12'
				}}
				onChange={jest.fn()}
			/>
		);

		expect(getByTestId('date-input')).toBeTruthy();
	});

	it('should render w/ DateRangeInput', () => {
		const {getByTestId} = render(
			<DateFilterConjunctionInput
				conjunctionCriterion={{
					operatorName: FunctionalOperators.Between,
					propertyName: 'date',
					touched: false,
					valid: false,
					value: {end: '2020-12-12', start: '2020-12-20'}
				}}
				onChange={jest.fn()}
			/>
		);

		expect(getByTestId('date-range-input')).toBeTruthy();
	});
});
