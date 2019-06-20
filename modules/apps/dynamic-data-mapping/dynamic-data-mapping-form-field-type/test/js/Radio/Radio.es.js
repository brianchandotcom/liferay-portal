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

import Radio from 'source/Radio/Radio.es';

let component;
const spritemap = 'icons.svg';

const defaultRadioConfig = {
	name: 'radioField',
	spritemap
};

describe('Field Radio', () => {
	afterEach(() => {
		if (component) {
			component.dispose();
		}
	});

	it('should be not edidable', () => {
		component = new Radio({
			...defaultRadioConfig,
			readOnly: false
		});

		expect(component).toMatchSnapshot();
	});

	it('should have a helptext', () => {
		component = new Radio({
			...defaultRadioConfig,
			tip: 'Type something'
		});

		expect(component).toMatchSnapshot();
	});

	it('should render options', () => {
		component = new Radio({
			...defaultRadioConfig,
			options: [
				{
					checked: false,
					disabled: false,
					id: 'id',
					inline: false,
					label: 'label',
					name: 'name',
					showLabel: true,
					value: 'item'
				},
				{
					checked: false,
					disabled: false,
					id: 'id',
					inline: false,
					label: 'label2',
					name: 'name',
					showLabel: true,
					value: 'item'
				}
			]
		});

		expect(component).toMatchSnapshot();
	});

	it('should render no options when options is empty', () => {
		component = new Radio({
			...defaultRadioConfig,
			options: []
		});

		expect(component).toMatchSnapshot();
	});

	it('should have an id', () => {
		component = new Radio({
			...defaultRadioConfig,
			id: 'ID'
		});

		expect(component).toMatchSnapshot();
	});

	it('should have a label', () => {
		component = new Radio({
			...defaultRadioConfig,
			label: 'label'
		});

		expect(component).toMatchSnapshot();
	});

	it('should have a predefined Value', () => {
		component = new Radio({
			...defaultRadioConfig,
			placeholder: 'Option 1'
		});

		expect(component).toMatchSnapshot();
	});

	it('should not be required', () => {
		component = new Radio({
			...defaultRadioConfig,
			required: false
		});

		expect(component).toMatchSnapshot();
	});

	it('should render Label if showLabel is true', () => {
		component = new Radio({
			...defaultRadioConfig,
			label: 'text',
			showLabel: true
		});

		expect(component).toMatchSnapshot();
	});

	it('should have a spritemap', () => {
		component = new Radio(defaultRadioConfig);

		expect(component).toMatchSnapshot();
	});

	it('should have a value', () => {
		component = new Radio({
			...defaultRadioConfig,
			value: 'value'
		});

		expect(component).toMatchSnapshot();
	});
});
