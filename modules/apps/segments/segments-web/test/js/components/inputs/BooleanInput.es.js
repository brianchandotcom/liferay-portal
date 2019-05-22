import BooleanInput from 'components/inputs/BooleanInput.es';
import React from 'react';
import {cleanup, render} from 'react-testing-library';
import {testControlledInput} from 'test/utils';

const OPTIONS_BOOLEAN_INPUT_TESTID = 'options-boolean';

describe('BooleanInput', () => {
	afterEach(cleanup);

	it('should render type boolean', () => {
		const mockOnChange = jest.fn();

		const defaultBoolValue = 'true';

		const {asFragment, getByTestId} = render(
			<BooleanInput onChange={mockOnChange} value={defaultBoolValue} />
		);

		expect(asFragment()).toMatchSnapshot();

		const element = getByTestId(OPTIONS_BOOLEAN_INPUT_TESTID);

		testControlledInput({
			element,
			mockFunc: mockOnChange,
			newValue: 'false',
			value: defaultBoolValue
		});
	});
});
