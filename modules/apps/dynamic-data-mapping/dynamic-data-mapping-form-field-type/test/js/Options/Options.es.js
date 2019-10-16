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

import Options from '../../../src/main/resources/META-INF/resources/Options/Options.es';

const fireEvent = {
	input: (element, config) => {
		element.value = config.target.value;

		var event = new Event('input', {
			...config,
			bubbles: true,
			cancelable: true
		});

		element.dispatchEvent(event);
	}
};

let component;
const spritemap = 'icons.svg';

const optionsValue = {
	en_US: [
		{
			label: 'Option 1',
			value: 'Option1'
		},
		{
			label: 'Option 2',
			value: 'Option2'
		}
	]
};

describe('Options', () => {
	beforeEach(() => jest.useFakeTimers());

	afterEach(() => {
		if (component) {
			component.dispose();
		}
	});

	it('shows the options', () => {
		component = new Options({
			name: 'options',
			spritemap,
			value: optionsValue
		});

		expect(component).toMatchSnapshot();
	});

	it('shows an empty option when value is an array of size 1', () => {
		component = new Options({
			name: 'options',
			spritemap,
			value: {
				[themeDisplay.getLanguageId()]: [
					{
						label: 'Option',
						value: 'Option'
					}
				]
			}
		});

		jest.runAllTimers();

		expect(component.defaultOption).toEqual(true);

		const {element} = component;
		const labelInputs = element.querySelectorAll('.ddm-field-text');

		expect(labelInputs.length).toEqual(2);
		expect(labelInputs[0].value).toEqual('Option');
		expect(labelInputs[1].value).toEqual('');

		const valueInputs = element.querySelectorAll('.key-value-input');

		expect(valueInputs.length).toEqual(2);
		expect(valueInputs[0].value).toEqual('Option');
		expect(valueInputs[1].value).toEqual('');
	});

	it('does not show an empty option when translating', () => {
		component = new Options({
			defaultLanguageId: themeDisplay.getLanguageId(),
			editingLanguageId: 'pt_BR',
			name: 'options',
			spritemap,
			value: {
				[themeDisplay.getLanguageId()]: [
					{
						label: 'Option',
						value: 'Option'
					}
				]
			}
		});

		jest.runAllTimers();

		expect(component.defaultOption).toEqual(true);

		const {element} = component;
		const labelInputs = element.querySelectorAll('.ddm-field-text');

		expect(labelInputs.length).toEqual(1);
	});

	it('edits the value of an option based on the label', () => {
		component = new Options({
			name: 'options',
			spritemap,
			value: {
				[themeDisplay.getLanguageId()]: [
					{
						label: 'Option',
						value: 'Option'
					}
				]
			}
		});

		jest.runAllTimers();

		const {element} = component;
		const labelInputs = element.querySelectorAll('.ddm-field-text');

		fireEvent.input(labelInputs[0], {target: {value: 'Hello'}});

		jest.runAllTimers();

		const valueInputs = element.querySelectorAll('.key-value-input');

		expect(valueInputs[0].value).toEqual('Hello');
	});

	it('inserts a new empty option when editing the last option', () => {
		component = new Options({
			name: 'options',
			spritemap,
			value: {
				[themeDisplay.getLanguageId()]: [
					{
						label: 'Option',
						value: 'Option'
					}
				]
			}
		});

		jest.runAllTimers();

		const {element} = component;
		const labelInputs = element.querySelectorAll('.ddm-field-text');

		fireEvent.input(labelInputs[1], {target: {value: 'Hello'}});

		jest.runAllTimers();

		const valueInputs = element.querySelectorAll('.key-value-input');

		expect(valueInputs.length).toEqual(labelInputs.length + 1);
	});

	it('does not insert a new empty option automatically if translating', () => {
		component = new Options({
			defaultLanguageId: themeDisplay.getLanguageId(),
			editingLanguageId: 'pt_BR',
			name: 'options',
			spritemap,
			value: {
				[themeDisplay.getLanguageId()]: [
					{
						label: 'Option',
						value: 'Option'
					}
				]
			}
		});

		jest.runAllTimers();

		const {element} = component;
		const labelInputs = element.querySelectorAll('.ddm-field-text');

		fireEvent.input(labelInputs[0], {target: {value: 'Hello'}});

		jest.runAllTimers();

		const valueInputs = element.querySelectorAll('.key-value-input');

		expect(valueInputs.length).toEqual(labelInputs.length);
	});
});
