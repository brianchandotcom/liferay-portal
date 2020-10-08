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

import {AutocompleteMultiSelect} from '../../../../src/main/resources/META-INF/resources/js/shared/components/autocomplete/AutocompleteMultiSelect.es';

import '@testing-library/jest-dom/extend-expect';

const items = [
	{id: 1, name: '1test test1'},
	{id: 2, name: '2test test2'},
];

const ContainerMock = ({children}) => {
	const [selectedItems, onChange] = useState([]);

	return cloneElement(children, {onChange, selectedItems});
};

describe('The AutocompleteMultiSelect component should', () => {
	let container, getByText;

	afterEach(cleanup);

	beforeEach(() => {
		const autocomplete = render(<AutocompleteMultiSelect items={items} />, {
			wrapper: ContainerMock,
		});

		container = autocomplete.container;
		getByText = autocomplete.getByText;
	});

	test('Show the dropdown list on focus input', () => {
		const multiSelectInput = container.querySelector(
			'input.form-control-inset'
		);
		const dropDownList = document.querySelector('#dropDownList');
		const dropDownListItems = document.querySelectorAll('.dropdown-item');

		const dropDown = dropDownList.parentNode;

		expect(dropDown).not.toHaveClass('show');

		fireEvent.focus(multiSelectInput);

		expect(dropDown).toHaveClass('show');
		expect(dropDownListItems[0]).toHaveTextContent('1test test1');
		expect(dropDownListItems[1]).toHaveTextContent('2test test2');

		fireEvent.mouseDown(dropDownListItems[0]);

		expect(dropDownListItems[0]).toHaveTextContent('2test test2');

		fireEvent.mouseDown(dropDownListItems[0]);

		const multiSelectItems = container.querySelectorAll(
			'.label-dismissible'
		);

		expect(multiSelectItems[0]).toHaveTextContent('1test test1');
		expect(multiSelectItems[1]).toHaveTextContent('2test test2');

		const dropDownEmpty = getByText('no-results-were-found');

		expect(dropDownEmpty).toBeTruthy();

		const multiSelectItemsRemove = container.querySelectorAll(
			'button.close'
		);

		fireEvent.click(multiSelectItemsRemove[1]);

		expect(dropDownListItems[0]).toHaveTextContent('2test test2');
	});

	test('Render its items list and select any option', () => {
		const multiSelectInput = container.querySelector(
			'input.form-control-inset'
		);
		const dropDownListItems = document.querySelectorAll('.dropdown-item');

		fireEvent.focus(multiSelectInput);

		expect(dropDownListItems[0]).toHaveTextContent('1test test1');
		expect(dropDownListItems[1]).toHaveTextContent('2test test2');
		expect(dropDownListItems[0]).not.toHaveClass('active');
		expect(dropDownListItems[1]).not.toHaveClass('active');

		fireEvent.mouseOver(dropDownListItems[1]);

		expect(dropDownListItems[0]).not.toHaveClass('active');
		expect(dropDownListItems[1]).toHaveClass('active');

		fireEvent.mouseOver(dropDownListItems[0]);

		expect(dropDownListItems[0]).toHaveClass('active');
		expect(dropDownListItems[1]).not.toHaveClass('active');

		fireEvent.keyDown(multiSelectInput, {keyCode: 40});

		expect(dropDownListItems[0]).not.toHaveClass('active');
		expect(dropDownListItems[1]).toHaveClass('active');

		fireEvent.keyDown(multiSelectInput, {keyCode: 38});

		expect(dropDownListItems[0]).toHaveClass('active');
		expect(dropDownListItems[1]).not.toHaveClass('active');

		fireEvent.keyDown(multiSelectInput, {keyCode: 40});

		expect(dropDownListItems[0]).not.toHaveClass('active');
		expect(dropDownListItems[1]).toHaveClass('active');

		fireEvent.keyDown(multiSelectInput, {keyCode: 13});

		const multiSelectItems = container.querySelectorAll(
			'.label-dismissible'
		);

		expect(multiSelectItems[0]).toHaveTextContent('2test test2');
	});
});
