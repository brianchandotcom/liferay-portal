import IntegerInput from 'components/inputs/IntegerInput.es';
import React from 'react';
import {cleanup, render} from 'react-testing-library';
import {testControlledInput} from 'test/utils';

const INTEGER_NUMBER_INPUT_TESTID = 'integer-number';

describe('IntegerInput', () => {
	afterEach(cleanup);

	it('should render type integer number', () => {
		const mockOnChange = jest.fn();

		const defaultNumberValue = '1';

		const {asFragment, getByTestId} = render(
			<IntegerInput onChange={mockOnChange} value={defaultNumberValue} />
		);

		expect(asFragment()).toMatchSnapshot();

		const element = getByTestId(INTEGER_NUMBER_INPUT_TESTID);

		testControlledInput({
			element,
			mockFunc: mockOnChange,
			newValue: '2',
			value: defaultNumberValue
		});
	});
});
