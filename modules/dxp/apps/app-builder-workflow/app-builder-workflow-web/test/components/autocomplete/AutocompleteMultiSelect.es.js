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
import React, {cloneElement, useState} from 'react';

import AutocompleteMultiSelect from '../../../src/main/resources/META-INF/resources/js/components/autocomplete/AutocompleteMultiSelect.es';

import '@testing-library/jest-dom/extend-expect';

const items = [
	{id: 1, name: '1test test1'},
	{id: 2, name: '2test test2'},
];

const ContainerMock = ({children}) => {
	const [selectedItems, onChange] = useState([]);

	return cloneElement(children, {onChange, selectedItems});
};

describe('AutocompleteMultiSelect', () => {
	let getByPlaceholderText;
	let getByText;

	afterEach(cleanup);

	beforeEach(() => {
		const autocomplete = render(
			<AutocompleteMultiSelect id="Test" items={items} />,
			{wrapper: ContainerMock}
		);

		getByPlaceholderText = autocomplete.getByPlaceholderText;
		getByText = autocomplete.getByText;
	});

	it('shows the dropdown list on focus input', () => {
		const multiSelectInput = getByPlaceholderText(
			'select-or-type-an-option'
		);
		const dropDownList = document.getElementById('dropDownListTest');
		const dropDown = dropDownList.parentNode;

		expect(dropDown).not.toHaveClass('show');

		fireEvent.focus(multiSelectInput);

		expect(dropDown).toHaveClass('show');

		const dropDownItem1 = getByText('1test test1');
		const dropDownItem2 = getByText('2test test2');

		expect(dropDownItem1).toBeTruthy();
		expect(dropDownItem2).toBeTruthy();

		fireEvent.mouseDown(dropDownItem1);

		expect(dropDownItem1).toHaveTextContent('2test test2');

		fireEvent.mouseDown(dropDownItem1);

		const multiSelectItem1 = getByText('1test test1');
		const multiSelectItem2 = getByText('2test test2');

		expect(multiSelectItem1).toBeTruthy();
		expect(multiSelectItem2).toBeTruthy();

		expect(getByText('no-results-were-found')).toBeTruthy();

		const multiSelectItemsRemove = document.querySelectorAll(
			'button.close'
		);

		fireEvent.click(multiSelectItemsRemove[1]);

		expect(dropDownItem1).toHaveTextContent('2test test2');
	});

	it('renders its items list and select any option', () => {
		const multiSelectInput = getByPlaceholderText(
			'select-or-type-an-option'
		);

		fireEvent.focus(multiSelectInput);

		const dropDownItem1 = getByText('1test test1');
		const dropDownItem2 = getByText('2test test2');

		expect(dropDownItem1).toBeTruthy();
		expect(dropDownItem2).toBeTruthy();

		expect(dropDownItem1).not.toHaveClass('active');
		expect(dropDownItem2).not.toHaveClass('active');

		fireEvent.mouseOver(dropDownItem2);

		expect(dropDownItem1).not.toHaveClass('active');
		expect(dropDownItem2).toHaveClass('active');

		fireEvent.mouseOver(dropDownItem1);

		expect(dropDownItem1).toHaveClass('active');
		expect(dropDownItem2).not.toHaveClass('active');

		fireEvent.keyDown(multiSelectInput, {keyCode: 40});

		expect(dropDownItem1).not.toHaveClass('active');
		expect(dropDownItem2).toHaveClass('active');

		fireEvent.keyDown(multiSelectInput, {keyCode: 38});

		expect(dropDownItem1).toHaveClass('active');
		expect(dropDownItem2).not.toHaveClass('active');

		fireEvent.keyDown(multiSelectInput, {keyCode: 40});

		expect(dropDownItem1).not.toHaveClass('active');
		expect(dropDownItem2).toHaveClass('active');

		fireEvent.keyDown(multiSelectInput, {keyCode: 13});

		const multiSelectItems = document.querySelectorAll(
			'span.label-dismissible'
		);

		expect(multiSelectItems[0]).toHaveTextContent('2test test2');
	});
});
