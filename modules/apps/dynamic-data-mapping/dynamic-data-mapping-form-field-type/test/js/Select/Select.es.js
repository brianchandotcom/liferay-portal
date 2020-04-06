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

import '@testing-library/jest-dom/extend-expect';
import {fireEvent, getByTestId, waitForElement} from '@testing-library/react';
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
	});

	afterEach(() => {
		if (component) {
			component.dispose();
		}
	});

	it('is not editable', () => {
		component = new SelectWithContextMock({
			readOnly: true,
			spritemap,
		});

		const dropdownTrigger = document.body.querySelector(
			'.select-field-trigger'
		);

		expect(dropdownTrigger).toHaveClass('disabled');
	});

	it('has a help text', () => {
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

		const dropdownTrigger = component.element.querySelector(
			'.form-builder-select-field.input-group-container'
		);

		fireEvent.click(dropdownTrigger);

		const dropdownItem = await waitForElement(() =>
			getByTestId(document.body, 'dropdownItem-0')
		);

		fireEvent.click(dropdownItem);
	});

	it('emits a field edit event when an item is selected using multiselect', async () => {
		const handleFieldEdited = data => {
			expect(data).toEqual(
				expect.objectContaining({
					fieldInstance: expect.any(Object),
					originalEvent: expect.any(Object),
					value: ['item7'],
				})
			);
		};

		const events = {fieldEdited: handleFieldEdited};

		component = new SelectWithContextMock({
			dataSourceType: 'manual',
			events,
			multiple: true,
			options: [
				{
					label: 'label1',
					name: 'name1',
					value: 'item1',
				},
				{
					label: 'label2',
					name: 'name2',
					value: 'item2',
				},
				{
					label: 'label3',
					name: 'name3',
					value: 'item3',
				},
				{
					label: 'label4',
					name: 'name4',
					value: 'item4',
				},
				{
					label: 'label5',
					name: 'name5',
					value: 'item5',
				},
				{
					label: 'label6',
					name: 'name6',
					value: 'item6',
				},
				{
					label: 'label7',
					name: 'name7',
					value: 'item7',
				},
			],
			spritemap,
		});

		const dropdownTrigger = component.element.querySelector(
			'.form-builder-select-field.input-group-container'
		);

		fireEvent.click(dropdownTrigger);

		const labelItem = await waitForElement(() =>
			getByTestId(document.body, 'labelItem-item7')
		);

		fireEvent.click(labelItem);

		expect(component).toMatchSnapshot();
	});

	it('emits a field edit event when an item is selected using multiselect', async done => {
		const handleFieldEdited = data => {
			expect(data).toEqual(
				expect.objectContaining({
					fieldInstance: expect.any(Object),
					originalEvent: expect.any(Object),
					value: ['item7', 'item2'],
				})
			);

			done();
		};

		const events = {fieldEdited: handleFieldEdited};

		component = new SelectWithContextMock({
			dataSourceType: 'manual',
			events,
			multiple: true,
			options: [
				{
					label: 'label1',
					name: 'name1',
					value: 'item1',
				},
				{
					label: 'label2',
					name: 'name2',
					value: 'item2',
				},
				{
					label: 'label3',
					name: 'name3',
					value: 'item3',
				},
				{
					label: 'label4',
					name: 'name4',
					value: 'item4',
				},
				{
					label: 'label5',
					name: 'name5',
					value: 'item5',
				},
				{
					label: 'label6',
					name: 'name6',
					value: 'item6',
				},
				{
					label: 'label7',
					name: 'name7',
					value: 'item7',
				},
			],
			spritemap,
			value: ['item7', 'item3', 'item2'],
		});

		const dropdownTrigger = component.element.querySelector(
			'.form-builder-select-field.input-group-container'
		);

		fireEvent.click(dropdownTrigger);

		const labelItemCloseButton = await waitForElement(() => {
			return getByTestId(dropdownTrigger, 'closeButtonitem3');
		});

		fireEvent.click(labelItemCloseButton);
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
