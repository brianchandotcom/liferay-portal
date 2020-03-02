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

import KeyValue from '../../../src/main/resources/META-INF/resources/KeyValue/KeyValue.es';
import withContextMock from '../__mocks__/withContextMock.es';

let component;
const spritemap = 'icons.svg';

const KeyValueWithContextMock = withContextMock(KeyValue);

describe('KeyValue', () => {
	afterEach(() => {
		if (component) {
			component.dispose();
		}
	});

	it('is not edidable', () => {
		component = new KeyValueWithContextMock({
			name: 'keyValue',
			readOnly: false,
			spritemap,
		});

		expect(component).toMatchSnapshot();
	});

	it('has a helptext', () => {
		component = new KeyValueWithContextMock({
			name: 'keyValue',
			spritemap,
			tip: 'Type something',
		});

		expect(component).toMatchSnapshot();
	});

	it('has an id', () => {
		component = new KeyValueWithContextMock({
			id: 'ID',
			name: 'keyValue',
			spritemap,
		});

		expect(component).toMatchSnapshot();
	});

	it('has a label', () => {
		component = new KeyValueWithContextMock({
			label: 'label',
			name: 'keyValue',
			spritemap,
		});

		expect(component).toMatchSnapshot();
	});

	it('has a predefined Value', () => {
		component = new KeyValueWithContextMock({
			name: 'keyValue',
			placeholder: 'Option 1',
			spritemap,
		});

		expect(component).toMatchSnapshot();
	});

	it('is not required', () => {
		component = new KeyValueWithContextMock({
			name: 'keyValue',
			required: false,
			spritemap,
		});

		expect(component).toMatchSnapshot();
	});

	it('renders Label if showLabel is true', () => {
		component = new KeyValueWithContextMock({
			label: 'text',
			name: 'keyValue',
			showLabel: true,
			spritemap,
		});

		expect(component).toMatchSnapshot();
	});

	it('has a spritemap', () => {
		component = new KeyValueWithContextMock({
			name: 'keyValue',
			spritemap,
		});

		expect(component).toMatchSnapshot();
	});

	it('has a value', () => {
		component = new KeyValueWithContextMock({
			name: 'keyValue',
			spritemap,
			value: 'value',
		});

		expect(component).toMatchSnapshot();
	});

	it('renders component with a key', () => {
		component = new KeyValueWithContextMock({
			keyword: 'key',
			name: 'keyValue',
			spritemap,
		});

		expect(component).toMatchSnapshot();
	});
});
