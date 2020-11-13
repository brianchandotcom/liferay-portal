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

import Text from '../../../src/main/resources/META-INF/resources/Text/Text.es';

const spritemap = 'icons.svg';

const defaultTextConfig = {
	name: 'textField',
	spritemap,
};

const TextWithProvider = (props) => (
	<PageProvider value={{editingLanguageId: 'en_US'}}>
		<Text {...props} />
	</PageProvider>
);

describe('Field Text', () => {
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

	it('is not readOnly', () => {
		const {container} = render(
			<TextWithProvider {...defaultTextConfig} readOnly={false} />
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});

	it('is readOnly', () => {
		const {container} = render(
			<TextWithProvider {...defaultTextConfig} readOnly={true} />
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});

	it('has a helptext', () => {
		const {container} = render(
			<TextWithProvider {...defaultTextConfig} tip="Type something" />
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});

	it('has an id', () => {
		const {container} = render(
			<TextWithProvider {...defaultTextConfig} id="Id" />
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});

	it('has a label', () => {
		const {container} = render(
			<TextWithProvider {...defaultTextConfig} label="label" />
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});

	it('has a placeholder', () => {
		const {container} = render(
			<TextWithProvider
				{...defaultTextConfig}
				placeholder="Placeholder"
			/>
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});

	it('hides autocomplete dropdown menu when container layout is hidden', () => {
		const props = {
			autocomplete: true,
			options: [
				{label: 'Option 1', value: 'Option1'},
				{label: 'Option 2', value: 'Option2'},
			],
			value: 'Option',
			...defaultTextConfig,
		};

		render(
			<div className="ddm-page-container-layout hide">
				<TextWithProvider {...props} />
			</div>
		);

		act(() => {
			jest.runAllTimers();
		});

		const autocompleteDropdownMenu = document.body.querySelector(
			'.autocomplete-dropdown-menu'
		);

		const classList = autocompleteDropdownMenu.classList;

		expect(classList.contains('show')).toBeFalsy();
	});

	it('is not required', () => {
		const {container} = render(
			<TextWithProvider {...defaultTextConfig} required={false} />
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});

	it('renders autocomplete dropdown menu', () => {
		const props = {
			autocomplete: true,
			options: [
				{label: 'Option 1', value: 'Option1'},
				{label: 'Option 2', value: 'Option2'},
			],
			value: 'Option',
			...defaultTextConfig,
		};

		render(
			<div className="ddm-page-container-layout">
				<TextWithProvider {...props} />
			</div>
		);

		act(() => {
			jest.runAllTimers();
		});

		const autocompleteDropdownMenu = document.body.querySelector(
			'.autocomplete-dropdown-menu'
		);

		const classList = autocompleteDropdownMenu.classList;

		expect(classList.contains('show')).toBeTruthy();
	});

	it('renders Label if showLabel is true', () => {
		const {container} = render(
			<TextWithProvider {...defaultTextConfig} label="text" showLabel />
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});

	it('has a value', () => {
		const {container} = render(
			<TextWithProvider {...defaultTextConfig} value="value" />
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});

	it('emits a field edit with correct parameters', () => {
		const onChange = jest.fn();

		const {container} = render(
			<TextWithProvider
				{...defaultTextConfig}
				key="input"
				onChange={onChange}
			/>
		);

		const input = container.querySelector('input');

		fireEvent.change(input, {
			target: {
				value: 'test',
			},
		});

		act(() => {
			jest.runAllTimers();
		});

		expect(onChange).toHaveBeenCalled();
	});

	it('normalizes the field reference if it contains invalid characters', () => {
		const onChange = jest.fn();

		const {container} = render(
			<TextWithProvider
				{...defaultTextConfig}
				fieldName="fieldReference"
				key="input"
				onChange={onChange}
			/>
		);

		const input = container.querySelector('input');

		fireEvent.change(input, {
			target: {
				value: 'Field¿êReference',
			},
		});

		act(() => {
			jest.runAllTimers();
		});

		expect(input.value).toEqual('FieldReference');
	});
});
