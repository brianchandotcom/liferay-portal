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

import Numeric from '../../../src/main/resources/META-INF/resources/Numeric/Numeric.es';
import withContextMock from '../__mocks__/withContextMock.es';

let component;
const spritemap = 'icons.svg';

const defaultNumericConfig = {
	name: 'numericField',
	spritemap,
};

const NumericWithContextMock = withContextMock(Numeric);

describe('Field Numeric', () => {
	beforeEach(() => {
		jest.useFakeTimers();
	});

	afterEach(() => {
		if (component) {
			component.dispose();
		}
	});

	it('renders the default markup', () => {
		component = new NumericWithContextMock({
			...defaultNumericConfig,
			readOnly: false,
		});

		expect(component).toMatchSnapshot();
	});

	it('is not readOnly', () => {
		component = new NumericWithContextMock({
			...defaultNumericConfig,
			readOnly: false,
		});

		expect(component).toMatchSnapshot();
	});

	it('has a helptext', () => {
		component = new NumericWithContextMock({
			...defaultNumericConfig,
			tip: 'Type something',
		});

		expect(component).toMatchSnapshot();
	});

	it('has an id', () => {
		component = new NumericWithContextMock({
			...defaultNumericConfig,
			id: 'ID',
		});

		expect(component).toMatchSnapshot();
	});

	it('has a label', () => {
		component = new NumericWithContextMock({
			...defaultNumericConfig,
			label: 'label',
		});

		expect(component).toMatchSnapshot();
	});

	it('has a placeholder', () => {
		component = new NumericWithContextMock({
			...defaultNumericConfig,
			placeholder: 'Placeholder',
		});

		expect(component).toMatchSnapshot();
	});

	it('is not required', () => {
		component = new NumericWithContextMock({
			...defaultNumericConfig,
			required: false,
		});

		expect(component).toMatchSnapshot();
	});

	it('renders Label if showLabel is true', () => {
		component = new NumericWithContextMock({
			...defaultNumericConfig,
			label: 'Numeric Field',
			showLabel: true,
		});

		expect(component).toMatchSnapshot();
	});

	it('has a spritemap', () => {
		component = new NumericWithContextMock(defaultNumericConfig);

		expect(component).toMatchSnapshot();
	});

	it('has a value', () => {
		component = new NumericWithContextMock({
			...defaultNumericConfig,
			value: '123',
		});

		expect(component).toMatchSnapshot();
	});

	it('has a key', () => {
		component = new NumericWithContextMock({
			...defaultNumericConfig,
			key: 'key',
		});

		expect(component).toMatchSnapshot();
	});

	it('propagates the field edit event', () => {
		jest.useFakeTimers();

		const fn = jest.fn();

		component = new NumericWithContextMock({
			...defaultNumericConfig,
			events: {fieldEdited: fn},
			key: 'input',
		});

		const input = component.element.querySelector('input');

		fireEvent.change(input, {target: {value: '2'}});

		jest.runAllTimers();

		expect(fn).toHaveBeenCalled();
	});

	it('changes the mask type', () => {
		component = new NumericWithContextMock({
			...defaultNumericConfig,
		});

		jest.runAllTimers();

		component.props.dataType = 'double';

		jest.runAllTimers();

		expect(component.props.dataType).toBe('double');
	});

	/**
	 * This test needs to be revised, it is strange to wait for the
	 * decimal value when the dataType is an integer.
	 */
	it.skip('check if event is sent when decimal is being writen', done => {
		const handleFieldEdited = data => {
			expect(data).toEqual(
				expect.objectContaining({
					fieldInstance: component,
					originalEvent: expect.any(Object),
					value: '3.0',
				})
			);
			done();
		};

		const events = {fieldEdited: handleFieldEdited};

		component = new NumericWithContextMock({
			...defaultNumericConfig,
			events,
			key: 'input',
		});

		const input = component.element.querySelector('input');

		fireEvent.change(input, {
			target: {
				value: '3.0',
			},
		});

		jest.runAllTimers();
	});

	it('check field value is rounded when fieldType is integer but it receives a double', done => {
		const handleFieldEdited = event => {
			expect(event.value).toBe('4');
			done();
		};

		const events = {fieldEdited: handleFieldEdited};

		component = new NumericWithContextMock({
			...defaultNumericConfig,
			events,
			key: 'input',
			value: '3.8',
		});

		jest.runAllTimers();
	});
});
