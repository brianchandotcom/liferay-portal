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

import Checkbox from '../../../src/main/resources/META-INF/resources/Checkbox/Checkbox.es';

let component;
const spritemap = 'icons.svg';

describe('Field Checkbox', () => {
	beforeEach(() => {
		jest.useFakeTimers();
	});

	afterEach(() => {
		if (component) {
			component.dispose();
		}
	});

	it('is not edidable', () => {
		component = new Checkbox({
			readOnly: false,
			spritemap
		});

		expect(component).toMatchSnapshot();
	});

	it('has a helptext', () => {
		component = new Checkbox({
			spritemap,
			tip: 'Type something'
		});

		expect(component).toMatchSnapshot();
	});

	it('has an id', () => {
		component = new Checkbox({
			id: 'ID',
			spritemap
		});

		expect(component).toMatchSnapshot();
	});

	it('has a label', () => {
		component = new Checkbox({
			label: 'label',
			spritemap
		});

		expect(component).toMatchSnapshot();
	});

	it('has a predefined Value', () => {
		component = new Checkbox({
			placeholder: 'Option 1',
			spritemap
		});

		expect(component).toMatchSnapshot();
	});

	it('is not required', () => {
		component = new Checkbox({
			required: false,
			spritemap
		});

		expect(component).toMatchSnapshot();
	});

	it('is shown as a switcher', () => {
		component = new Checkbox({
			showAsSwitcher: true,
			spritemap
		});

		expect(component).toMatchSnapshot();
	});

	it('is shown as checkbox', () => {
		component = new Checkbox({
			showAsSwitcher: false,
			spritemap
		});

		expect(component).toMatchSnapshot();
	});

	it('renders Label if showLabel is true', () => {
		component = new Checkbox({
			label: 'text',
			showLabel: true,
			spritemap
		});

		expect(component).toMatchSnapshot();
	});

	it('has a spritemap', () => {
		component = new Checkbox({
			spritemap
		});

		expect(component).toMatchSnapshot();
	});

	it('has a value', () => {
		component = new Checkbox({
			spritemap,
			value: true
		});

		expect(component).toMatchSnapshot();
	});

	it('has a key', () => {
		component = new Checkbox({
			key: 'key',
			spritemap
		});

		expect(component).toMatchSnapshot();
	});

	it('emits field edit event on field change', done => {
		const handleFieldEdited = jest.fn();

		const events = {fieldEdited: handleFieldEdited};

		component = new Checkbox({
			events,
			spritemap
		});

		component.on('fieldEdited', () => {
			expect(handleFieldEdited).toHaveBeenCalled();

			done();
		});

		component.handleInputChangeEvent({
			delegateTarget: {
				checked: true
			}
		});

		jest.runAllTimers();
	});

	it('propagates the field edit event on field change', () => {
		component = new Checkbox({
			spritemap
		});

		const spy = jest.spyOn(component, 'emit');

		component.handleInputChangeEvent({
			delegateTarget: {
				checked: true
			}
		});

		expect(spy).toHaveBeenCalled();
		expect(spy).toHaveBeenCalledWith('fieldEdited', expect.any(Object));
	});
});
