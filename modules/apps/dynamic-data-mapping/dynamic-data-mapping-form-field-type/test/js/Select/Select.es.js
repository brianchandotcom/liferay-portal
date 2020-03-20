/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {
	act,
	fireEvent,
	getByTestId,
	waitForElement,
} from '@testing-library/react';
import ReactDOM from 'react';

import Select from '../../../src/main/resources/META-INF/resources/Select/Select.es';
import withContextMock from '../__mocks__/withContextMock.es';

let component;
const spritemap = 'icons.svg';

const SelectWithContextMock = withContextMock(Select);

describe('Select', () => {
	beforeAll(() => {
		ReactDOM.createPortal = jest.fn(element => {
			return element;
		});
		jest.useFakeTimers();
	});

	afterEach(() => {
		if (component) {
			component.dispose();
		}
	});

	it('is not editable', () => {
		component = new Select({
			readOnly: false,
			spritemap,
		});

		expect(component).toMatchSnapshot();
	});

	it('has a helptext', () => {
		component = new Select({
			spritemap,
			tip: 'Type something',
		});

		expect(component).toMatchSnapshot();
	});

	it('has an id', () => {
		component = new Select({
			id: 'ID',
			spritemap,
		});

		expect(component).toMatchSnapshot();
	});

	it('renders options', () => {
		component = new Select({
			options: [
				{
					checked: false,
					disabled: false,
					id: 'id',
					inline: false,
					label: 'label',
					name: 'name',
					showLabel: true,
					value: 'item',
				},
			],
			spritemap,
		});

		expect(component).toMatchSnapshot();
	});

	it('renders no options when options come empty', () => {
		component = new Select({
			options: [],
			spritemap,
		});

		expect(component).toMatchSnapshot();
	});

	it('has a label', () => {
		component = new Select({
			label: 'label',
			spritemap,
		});

		expect(component).toMatchSnapshot();
	});

	it('is closed by default', () => {
		component = new Select({
			open: false,
			spritemap,
		});

		expect(component).toMatchSnapshot();
	});

	it("has class dropdown-opened when it's opened", () => {
		component = new Select({
			open: true,
			spritemap,
		});

		expect(component).toMatchSnapshot();
	});

	it('has a placeholder', () => {
		component = new Select({
			placeholder: 'Placeholder',
			spritemap,
		});

		expect(component).toMatchSnapshot();
	});

	it('has a predefinedValue', () => {
		component = new Select({
			predefinedValue: ['Select'],
			spritemap,
		});

		expect(component).toMatchSnapshot();
	});

	it('is not required', () => {
		component = new Select({
			required: false,
			spritemap,
		});

		expect(component).toMatchSnapshot();
	});

	it('puts an asterisk when field is required', () => {
		component = new Select({
			label: 'This is the label',
			required: true,
			spritemap,
		});

		expect(component).toMatchSnapshot();
	});

	it('renders Label if showLabel is true', () => {
		component = new Select({
			label: 'text',
			showLabel: true,
			spritemap,
		});

		expect(component).toMatchSnapshot();
	});

	it('has a spritemap', () => {
		component = new Select({
			spritemap,
		});

		expect(component).toMatchSnapshot();
	});

	it('has a value', () => {
		component = new Select({
			spritemap,
			value: ['value'],
		});

		expect(component).toMatchSnapshot();
	});

	it('has a key', () => {
		component = new Select({
			key: 'key',
			spritemap,
		});

		expect(component).toMatchSnapshot();
	});

	it('emits a field edit event when an item is selected', async () => {
		const handleFieldEdited = data => {
			expect(data).toEqual(
				expect.objectContaining({
					fieldInstance: expect.any(Object),
					originalEvent: expect.any(Object),
					value: [''],
				})
			);
		};

		const events = {fieldEdited: handleFieldEdited};

		component = new SelectWithContextMock({
			dataSourceType: 'manual',
			events,
			options: [
				{
					label: 'label',
					value: 'item',
				},
				{
					label: 'label2',
					value: 'item2',
				},
			],
			spritemap,
		});

		act(() => {
			jest.runAllTimers();
		});

		const dropdownTrigger = component.element.querySelector(
			'.form-builder-select-field.input-group-container'
		);

		fireEvent.click(dropdownTrigger);

		// Waits for the dropdown being opened.
		jest.runAllTimers();

		const dropdownItem = await waitForElement(() =>
			getByTestId(document.body, 'dropdownItem-0')
		);

		fireEvent.click(dropdownItem);
	});

	it('renders the dropdown with search when there are more than six options', () => {
		component = new Select({
			dataSourceType: 'manual',
			options: [
				{
					label: 'label',
					name: 'name',
					value: 'item',
				},
				{
					label: 'label',
					name: 'name',
					value: 'item',
				},
				{
					label: 'label',
					name: 'name',
					value: 'item',
				},
				{
					label: 'label',
					name: 'name',
					value: 'item',
				},
				{
					label: 'label',
					name: 'name',
					value: 'item',
				},
				{
					label: 'label',
					name: 'name',
					value: 'item',
				},
				{
					label: 'label',
					name: 'name',
					value: 'item',
				},
			],
			spritemap,
		});

		expect(component).toMatchSnapshot();
	});
});
