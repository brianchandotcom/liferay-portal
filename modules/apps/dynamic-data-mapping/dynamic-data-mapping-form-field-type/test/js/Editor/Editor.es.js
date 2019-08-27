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

import Editor from '../../../src/main/resources/META-INF/resources/Editor/Editor.es';

let component;
const spritemap = 'icons.svg';

const defaultEditorConfig = {
	name: 'textField',
	spritemap
};

describe('Field Editor', () => {
	afterEach(() => {
		if (component) {
			component.dispose();
		}
	});

	it('is readOnly', () => {
		component = new Editor({
			...defaultEditorConfig,
			readOnly: true
		});

		expect(component).toMatchSnapshot();
	});

	it('has a helptext', () => {
		component = new Editor({
			...defaultEditorConfig,
			tip: 'Type something'
		});

		expect(component).toMatchSnapshot();
	});

	it('has an id', () => {
		component = new Editor({
			...defaultEditorConfig,
			id: 'ID'
		});

		expect(component).toMatchSnapshot();
	});

	it('has a label', () => {
		component = new Editor({
			...defaultEditorConfig,
			label: 'label'
		});

		expect(component).toMatchSnapshot();
	});

	it('has a placeholder', () => {
		component = new Editor({
			...defaultEditorConfig,
			placeholder: 'Placeholder'
		});

		expect(component).toMatchSnapshot();
	});

	it('is not required', () => {
		component = new Editor({
			...defaultEditorConfig,
			required: false
		});

		expect(component).toMatchSnapshot();
	});

	it('renders Label if showLabel is true', () => {
		component = new Editor({
			...defaultEditorConfig,
			label: 'text',
			showLabel: true
		});

		expect(component).toMatchSnapshot();
	});

	it('has a spritemap', () => {
		component = new Editor(defaultEditorConfig);

		expect(component).toMatchSnapshot();
	});

	it('has a value', () => {
		component = new Editor({
			...defaultEditorConfig,
			value: 'value'
		});

		expect(component).toMatchSnapshot();
	});

	it('has a key', () => {
		component = new Editor({
			...defaultEditorConfig,
			key: 'key'
		});

		expect(component).toMatchSnapshot();
	});

	it('emits a change value when onChangeEditor method is triggered', () => {
		component = new Editor({
			...defaultEditorConfig
		});
		const event = {};
		const spy = jest.spyOn(component, 'emit');

		component._onChangeEditor(event);

		expect(spy).toBeCalled();
	});

	it('triggers AlloyEditor actionPerformed method', () => {
		component = new Editor({
			...defaultEditorConfig
		});

		component._onActionPerformed({
			data: {
				props: {}
			}
		});

		component.willReceiveState({
			children: true,
			value: {
				newVal: '<p>test</p>'
			}
		});

		expect(component).toMatchSnapshot();
	});
});
