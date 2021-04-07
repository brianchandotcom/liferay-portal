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

import {act, cleanup, render} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import {PageProvider} from 'data-engine-js-components-web';
import React from 'react';

import ColorPicker from '../../../src/main/resources/META-INF/resources/ColorPicker/ColorPicker.es';

const name = 'colorPicker';
const spritemap = 'icons.svg';

const ColorPickerWithProvider = (props) => (
	<PageProvider value={{editingLanguageId: 'en_US'}}>
		<ColorPicker {...props} />
	</PageProvider>
);

describe('Field Color Picker', () => {
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

	it('renders field disabled', () => {
		const {container} = render(
			<ColorPickerWithProvider
				name={name}
				readOnly={true}
				spritemap={spritemap}
			/>
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});

	it('renders field with helptext', () => {
		const {container} = render(
			<ColorPickerWithProvider
				name={name}
				spritemap={spritemap}
				tip="Helptext"
			/>
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});

	it('renders field with label', () => {
		const {container} = render(
			<ColorPickerWithProvider
				label="label"
				name={name}
				spritemap={spritemap}
				tip="Helptext"
			/>
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});

	it('renders with basic color', () => {
		const color = '#FF67AA';

		const {container} = render(
			<ColorPickerWithProvider
				name={name}
				readOnly
				spritemap={spritemap}
				value={color}
			/>
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container.querySelector('input').value).toBe(color);
	});

	it.skip('should call the onChange callback on the field change', () => {
		const handleFieldEdited = jest.fn();

		render(
			<ColorPickerWithProvider
				name={name}
				onChange={handleFieldEdited}
				spritemap={spritemap}
			/>
		);

		userEvent.click(document.body.querySelector('input'), {
			target: {value: 'ffffff'},
		});

		act(() => {
			jest.runAllTimers();
		});

		expect(handleFieldEdited).toHaveBeenCalled();

		const inputEl = document.body.querySelector('input');
		expect(inputEl.value).toBe('ffffff');
	});
});
