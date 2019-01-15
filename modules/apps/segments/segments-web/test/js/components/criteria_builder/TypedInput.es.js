import React from 'react';
import TypedInput from 'components/criteria_builder/TypedInput.es';
import { PROPERTY_TYPES } from 'utils/constants.es';
import {cleanup, render, fireEvent} from 'react-testing-library';

const defaultValue = "defaultValue";

function testControlledInput ({element, value, mockFunc, pushingValue = 'Liferay'}) {
	expect(element.value).toBe(value);
				
	fireEvent.change(element, { target: { value: pushingValue } })

	expect(mockFunc.mock.calls.length).toBe(1);
	expect(mockFunc.mock.calls[0][0]).toBe(pushingValue);
	expect(element.value).toBe(value); // as the input is controlled
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

				testControlledInput({element: input, value: defaultValue, mockFunc: mockOnChange});
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

				testControlledInput({element: selector, value: defaultValue, mockFunc: mockOnChange});
			}
		);
		it(
			'should render type boolean ',
			() => {
				const mockOnChange = jest.fn();
				const defaultBoolValue = 'true';
				const {asFragment, getByTestId} = render(
					<TypedInput
						type={PROPERTY_TYPES.BOOLEAN}
						value={defaultBoolValue}
						onChange={mockOnChange}
					/>
				);

				expect(asFragment()).toMatchSnapshot();

				const selector = getByTestId('options-boolean');

				testControlledInput({element: selector, value: defaultBoolValue, mockFunc: mockOnChange, pushingValue: 'false'});
			}
		);
		it(
			'should render type number ',
			() => {
				const mockOnChange = jest.fn();
				const defaultNumberValue = '1';
				const {asFragment, getByTestId} = render(
					<TypedInput
						type={PROPERTY_TYPES.NUMBER}
						value={defaultNumberValue}
						onChange={mockOnChange}
					/>
				);

				expect(asFragment()).toMatchSnapshot();

				const selector = getByTestId('simple-number');

				testControlledInput({element: selector, value: defaultNumberValue, mockFunc: mockOnChange, pushingValue: '2'});
			}
		);
	}
);