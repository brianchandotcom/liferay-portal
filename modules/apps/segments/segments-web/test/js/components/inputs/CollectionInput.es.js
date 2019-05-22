import CollectionInput from 'components/inputs/CollectionInput.es';
import React from 'react';
import {cleanup, render} from 'react-testing-library';
import {testControlledInput} from 'test/utils';

const COLLECTION_KEY_INPUT_TESTID = 'collection-key-input';

const COLLECTION_VALUE_INPUT_TESTID = 'collection-value-input';

describe('CollectionInput', () => {
	afterEach(cleanup);

	it('should render collection type', () => {
		const mockOnChange = jest.fn();

		const startingKey = 'testKey';
		const startingValue = 'testValue';

		const {asFragment} = render(
			<CollectionInput
				onChange={mockOnChange}
				value={`${startingKey}=${startingValue}`}
			/>
		);

		expect(asFragment()).toMatchSnapshot();
	});

	it('should render a key input with the right-side value of the equal character', () => {
		const mockOnChange = jest.fn();

		const startingKey = 'testKey';
		const startingValue = 'testValue';

		const {getByTestId} = render(
			<CollectionInput
				onChange={mockOnChange}
				value={`${startingKey}=${startingValue}`}
			/>
		);

		const keyInputElement = getByTestId(COLLECTION_KEY_INPUT_TESTID);

		expect(keyInputElement.value).toBe(startingKey);
	});

	it('should render a value input with the left-side value of the equal character', () => {
		const mockOnChange = jest.fn();

		const startingKey = 'testKey';
		const startingValue = 'testValue';

		const {getByTestId} = render(
			<CollectionInput
				onChange={mockOnChange}
				value={`${startingKey}=${startingValue}`}
			/>
		);

		const valueInputElement = getByTestId(COLLECTION_VALUE_INPUT_TESTID);

		expect(valueInputElement.value).toBe(startingValue);
	});

	it('should have a changeable key input', () => {
		const mockOnChange = jest.fn();

		const startingKey = 'testKey';
		const startingValue = 'testValue';

		const {getByTestId} = render(
			<CollectionInput
				onChange={mockOnChange}
				value={`${startingKey}=${startingValue}`}
			/>
		);

		const keyInputElement = getByTestId(COLLECTION_KEY_INPUT_TESTID);

		testControlledInput({
			element: keyInputElement,
			mockFunc: mockOnChange,
			newValue: 'newKey',
			newValueExpected: `newKey=${startingValue}`,
			value: startingKey
		});
	});

	it('should have a changeable value input', () => {
		const mockOnChange = jest.fn();

		const startingKey = 'testKey';
		const startingValue = 'testValue';

		const {getByTestId} = render(
			<CollectionInput
				onChange={mockOnChange}
				value={`${startingKey}=${startingValue}`}
			/>
		);

		const valueInputElement = getByTestId(COLLECTION_VALUE_INPUT_TESTID);

		testControlledInput({
			element: valueInputElement,
			mockFunc: mockOnChange,
			newValue: 'newValue',
			newValueExpected: `${startingKey}=newValue`,
			value: startingValue
		});
	});
});
