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

import Autocomplete from '../../../src/main/resources/META-INF/resources/js/components/autocomplete/Autocomplete.es';

import '@testing-library/jest-dom/extend-expect';

const items = [
	{id: 1, name: '0test test0'},
	{id: 2, name: '1test test1'},
	{id: 3, name: '2test test2'},
];

describe('Autocomplete', () => {
	describe('with items', () => {
		let getByPlaceholderText;
		let getByText;

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

			getByPlaceholderText = autocomplete.getByPlaceholderText;
			getByText = autocomplete.getByText;
		});

		it('shows the dropdown list on focus input', () => {
			const autocompleteInput = getByPlaceholderText(
				'select-or-type-an-option'
			);
			const dropDownList = document.getElementById('dropDownList');
			const dropDown = dropDownList.parentNode;

			expect(dropDown).not.toHaveClass('show');

			fireEvent.focus(autocompleteInput);

			expect(dropDown).toHaveClass('show');

			const dropDownItem1 = getByText('0test test0');

			fireEvent.mouseDown(dropDownItem1);

			expect(autocompleteInput.value).toBe('0test test0');
			expect(dropDown).not.toHaveClass('show');

			fireEvent.focus(autocompleteInput);

			expect(dropDown).toHaveClass('show');

			fireEvent.change(autocompleteInput, {target: {value: 'test'}});
			fireEvent.blur(autocompleteInput);

			expect(autocompleteInput.value).toBe('');
			expect(dropDown).not.toHaveClass('show');
		});

		it('renders its items list and select any option', () => {
			const autocompleteInput = getByPlaceholderText(
				'select-or-type-an-option'
			);

			fireEvent.focus(autocompleteInput);

			const dropDownItem1 = getByText('0test test0');
			const dropDownItem2 = getByText('1test test1');
			const dropDownItem3 = getByText('2test test2');

			expect(dropDownItem1).toBeTruthy();
			expect(dropDownItem2).toBeTruthy();
			expect(dropDownItem3).toBeTruthy();

			expect(dropDownItem1).not.toHaveClass('active');
			expect(dropDownItem2).not.toHaveClass('active');
			expect(dropDownItem3).not.toHaveClass('active');

			fireEvent.mouseOver(dropDownItem3);

			expect(dropDownItem1).not.toHaveClass('active');
			expect(dropDownItem2).not.toHaveClass('active');
			expect(dropDownItem3).toHaveClass('active');

			fireEvent.mouseOver(dropDownItem1);

			expect(dropDownItem1).toHaveClass('active');
			expect(dropDownItem2).not.toHaveClass('active');
			expect(dropDownItem3).not.toHaveClass('active');

			fireEvent.keyDown(autocompleteInput, {keyCode: 40});

			expect(dropDownItem1).not.toHaveClass('active');
			expect(dropDownItem2).toHaveClass('active');
			expect(dropDownItem3).not.toHaveClass('active');

			fireEvent.keyDown(autocompleteInput, {keyCode: 40});

			expect(dropDownItem1).not.toHaveClass('active');
			expect(dropDownItem2).not.toHaveClass('active');
			expect(dropDownItem3).toHaveClass('active');

			fireEvent.keyDown(autocompleteInput, {keyCode: 38});

			expect(dropDownItem1).not.toHaveClass('active');
			expect(dropDownItem2).toHaveClass('active');
			expect(dropDownItem3).not.toHaveClass('active');

			fireEvent.keyDown(autocompleteInput, {keyCode: 13});

			expect(onSelect).toHaveBeenCalledWith(items[1]);

			expect(autocompleteInput.value).toBe('1test test1');
		});

		it('fires onChange handler function on change its text and clear input onBlur without select any option', () => {
			const autocompleteInput = getByPlaceholderText(
				'select-or-type-an-option'
			);

			fireEvent.focus(autocompleteInput);
			fireEvent.change(autocompleteInput, {target: {value: '0te'}});

			expect(onChange).toHaveBeenCalled();

			fireEvent.blur(autocompleteInput);

			expect(autocompleteInput.value).toBe('');
		});
	});

	describe('with children', () => {
		let getByText;

		afterEach(cleanup);

		beforeEach(() => {
			const autocomplete = render(
				<Autocomplete items={items}>
					<span>Mock child</span>
				</Autocomplete>
			);

			getByText = autocomplete.getByText;
		});

		it('renders the children', () => {
			const mockChild = getByText('Mock child');

			expect(mockChild).toBeTruthy();
		});
	});

	describe('with no items', () => {
		let getByText;

		afterEach(cleanup);

		beforeEach(() => {
			const autocomplete = render(<Autocomplete items={[]} />);

			getByText = autocomplete.getByText;
		});

		it('renders with "no results were found" message', () => {
			const dropDownEmpty = getByText('no-results-were-found');

			expect(dropDownEmpty).toBeTruthy();
		});
	});
});
