import React from 'react';
import TypedInput from 'components/criteria_builder/TypedInput.es';
import {PROPERTY_TYPES} from 'utils/constants.es';
import {cleanup, fireEvent, render} from 'react-testing-library';

const DATE_INPUT_TESTID = 'date-input';

const DECIMAL_NUMBER_INPUT_TESTID = 'decimal-number';

const ENTITY_SELECT_INPUT_TESTID = 'entity-select-input';

const INTEGER_NUMBER_INPUT_TESTID = 'integer-number';

const OPTIONS_BOOLEAN_INPUT_TESTID = 'options-boolean';

const OPTIONS_STRING_INPUT_TESTID = 'options-string';

const SIMPLE_STRING_INPUT_TESTID = 'simple-string';

const defaultValue = 'defaultValue';

function testControlledInput(
	{
		element,
		mockFunc,
		newValue = 'Liferay',
		newValueExpected,
		value
	}
) {
	expect(element.value).toBe(value);

	fireEvent.change(
		element,
		{
			target: {value: newValue}
		}
	);

	expect(mockFunc.mock.calls.length).toBe(1);
	expect(mockFunc.mock.calls[0][0]).toBe(newValueExpected || newValue);

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
						onChange={jest.fn()}
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

				const element = getByTestId(SIMPLE_STRING_INPUT_TESTID);

				testControlledInput(
					{
						element,
						mockFunc: mockOnChange,
						value: defaultValue
					}
				);
			}
		);

		it(
			'should render type string with options',
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

				const element = getByTestId(OPTIONS_STRING_INPUT_TESTID);

				testControlledInput(
					{
						element,
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

				const element = getByTestId(OPTIONS_BOOLEAN_INPUT_TESTID);

				testControlledInput(
					{
						element,
						mockFunc: mockOnChange,
						newValue: 'false',
						value: defaultBoolValue
					}
				);
			}
		);

		it(
			'should render type integer number',
			() => {
				const mockOnChange = jest.fn();

				const defaultNumberValue = '1';

				const {asFragment, getByTestId} = render(
					<TypedInput
						onChange={mockOnChange}
						type={PROPERTY_TYPES.INTEGER}
						value={defaultNumberValue}
					/>
				);

				expect(asFragment()).toMatchSnapshot();

				const element = getByTestId(INTEGER_NUMBER_INPUT_TESTID);

				testControlledInput(
					{
						element,
						mockFunc: mockOnChange,
						newValue: '2',
						value: defaultNumberValue
					}
				);
			}
		);

		it(
			'should render type decimal number',
			() => {
				const mockOnChange = jest.fn();

				const defaultNumberValue = '1.23';
				const newNumberValue = '2.34';

				const {asFragment, getByTestId} = render(
					<TypedInput
						onChange={mockOnChange}
						type={PROPERTY_TYPES.DOUBLE}
						value={defaultNumberValue}
					/>
				);

				expect(asFragment()).toMatchSnapshot();

				const element = getByTestId(DECIMAL_NUMBER_INPUT_TESTID);

				// Needs to be tested a different way from other inputs since
				// the onChange is called in onBlur rather than on input change.

				expect(element.value).toBe(defaultNumberValue);

				fireEvent.change(
					element,
					{
						target: {value: newNumberValue}
					}
				);

				fireEvent.blur(element);

				expect(element.value).toBe(newNumberValue);
			}
		);

		it(
			'should render type date',
			() => {
				const mockOnChange = jest.fn();

				const defaultNumberValue = '2019-01-23';

				const {asFragment, getByTestId} = render(
					<TypedInput
						onChange={mockOnChange}
						type={PROPERTY_TYPES.DATE}
						value={defaultNumberValue}
					/>
				);

				expect(asFragment()).toMatchSnapshot();

				const element = getByTestId(DATE_INPUT_TESTID);

				testControlledInput(
					{
						element,
						mockFunc: mockOnChange,
						newValue: '2019-01-24',
						newValueExpected: '2019-01-24T00:00:00.000Z',
						value: defaultNumberValue
					}
				);
			}
		);

		it(
			'should render type id',
			() => {
				const mockOnChange = jest.fn();

				const defaultNumberValue = '12345';

				const {getByTestId} = render(
					<TypedInput
						onChange={mockOnChange}
						selectEntity={{
							id: 'entitySelect',
							title: 'Select Entity Test',
							url: ''
						}}
						type={PROPERTY_TYPES.ID}
						value={defaultNumberValue}
					/>
				);

				const element = getByTestId(ENTITY_SELECT_INPUT_TESTID);

				expect(element.value).toBe(defaultNumberValue);
			}
		);
	}
);