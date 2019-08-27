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

import Radio from '../../../src/main/resources/META-INF/resources/Radio/Radio.es';

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

	it('is not edidable', () => {
		component = new Radio({
			...defaultRadioConfig,
			readOnly: false
		});

		expect(component).toMatchSnapshot();
	});

	it('has a helptext', () => {
		component = new Radio({
			...defaultRadioConfig,
			tip: 'Type something'
		});

		expect(component).toMatchSnapshot();
	});

	it('renders options', () => {
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

	it('renders no options when options is empty', () => {
		component = new Radio({
			...defaultRadioConfig,
			options: []
		});

		expect(component).toMatchSnapshot();
	});

	it('has an id', () => {
		component = new Radio({
			...defaultRadioConfig,
			id: 'ID'
		});

		expect(component).toMatchSnapshot();
	});

	it('has a label', () => {
		component = new Radio({
			...defaultRadioConfig,
			label: 'label'
		});

		expect(component).toMatchSnapshot();
	});

	it('has a predefined Value', () => {
		component = new Radio({
			...defaultRadioConfig,
			placeholder: 'Option 1'
		});

		expect(component).toMatchSnapshot();
	});

	it('is not required', () => {
		component = new Radio({
			...defaultRadioConfig,
			required: false
		});

		expect(component).toMatchSnapshot();
	});

	it('renders Label if showLabel is true', () => {
		component = new Radio({
			...defaultRadioConfig,
			label: 'text',
			showLabel: true
		});

		expect(component).toMatchSnapshot();
	});

	it('has a spritemap', () => {
		component = new Radio(defaultRadioConfig);

		expect(component).toMatchSnapshot();
	});

	it('has a value', () => {
		component = new Radio({
			...defaultRadioConfig,
			value: 'value'
		});

		expect(component).toMatchSnapshot();
	});
});
