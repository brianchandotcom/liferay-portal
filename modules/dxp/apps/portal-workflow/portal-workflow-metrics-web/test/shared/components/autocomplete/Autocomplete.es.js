/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';

import {Autocomplete} from '../../../../src/main/resources/META-INF/resources/js/shared/components/autocomplete/Autocomplete.es';

import '@testing-library/jest-dom/extend-expect';

const items = [
	{id: 1, name: '0test test0'},
	{id: 2, name: '1test test1'},
	{id: 3, name: '2test test2'}
];

describe('The Autocomplete component should', () => {
	let getByTestId;
	let getAllByTestId;

	const onChange = jest.fn();
	const onSelect = jest.fn();

	afterEach(cleanup);

	beforeEach(() => {
		const autocomplete = render(
			<Autocomplete
				items={items}
				onChange={onChange}
				onSelect={onSelect}
			/>
		);

		getAllByTestId = autocomplete.getAllByTestId;
		getByTestId = autocomplete.getByTestId;
	});

	test('Show the dropdown list on focus input', () => {
		const autocompleteInput = getByTestId('autocompleteInput');
		const dropDownList = getByTestId('dropDownList');
		const dropDown = dropDownList.parentNode;
		const dropDownListItems = getAllByTestId('dropDownListItem');

		expect(dropDown).not.toHaveClass('show');

		fireEvent.focus(autocompleteInput);

		expect(dropDown).toHaveClass('show');

		fireEvent.mouseDown(dropDownListItems[0]);

		expect(autocompleteInput.value).toBe('0test test0');
		expect(dropDown).not.toHaveClass('show');

		fireEvent.focus(autocompleteInput);

		expect(dropDown).toHaveClass('show');

		fireEvent.change(autocompleteInput, {target: {value: 'test'}});
		fireEvent.blur(autocompleteInput);

		expect(autocompleteInput.value).toBe('');
		expect(dropDown).not.toHaveClass('show');
	});

	test('Render its items list and select any option', () => {
		const autocompleteInput = getByTestId('autocompleteInput');
		const dropDownListItems = getAllByTestId('dropDownListItem');

		fireEvent.focus(autocompleteInput);

		expect(dropDownListItems[0]).toHaveTextContent('0test test0');
		expect(dropDownListItems[1]).toHaveTextContent('1test test1');
		expect(dropDownListItems[2]).toHaveTextContent('2test test2');

		expect(dropDownListItems[0]).not.toHaveClass('active');
		expect(dropDownListItems[1]).not.toHaveClass('active');
		expect(dropDownListItems[2]).not.toHaveClass('active');

		fireEvent.mouseOver(dropDownListItems[2]);

		expect(dropDownListItems[0]).not.toHaveClass('active');
		expect(dropDownListItems[1]).not.toHaveClass('active');
		expect(dropDownListItems[2]).toHaveClass('active');

		fireEvent.mouseOver(dropDownListItems[0]);

		expect(dropDownListItems[0]).toHaveClass('active');
		expect(dropDownListItems[1]).not.toHaveClass('active');
		expect(dropDownListItems[2]).not.toHaveClass('active');

		fireEvent.keyDown(autocompleteInput, {keyCode: 40});

		expect(dropDownListItems[0]).not.toHaveClass('active');
		expect(dropDownListItems[1]).toHaveClass('active');
		expect(dropDownListItems[2]).not.toHaveClass('active');

		fireEvent.keyDown(autocompleteInput, {keyCode: 40});

		expect(dropDownListItems[0]).not.toHaveClass('active');
		expect(dropDownListItems[1]).not.toHaveClass('active');
		expect(dropDownListItems[2]).toHaveClass('active');

		fireEvent.keyDown(autocompleteInput, {keyCode: 38});

		expect(dropDownListItems[0]).not.toHaveClass('active');
		expect(dropDownListItems[1]).toHaveClass('active');
		expect(dropDownListItems[2]).not.toHaveClass('active');

		fireEvent.keyDown(autocompleteInput, {keyCode: 13});

		expect(onSelect).toHaveBeenCalledWith(items[1]);

		expect(autocompleteInput.value).toBe('1test test1');
	});

	test('Fire onChange handler function on change its text and clear input onBlur without select any option', () => {
		const autocompleteInput = getByTestId('autocompleteInput');

		fireEvent.focus(autocompleteInput);
		fireEvent.change(autocompleteInput, {target: {value: '0te'}});

		expect(onChange).toHaveBeenCalled();

		fireEvent.blur(autocompleteInput);

		expect(autocompleteInput.value).toBe('');
	});
});

describe('The Autocomplete component with children should', () => {
	let getByTestId;

	afterEach(cleanup);

	beforeEach(() => {
		const autocomplete = render(
			<Autocomplete items={items}>
				<span data-testid="mockChild">Mock child</span>
			</Autocomplete>
		);

		getByTestId = autocomplete.getByTestId;
	});

	test('Render the children', () => {
		const mockChild = getByTestId('mockChild');

		expect(mockChild).toHaveTextContent('Mock child');
	});
});

describe('The Autocomplete component should be render with no items', () => {
	let getByTestId;

	afterEach(cleanup);

	beforeEach(() => {
		const autocomplete = render(<Autocomplete items={[]} />);

		getByTestId = autocomplete.getByTestId;
	});

	test('Render with "no results found" message', () => {
		const dropDownEmpty = getByTestId('dropDownEmpty');

		expect(dropDownEmpty).toHaveTextContent('no-results-found');
	});
});
