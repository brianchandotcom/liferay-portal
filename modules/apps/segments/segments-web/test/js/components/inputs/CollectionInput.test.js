/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import CollectionInput from '../../../../src/main/resources/META-INF/resources/js/components/inputs/CollectionInput';
import {testControlledInput} from '../../utils';

const COLLECTION_KEY_INPUT_TESTID = 'collection-key-input';

const COLLECTION_VALUE_INPUT_TESTID = 'collection-value-input';

describe('CollectionInput', () => {
	afterEach(cleanup);

	it('renders collection type', () => {
		const mockOnChange = jest.fn();

		const startingKey = 'testKey';
		const startingValue = 'testValue';

		const {asFragment} = render(
			<CollectionInput
				onChange={mockOnChange}
				propertyLabel="Test label"
				value={`${startingKey}=${startingValue}`}
			/>
		);

		expect(asFragment()).toMatchSnapshot();
	});

	it('renders a key input with the right-side value of the equal character', () => {
		const mockOnChange = jest.fn();

		const startingKey = 'testKey';
		const startingValue = 'testValue';

		const {getByTestId} = render(
			<CollectionInput
				onChange={mockOnChange}
				propertyLabel="Test label"
				value={`${startingKey}=${startingValue}`}
			/>
		);

		const keyInputElement = getByTestId(COLLECTION_KEY_INPUT_TESTID);

		expect(keyInputElement.value).toBe(startingKey);
	});

	it('renders a value input with the left-side value of the equal character', () => {
		const mockOnChange = jest.fn();

		const startingKey = 'testKey';
		const startingValue = 'testValue';

		const {getByTestId} = render(
			<CollectionInput
				onChange={mockOnChange}
				propertyLabel="Test label"
				value={`${startingKey}=${startingValue}`}
			/>
		);

		const valueInputElement = getByTestId(COLLECTION_VALUE_INPUT_TESTID);

		expect(valueInputElement.value).toBe(startingValue);
	});

	it('has a changeable key input', () => {
		const mockOnChange = jest.fn();

		const startingKey = 'testKey';
		const startingValue = 'testValue';

		const {getByTestId} = render(
			<CollectionInput
				onChange={mockOnChange}
				propertyLabel="Test label"
				value={`${startingKey}=${startingValue}`}
			/>
		);

		const keyInputElement = getByTestId(COLLECTION_KEY_INPUT_TESTID);

		testControlledInput({
			element: keyInputElement,
			mockFunc: mockOnChange,
			newValue: 'newKey',
			newValueExpected: `newKey=${startingValue}`,
			value: startingKey,
		});
	});

	it('has a changeable value input', () => {
		const mockOnChange = jest.fn();

		const startingKey = 'testKey';
		const startingValue = 'testValue';

		const {getByTestId} = render(
			<CollectionInput
				onChange={mockOnChange}
				propertyLabel="Test label"
				value={`${startingKey}=${startingValue}`}
			/>
		);

		const valueInputElement = getByTestId(COLLECTION_VALUE_INPUT_TESTID);

		testControlledInput({
			element: valueInputElement,
			mockFunc: mockOnChange,
			newValue: 'newValue',
			newValueExpected: `${startingKey}=newValue`,
			value: startingValue,
		});
	});
});
