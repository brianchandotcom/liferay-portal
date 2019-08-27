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

import Text from '../../../src/main/resources/META-INF/resources/Text/Text.es';

let component;
const spritemap = 'icons.svg';

const defaultTextConfig = {
	name: 'textField',
	spritemap
};

describe('Field Text', () => {
	afterEach(() => {
		if (component) {
			component.dispose();
		}
	});

	it('is not readOnly', () => {
		component = new Text({
			...defaultTextConfig,
			readOnly: false
		});

		expect(component).toMatchSnapshot();
	});

	it('has a helptext', () => {
		component = new Text({
			...defaultTextConfig,
			tip: 'Type something'
		});

		expect(component).toMatchSnapshot();
	});

	it('has an id', () => {
		component = new Text({
			...defaultTextConfig,
			id: 'ID'
		});

		expect(component).toMatchSnapshot();
	});

	it('has a label', () => {
		component = new Text({
			...defaultTextConfig,
			label: 'label'
		});

		expect(component).toMatchSnapshot();
	});

	it('has a placeholder', () => {
		component = new Text({
			...defaultTextConfig,
			placeholder: 'Placeholder'
		});

		expect(component).toMatchSnapshot();
	});

	it('is not required', () => {
		component = new Text({
			...defaultTextConfig,
			required: false
		});

		expect(component).toMatchSnapshot();
	});

	it('renders Label if showLabel is true', () => {
		component = new Text({
			...defaultTextConfig,
			label: 'text',
			showLabel: true
		});

		expect(component).toMatchSnapshot();
	});

	it('has a spritemap', () => {
		component = new Text(defaultTextConfig);

		expect(component).toMatchSnapshot();
	});

	it('has a value', () => {
		component = new Text({
			...defaultTextConfig,
			value: 'value'
		});

		expect(component).toMatchSnapshot();
	});

	it('emits a field edit with correct parameters', done => {
		const handleFieldEdited = data => {
			expect(data).toEqual(
				expect.objectContaining({
					fieldInstance: component,
					originalEvent: expect.any(Object),
					value: expect.any(String)
				})
			);
			done();
		};

		const events = {fieldEdited: handleFieldEdited};

		component = new Text({
			...defaultTextConfig,
			events,
			key: 'input'
		});

		component._handleFieldChanged({
			target: {
				value: 'test'
			}
		});
	});
});
