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

import dom from 'metal-dom';

import DatePicker from '../../../src/main/resources/META-INF/resources/DatePicker/DatePicker.es';

let component;
const spritemap = 'icons.svg';

const defaultDatePickerConfig = {
	name: 'dateField',
	spritemap
};

describe('DatePicker', () => {
	afterEach(() => {
		component.dispose();
	});

	beforeEach(() => {
		jest.useFakeTimers();
	});

	it('has a helptext', () => {
		component = new DatePicker({
			...defaultDatePickerConfig,
			tip: 'Type something'
		});

		expect(component).toMatchSnapshot();
	});

	it('has an id', () => {
		component = new DatePicker({
			...defaultDatePickerConfig,
			id: 'ID'
		});

		expect(component).toMatchSnapshot();
	});

	it('has a label', () => {
		component = new DatePicker({
			...defaultDatePickerConfig,
			label: 'label'
		});

		expect(component).toMatchSnapshot();
	});

	it('has a predefinedValue', () => {
		component = new DatePicker({
			...defaultDatePickerConfig,
			predefinedValue: '05/05/2019'
		});

		expect(component).toMatchSnapshot();
	});

	it('expands the datepicker when clicking the calendar icon', () => {
		component = new DatePicker({
			...defaultDatePickerConfig
		});

		const spy = jest.spyOn(component, 'emit');

		dom.triggerEvent(
			component.element.querySelector('.input-group-item button'),
			'click'
		);

		const event = {};

		component._handleToggle(event);

		expect(spy).toBeCalled();
	});

	it('fills the input with the current date selected on Date Picker', () => {
		component = new DatePicker({
			...defaultDatePickerConfig
		});

		const spy = jest.spyOn(component, 'emit');

		dom.triggerEvent(
			component.element.querySelector('.input-group-item button'),
			'click'
		);

		jest.runAllTimers();

		dom.triggerEvent(
			component.element.querySelector("[aria-label='live']"),
			'click'
		);

		jest.runAllTimers();

		expect(spy).toHaveBeenCalledWith('fieldEdited', expect.anything());
	});

	it('decreases the current month when the back arrow is selected on Date Picker', () => {
		component = new DatePicker({
			...defaultDatePickerConfig
		});

		dom.triggerEvent(
			component.element.querySelector('.input-group-item button'),
			'click'
		);

		jest.runAllTimers();

		dom.triggerEvent(
			component.element.querySelector("[aria-label='live']"),
			'click'
		);

		const monthBefore = component._month;

		jest.runAllTimers();

		dom.triggerEvent(
			component.element.querySelector("[aria-label='angle-left']"),
			'click'
		);

		jest.runAllTimers();

		if (monthBefore > 0) {
			expect(component._month).toEqual(monthBefore - 1);
		}
		else if (monthBefore == 0) {
			expect(component._month).toEqual(11);
		}
	});

	it('increases the current month when the forward arrow is selected on Date Picker', () => {
		component = new DatePicker({
			...defaultDatePickerConfig
		});

		dom.triggerEvent(
			component.element.querySelector('.input-group-item button'),
			'click'
		);

		jest.runAllTimers();

		dom.triggerEvent(
			component.element.querySelector("[aria-label='live']"),
			'click'
		);

		const monthBefore = component._month;

		jest.runAllTimers();

		dom.triggerEvent(
			component.element.querySelector("[aria-label='angle-right']"),
			'click'
		);

		jest.runAllTimers();

		if (monthBefore < 11) {
			expect(component._month).toEqual(monthBefore + 1);
		}
		else if (monthBefore == 11) {
			expect(component._month).toEqual(0);
		}
	});
});
