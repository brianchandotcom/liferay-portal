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

import KeyValue from 'source/KeyValue/KeyValue.es';

let component;
const spritemap = 'icons.svg';

describe('KeyValue', () => {
	afterEach(() => {
		if (component) {
			component.dispose();
		}
	});

	it('should be not edidable', () => {
		component = new KeyValue({
			name: 'keyValue',
			readOnly: false,
			spritemap
		});

		expect(component).toMatchSnapshot();
	});

	it('should have a helptext', () => {
		component = new KeyValue({
			name: 'keyValue',
			spritemap,
			tip: 'Type something'
		});

		expect(component).toMatchSnapshot();
	});

	it('should have an id', () => {
		component = new KeyValue({
			id: 'ID',
			name: 'keyValue',
			spritemap
		});

		expect(component).toMatchSnapshot();
	});

	it('should have a label', () => {
		component = new KeyValue({
			label: 'label',
			name: 'keyValue',
			spritemap
		});

		expect(component).toMatchSnapshot();
	});

	it('should have a predefined Value', () => {
		component = new KeyValue({
			name: 'keyValue',
			placeholder: 'Option 1',
			spritemap
		});

		expect(component).toMatchSnapshot();
	});

	it('should not be required', () => {
		component = new KeyValue({
			name: 'keyValue',
			required: false,
			spritemap
		});

		expect(component).toMatchSnapshot();
	});

	it('should render Label if showLabel is true', () => {
		component = new KeyValue({
			label: 'text',
			name: 'keyValue',
			showLabel: true,
			spritemap
		});

		expect(component).toMatchSnapshot();
	});

	it('should have a spritemap', () => {
		component = new KeyValue({
			name: 'keyValue',
			spritemap
		});

		expect(component).toMatchSnapshot();
	});

	it('should have a value', () => {
		component = new KeyValue({
			name: 'keyValue',
			spritemap,
			value: 'value'
		});

		expect(component).toMatchSnapshot();
	});

	it('should render component with a key', () => {
		component = new KeyValue({
			keyword: 'key',
			name: 'keyValue',
			spritemap
		});

		expect(component).toMatchSnapshot();
	});
});
