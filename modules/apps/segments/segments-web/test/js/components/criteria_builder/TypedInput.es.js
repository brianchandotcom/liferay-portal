import React from 'react';
import TypedInput from 'components/criteria_builder/TypedInput.es';
import {PROPERTY_TYPES} from 'utils/constants.es';
import {cleanup, fireEvent, render} from 'react-testing-library';

const defaultValue = 'defaultValue';

function testControlledInput({element, value, mockFunc, pushingValue = 'Liferay'}) {
	expect(element.value).toBe(value);

	fireEvent.change(
		element,
		{
			target: {value: pushingValue}
		}
	);

	expect(mockFunc.mock.calls.length).toBe(1);
	expect(mockFunc.mock.calls[0][0]).toBe(pushingValue);

	// As the input is controlled

	expect(element.value).toBe(value);
}

describe(
	'CriteriaRow',
	() => {
		afterEach(cleanup);

		it(
			'should render with type string',
			() => {
				const {asFragment} = render(
					<TypedInput
						type="string"
					/>
				);

				expect(asFragment()).toMatchSnapshot();
			}
		);

		it(
			'should render type string with value',
			() => {
				const mockOnChange = jest.fn();

				const {asFragment, getByTestId} = render(
					<TypedInput
						onChange={mockOnChange}
						type="string"
						value={defaultValue}
					/>
				);

				expect(asFragment()).toMatchSnapshot();

				const input = getByTestId('simple-string');

				testControlledInput(
					{
						element: input,
						mockFunc: mockOnChange,
						value: defaultValue
					}
				);
			}
		);

		it(
			'should render type string with pseudotype options',
			() => {
				const mockOnChange = jest.fn();

				const options = [
					{
						label: 'Default Value',
						value: 'defaultValue'
					}, {
						label: 'LIFERAY',
						value: 'Liferay'
					}
				];

				const {asFragment, getByTestId} = render(
					<TypedInput
						onChange={mockOnChange}
						options={options}
						type="string"
						value={defaultValue}
					/>
				);

				expect(asFragment()).toMatchSnapshot();

				const selector = getByTestId('options-string');

				testControlledInput(
					{
						element: selector,
						mockFunc: mockOnChange,
						value: defaultValue
					}
				);
			}
		);

		it(
			'should render type boolean',
			() => {
				const mockOnChange = jest.fn();

				const defaultBoolValue = 'true';

				const {asFragment, getByTestId} = render(
					<TypedInput
						onChange={mockOnChange}
						type={PROPERTY_TYPES.BOOLEAN}
						value={defaultBoolValue}
					/>
				);

				expect(asFragment()).toMatchSnapshot();

				const selector = getByTestId('options-boolean');

				testControlledInput(
					{
						element: selector,
						mockFunc: mockOnChange,
						pushingValue: 'false',
						value: defaultBoolValue
					}
				);
			}
		);

		it(
			'should render type number',
			() => {
				const mockOnChange = jest.fn();

				const defaultNumberValue = '1';

				const {asFragment, getByTestId} = render(
					<TypedInput
						onChange={mockOnChange}
						type={PROPERTY_TYPES.NUMBER}
						value={defaultNumberValue}
					/>
				);

				expect(asFragment()).toMatchSnapshot();

				const selector = getByTestId('simple-number');

				testControlledInput(
					{
						element: selector,
						mockFunc: mockOnChange,
						pushingValue: '2',
						value: defaultNumberValue
					}
				);
			}
		);
	}
);