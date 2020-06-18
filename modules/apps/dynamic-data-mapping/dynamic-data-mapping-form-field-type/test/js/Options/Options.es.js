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

import {act, cleanup, fireEvent, render} from '@testing-library/react';
import {PageProvider} from 'dynamic-data-mapping-form-renderer';
import React from 'react';

import Options from '../../../src/main/resources/META-INF/resources/Options/Options.es';

const spritemap = 'icons.svg';

const OptionsWithProvider = (props) => (
	<PageProvider value={{editingLanguageId: themeDisplay.getLanguageId()}}>
		<Options {...props} />
	</PageProvider>
);

const optionsValue = {
	[themeDisplay.getLanguageId()]: [
		{
			label: 'Option 1',
			value: 'Option1',
		},
		{
			label: 'Option 2',
			value: 'Option2',
		},
	],
};

describe('Options', () => {
	// eslint-disable-next-line no-console
	const originalWarn = console.warn;

	beforeAll(() => {
		// eslint-disable-next-line no-console
		console.warn = (...args) => {
			if (/DataProvider: Trying/.test(args[0])) {
				return;
			}
			originalWarn.call(console, ...args);
		};
	});

	afterAll(() => {
		// eslint-disable-next-line no-console
		console.warn = originalWarn;
	});

	afterEach(cleanup);

	beforeEach(() => {
		jest.useFakeTimers();
		fetch.mockResponseOnce(JSON.stringify({}));
	});

	it('shows the options', () => {
		const {container} = render(
			<OptionsWithProvider
				name="options"
				spritemap={spritemap}
				value={optionsValue}
			/>
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});

	it('shows an empty option when value is an array of size 1', () => {
		const {container} = render(
			<OptionsWithProvider
				name="options"
				onChange={jest.fn()}
				spritemap={spritemap}
				value={{
					[themeDisplay.getLanguageId()]: [
						{
							label: 'Option',
							value: 'Option',
						},
					],
				}}
			/>
		);

		act(() => {
			jest.runAllTimers();
		});

		const labelInputs = container.querySelectorAll('.ddm-field-text');

		expect(labelInputs.length).toEqual(2);
		expect(labelInputs[0].value).toEqual('Option');
		expect(labelInputs[1].value).toEqual('');

		const valueInputs = container.querySelectorAll('.key-value-input');

		expect(valueInputs.length).toEqual(2);
		expect(valueInputs[0].value).toEqual('Option');
		expect(valueInputs[1].value).toEqual('');
	});

	it('does show an empty option when translating', () => {
		const {container} = render(
			<OptionsWithProvider
				defaultLanguageId={themeDisplay.getLanguageId()}
				editingLanguageId="pt_BR"
				name="options"
				onChange={jest.fn()}
				spritemap={spritemap}
				value={{
					[themeDisplay.getLanguageId()]: [
						{
							label: 'Option',
							value: 'Option',
						},
					],
					pt_BR: [
						{
							label: 'Option',
							value: 'Option',
						},
					],
				}}
			/>
		);

		act(() => {
			jest.runAllTimers();
		});

		const labelInputs = container.querySelectorAll('.ddm-field-text');

		expect(labelInputs.length).toEqual(2);
	});

	it('edits the value of an option based on the label', () => {
		const {container} = render(
			<OptionsWithProvider
				name="options"
				onChange={jest.fn()}
				spritemap={spritemap}
				value={{
					[themeDisplay.getLanguageId()]: [
						{
							label: 'Option',
							value: 'Option',
						},
					],
				}}
			/>
		);

		act(() => {
			jest.runAllTimers();
		});

		const labelInputs = container.querySelectorAll('.ddm-field-text');

		fireEvent.change(labelInputs[0], {
			target: {
				value: 'Hello',
			},
		});

		act(() => {
			jest.runAllTimers();
		});

		const valueInputs = container.querySelectorAll('.key-value-input');

		expect(valueInputs[0].value).toEqual('Hello');
	});

	it('inserts a new empty option when editing the last option', () => {
		const {container} = render(
			<OptionsWithProvider
				name="options"
				onChange={jest.fn()}
				spritemap={spritemap}
				value={{
					[themeDisplay.getLanguageId()]: [
						{
							label: 'Option',
							value: 'Option',
						},
					],
				}}
			/>
		);

		act(() => {
			jest.runAllTimers();
		});

		const labelInputs = container.querySelectorAll('.ddm-field-text');

		fireEvent.change(labelInputs[1], {
			target: {
				value: 'Hello',
			},
		});

		act(() => {
			jest.runAllTimers();
		});

		const valueInputs = container.querySelectorAll('.key-value-input');

		expect(valueInputs.length).toEqual(labelInputs.length + 1);
	});

	it('does not insert a new empty option automatically if translating', () => {
		const {container} = render(
			<OptionsWithProvider
				defaultLanguageId={themeDisplay.getLanguageId()}
				editingLanguageId="pt_BR"
				name="options"
				onChange={jest.fn()}
				spritemap={spritemap}
				value={{
					[themeDisplay.getLanguageId()]: [
						{
							label: 'Option',
							value: 'Option',
						},
					],
					pt_BR: [
						{
							label: 'Option',
							value: 'Option',
						},
					],
				}}
			/>
		);

		act(() => {
			jest.runAllTimers();
		});

		const labelInputs = container.querySelectorAll('.ddm-field-text');

		fireEvent.input(labelInputs[0], {target: {value: 'Hello'}});

		act(() => {
			jest.runAllTimers();
		});

		const valueInputs = container.querySelectorAll('.key-value-input');

		expect(valueInputs.length).toEqual(labelInputs.length);
	});

	it('deduplication of value when adding a new option', () => {
		const {container} = render(
			<OptionsWithProvider
				name="options"
				onChange={jest.fn()}
				spritemap={spritemap}
				value={{
					[themeDisplay.getLanguageId()]: [
						{
							label: 'Foo',
							value: 'Foo',
						},
					],
				}}
			/>
		);

		act(() => {
			jest.runAllTimers();
		});

		const labelInputs = container.querySelectorAll('.ddm-field-text');

		fireEvent.input(labelInputs[1], {target: {value: 'Foo'}});

		act(() => {
			jest.runAllTimers();
		});

		const valueInputs = container.querySelectorAll('.key-value-input');

		expect(valueInputs[1].value).toEqual('Foo1');
	});

	it('deduplication of the value when editing the value', () => {
		const {container} = render(
			<OptionsWithProvider
				name="options"
				onChange={jest.fn()}
				spritemap={spritemap}
				value={{
					[themeDisplay.getLanguageId()]: [
						{
							label: 'Bar',
							value: 'Bar',
						},
						{
							label: 'Foo',
							value: 'Foo',
						},
					],
				}}
			/>
		);

		act(() => {
			jest.runAllTimers();
		});

		const labelInputs = container.querySelectorAll('.ddm-field-text');

		fireEvent.input(labelInputs[1], {target: {value: 'Bar'}});

		act(() => {
			jest.runAllTimers();
		});

		const valueInputs = container.querySelectorAll('.key-value-input');

		expect(valueInputs[1].value).toEqual('Bar1');
	});

	it('adds a value to the value property when the label is empty', () => {
		const {container} = render(
			<OptionsWithProvider
				name="options"
				onChange={jest.fn()}
				spritemap={spritemap}
				value={{
					[themeDisplay.getLanguageId()]: [
						{
							label: 'Bar',
							value: 'Bar',
						},
					],
				}}
			/>
		);

		act(() => {
			jest.runAllTimers();
		});

		const labelInput = container.querySelector('.ddm-field-text');

		fireEvent.input(labelInput, {target: {value: ''}});

		act(() => {
			jest.runAllTimers();
		});

		const valueInput = container.querySelector('.key-value-input');

		expect(valueInput.value).toBe('option');
	});
});
