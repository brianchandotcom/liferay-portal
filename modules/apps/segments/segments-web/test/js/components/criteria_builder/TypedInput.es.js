import React from 'react';
import TypedInput from 'components/criteria_builder/TypedInput.es';
import {cleanup, fireEvent, render} from 'react-testing-library';

const defaultValue = 'defaultValue';

const options = [
	{
		label: 'Default Value',
		value: 'defaultValue'
	}, {
		label: 'LIFERAY',
		value: 'Liferay'
	}
];

function testControlledInput(element, value, mockFunc) {
	expect(element.value).toBe(value);

	fireEvent.change(
		element,
		{
			target: {value: 'Liferay'}
		}
	);

	expect(mockFunc.mock.calls.length).toBe(1);
	expect(mockFunc.mock.calls[0][0]).toBe('Liferay');
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

				testControlledInput(input, defaultValue, mockOnChange);
			}
		);

		it(
			'should render type string with pseudotype options',
			() => {
				const mockOnChange = jest.fn();

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

				testControlledInput(selector, defaultValue, mockOnChange);
			}
		);
	}
);