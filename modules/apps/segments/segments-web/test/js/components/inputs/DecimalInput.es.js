import React from 'react';
import DecimalInput from 'components/inputs/DecimalInput.es';
import {cleanup, fireEvent, render} from 'react-testing-library';
import {testControlledInput} from 'test/utils';

const DECIMAL_NUMBER_INPUT_TESTID = 'decimal-number';

describe(
	'DecimalInput',
	() => {
		afterEach(cleanup);

		it(
			'should render type decimal number',
			() => {
				const mockOnChange = jest.fn();

				const defaultNumberValue = '1.23';
				const newNumberValue = '2.34';

				const {asFragment, getByTestId} = render(
					<DecimalInput
						onChange={mockOnChange}
						value={defaultNumberValue}
					/>
				);

				expect(asFragment()).toMatchSnapshot();

				const element = getByTestId(DECIMAL_NUMBER_INPUT_TESTID);

				testControlledInput(
					{
						element,
						mockFunc: mockOnChange,
						newValue: newNumberValue,
						value: defaultNumberValue
					}
				);
			}
		);

		it(
			'should format the value after blur',
			() => {
				const mockOnChange = jest.fn();

				const {getByTestId} = render(
					<DecimalInput onChange={mockOnChange} />
				);

				const element = getByTestId(DECIMAL_NUMBER_INPUT_TESTID);

				fireEvent.change(
					element,
					{
						target: {value: '1.009'}
					}
				);

				fireEvent.blur(element);

				expect(mockOnChange.mock.calls[1][0]).toBe('1.01');
			}
		);
	}
);