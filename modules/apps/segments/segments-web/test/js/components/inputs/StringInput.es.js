import React from 'react';
import StringInput from 'components/inputs/StringInput.es';
import {cleanup, render} from 'react-testing-library';
import {testControlledInput} from 'test/utils';

const OPTIONS_STRING_INPUT_TESTID = 'options-string';

const SIMPLE_STRING_INPUT_TESTID = 'simple-string';

const defaultValue = 'defaultValue';

describe('StringInput', () => {
	afterEach(cleanup);

	it('should render with type string', () => {
		const {asFragment} = render(<StringInput onChange={jest.fn()} />);

		expect(asFragment()).toMatchSnapshot();
	});

	it('should render type string with value', () => {
		const mockOnChange = jest.fn();

		const {asFragment, getByTestId} = render(
			<StringInput onChange={mockOnChange} value={defaultValue} />
		);

		expect(asFragment()).toMatchSnapshot();

		const element = getByTestId(SIMPLE_STRING_INPUT_TESTID);

		testControlledInput({
			element,
			mockFunc: mockOnChange,
			value: defaultValue
		});
	});

	it('should render type string with options', () => {
		const mockOnChange = jest.fn();

		const options = [
			{
				label: 'Default Value',
				value: 'defaultValue'
			},
			{
				label: 'LIFERAY',
				value: 'Liferay'
			}
		];

		const {asFragment, getByTestId} = render(
			<StringInput
				onChange={mockOnChange}
				options={options}
				value={defaultValue}
			/>
		);

		expect(asFragment()).toMatchSnapshot();

		const element = getByTestId(OPTIONS_STRING_INPUT_TESTID);

		testControlledInput({
			element,
			mockFunc: mockOnChange,
			value: defaultValue
		});
	});
});
