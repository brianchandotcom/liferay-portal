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
			spritemap,
			value: optionsValue
		});

		expect(component).toMatchSnapshot();
	});

	it('deletes an option when delete button is clicked', () => {
		component = new Options({
			spritemap,
			value: optionsValue
		});

		const before = component.items.length;

		component.deleteOption(0);

		expect(component.items.length).toEqual(before - 1);
	});

	it('.normalizeValue() does not allow options with the same value', () => {
		component = new Options({
			spritemap
		});

		const value = {
			en_US: [
				{
					label: 'One',
					value: 'One'
				},
				{
					label: 'One',
					value: 'One'
				}
			]
		};

		expect(component.normalizeValue(value, true)).toEqual({
			en_US: [
				{
					label: 'One',
					value: 'One'
				},
				{
					label: 'One',
					value: 'One1'
				}
			]
		});
	});

	it('allows the user to order the fieldName options by dragging and dropping the options', () => {
		component = new Options({
			spritemap,
			value: optionsValue
		});

		const spy = jest.spyOn(component, 'emit');

		jest.runAllTimers();

		component.moveOption(1, 0);

		jest.runAllTimers();

		expect(component).toMatchSnapshot();

		expect(spy).toHaveBeenCalledWith('fieldEdited', expect.anything());
	});
});
