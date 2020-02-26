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

import {fireEvent} from '@testing-library/react';

import ColorPicker from '../../../src/main/resources/META-INF/resources/ColorPicker/ColorPicker.es';

let component;
const name = 'colorPicker';
const spritemap = 'icons.svg';

describe('Field Color Picker', () => {
	beforeEach(() => {
		jest.useFakeTimers();
	});

	afterEach(() => {
		if (component) {
			component.dispose();
		}
	});

	it('renders field disabled', () => {
		component = new ColorPicker({
			name,
			readOnly: false,
			spritemap,
		});

		expect(component).toMatchSnapshot();
	});

	it('renders field with helptext', () => {
		component = new ColorPicker({
			name,
			spritemap,
			tip: 'Helptext',
		});

		expect(component).toMatchSnapshot();
	});

	it('renders field with label', () => {
		component = new ColorPicker({
			label: 'Label',
			name,
			spritemap,
		});

		expect(component).toMatchSnapshot();
	});

	it.skip('emits field edit event on field change', () => {
		const handleFieldEdited = jest.fn();

		const events = {fieldEdited: handleFieldEdited};

		component = new ColorPicker({
			events,
			name,
			spritemap,
		});

		const {fieldBase} = component.refs;

		const inputEl = fieldBase.element.querySelector('input');

		jest.runAllTimers();

		fireEvent.change(inputEl, {target: {value: 'ffffff'}});

		expect(handleFieldEdited).toBeCalledTimes(1);
		expect(handleFieldEdited.mock.calls[0][0][0]).toBe('ffffff');
	});
});
