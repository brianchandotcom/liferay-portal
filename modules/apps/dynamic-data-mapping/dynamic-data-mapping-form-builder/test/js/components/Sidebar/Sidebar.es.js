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

import '../../__fixtures__/MockField.es';

import {dom as MetalTestUtil} from 'metal-dom';

import Sidebar from '../../../src/main/resources/META-INF/resources/js/components/Sidebar/Sidebar.es';
import {PagesVisitor} from '../../../src/main/resources/META-INF/resources/js/util/visitors.es';

let component;
const focusedField = {
	colIndex: 0,
	pageIndex: 0,
	rowIndex: 0,
	settingsContext: {
		pages: []
	},
	type: 'date'
};
const spritemap = 'icons.svg';

const getFieldValue = (pages, fieldName) => {
	const visitor = new PagesVisitor(pages);
	let fieldValue;

	visitor.mapFields(field => {
		if (field.fieldName === fieldName) {
			fieldValue = field.value;
		}
	});

	return fieldValue;
};

const fillField = (pages, fieldName, value) => {
	const visitor = new PagesVisitor(pages);

	return visitor.mapFields(field => {
		if (field.fieldName === fieldName) {
			field = {
				...field,
				value
			};
		}

		return field;
	});
};

const mockFieldType = {
	description: 'Single line or multiline text area.',
	icon: 'text',
	initialConfig_: {
		locale: 'en_US'
	},
	label: 'Text Field',
	name: 'text',
	settingsContext: {
		pages: [
			{
				rows: [
					{
						columns: [
							{
								fields: [
									{
										fieldName: 'label',
										localizable: true,
										type: 'text',
										value: 'Mock Field',
										visible: true
									},
									{
										fieldName: 'name',
										type: 'text',
										visible: true
									},
									{
										fieldName: 'showLabel',
										type: 'checkbox',
										value: true,
										visible: true
									},
									{
										fieldName: 'required',
										type: 'checkbox',
										visible: true
									},
									{
										fieldName: 'type',
										type: 'text',
										value: 'text',
										visible: false
									},
									{
										fieldName: 'validation',
										type: 'validation',
										value: 'expression=1',
										visible: false
									}
								]
							}
						]
					}
				]
			}
		]
	},
	type: 'text'
};

const fieldTypes = [
	{
		description: 'Select date from a Datepicker.',
		group: 'basic',
		icon: 'calendar',
		label: 'Date',
		name: 'date'
	},
	{
		description: 'Single line or multiline text area.',
		group: 'basic',
		icon: 'text',
		label: 'Text Field',
		name: 'text'
	},
	{
		description: 'Select only one item with a radio button.',
		group: 'basic',
		icon: 'radio-button',
		label: 'Single Selection',
		name: 'radio'
	},
	{
		description: 'Choose one or more options from a list.',
		group: 'basic',
		icon: 'list',
		label: 'Select from list',
		name: 'select'
	},
	{
		description: 'Select options from a matrix.',
		group: 'basic',
		icon: 'grid',
		label: 'Grid',
		name: 'grid'
	},
	{
		description: 'Select multiple options using a checkbox.',
		group: 'basic',
		icon: 'select-from-list',
		label: 'Multiple Selection',
		name: 'checkbox'
	}
].map(fieldType => ({
	...mockFieldType,
	...fieldType
}));

describe('Sidebar', () => {
	beforeEach(() => jest.useFakeTimers());

	afterEach(() => {
		if (component) {
			component.dispose();
		}
	});

	it('renders the default markup', () => {
		component = new Sidebar({
			fieldTypes,
			spritemap
		});

		expect(component).toMatchSnapshot();
	});

	it('renders a Sidebar open', () => {
		component = new Sidebar({
			fieldTypes,
			spritemap
		});
		component.open();

		jest.runAllTimers();

		expect(component).toMatchSnapshot();
	});

	it('renders a Sidebar closed', () => {
		component = new Sidebar({
			fieldTypes,
			spritemap
		});

		component.open();
		component.close();

		expect(component).toMatchSnapshot();
	});

	it('renders a Sidebar with fieldTypes', () => {
		component = new Sidebar({
			fieldTypes,
			spritemap
		});

		component.open();

		jest.runAllTimers();

		expect(component).toMatchSnapshot();
	});

	it('closes the sidebar when the mouse down event is not on it', () => {
		component = new Sidebar({
			fieldTypes,
			spritemap
		});

		jest.runAllTimers();

		component.open();

		component._handleDocumentMouseDown({
			target: null
		});

		expect(component).toMatchSnapshot();
	});

	it('emits fieldMoved when the dragEnd method is called', () => {
		component = new Sidebar({
			fieldTypes,
			spritemap
		});

		const spy = jest.spyOn(component, 'emit');

		const event = {
			preventDefault: jest.fn()
		};

		const data = {
			source: {
				dataset: {
					fieldTypeName: 'paragraph'
				}
			},
			target: {
				parentElement: {
					dataset: {
						ddmFieldColumn: 0,
						ddmFieldPage: 0,
						ddmFieldRow: 0
					}
				}
			}
		};

		jest.runAllTimers();

		component.open();

		component._handleDragEnded(data, event);

		jest.runAllTimers();

		expect(component).toMatchSnapshot();

		expect(spy).toHaveBeenCalledWith('fieldAdded', expect.anything());
	});

	it('emits the fieldDuplicated event when the duplicate field option is clicked on the sidebar settings', () => {
		component = new Sidebar({
			fieldTypes,
			focusedField,
			spritemap
		});

		const spy = jest.spyOn(component, 'emit');

		const data = {
			item: {
				settingsItem: 'duplicate-field'
			}
		};

		jest.runAllTimers();

		component.open();

		component._handleFieldSettingsClicked({data});

		expect(spy).toHaveBeenCalledWith('fieldDuplicated', expect.anything());
	});

	it('emits the fieldDeleted event when the delete field option is clicked on the sidebar settings', () => {
		component = new Sidebar({
			fieldTypes,
			focusedField,
			spritemap
		});

		const spy = jest.spyOn(component, 'emit');

		const data = {
			item: {
				settingsItem: 'delete-field'
			}
		};

		jest.runAllTimers();

		component.open();

		component._handleFieldSettingsClicked({data});

		expect(spy).toHaveBeenCalled();
	});

	it('emits the fieldChangesCanceled event when the cancel field chages option is clicked on the sidebar settings', () => {
		component = new Sidebar({
			fieldTypes,
			focusedField,
			spritemap
		});

		const spy = jest.spyOn(component, 'emit');

		const data = {
			item: {
				settingsItem: 'cancel-field-changes'
			}
		};

		jest.runAllTimers();

		component.open();

		component._handleFieldSettingsClicked({data});

		expect(spy).toHaveBeenCalled();
	});

	it('renders a Sidebar with spritemap', () => {
		component = new Sidebar({
			fieldTypes,
			spritemap
		});

		component.open();

		jest.runAllTimers();

		expect(component).toMatchSnapshot();
	});

	it('closes the sidebar in edition mode', () => {
		const focusedField = mockFieldType;

		component = new Sidebar({
			fieldTypes,
			focusedField,
			spritemap
		});

		component.open();

		jest.runAllTimers();

		expect(component).toMatchSnapshot();
	});

	it('closes the sidebar in edition mode', () => {
		const focusedField = mockFieldType;

		component = new Sidebar({
			fieldTypes,
			focusedField,
			spritemap
		});

		component.open();

		jest.runAllTimers();

		MetalTestUtil.triggerEvent(
			component.refs.previousButton.element,
			'click',
			{}
		);

		jest.runAllTimers();

		expect(component.state.open).toBeFalsy();
		expect(component).toMatchSnapshot();
	});

	it('propagates evaluator changed event', () => {
		const focusedField = mockFieldType;

		component = new Sidebar({
			fieldTypes,
			focusedField,
			spritemap
		});

		component.open();

		jest.runAllTimers();

		const {FormRenderer} = component.refs;
		const spy = jest.spyOn(component, 'emit');

		FormRenderer.emit('evaluated', {
			focusedField
		});

		expect(spy).toHaveBeenCalled();
	});

	it('propagates field edited event', () => {
		const focusedField = mockFieldType;

		component = new Sidebar({
			fieldTypes,
			focusedField,
			spritemap
		});

		component.open();

		jest.runAllTimers();

		const {FormRenderer} = component.refs;
		const spy = jest.spyOn(component, 'emit');

		FormRenderer.emit('fieldEdited', {});

		expect(spy).toHaveBeenCalled();
	});

	describe('Interaction with markup', () => {
		it('closes Sidebar when click the button close', () => {
			component = new Sidebar({
				fieldTypes,
				spritemap
			});

			component.open();

			expect(component.state.open).toBeTruthy();

			const spy = jest.spyOn(component, 'close');
			const {closeButton} = component.refs;

			closeButton.click();

			jest.runAllTimers();

			expect(component.state.open).toBeFalsy();
			expect(spy).toHaveBeenCalled();
		});
	});

	describe('Changing field type', () => {
		it('is always be enabled', () => {
			component = new Sidebar({
				fieldTypes,
				focusedField: mockFieldType,
				spritemap
			});

			jest.runAllTimers();

			expect(component.isChangeFieldTypeEnabled()).toBeTruthy();
		});

		it('keeps basic properties after changing field type', done => {
			const {settingsContext} = mockFieldType;
			let {pages} = settingsContext;

			pages = fillField(pages, 'label', 'my field');
			pages = fillField(pages, 'showLabel', false);

			component = new Sidebar({
				fieldTypes,
				focusedField: {
					...mockFieldType,
					settingsContext: {
						...mockFieldType.settingsContext,
						pages
					}
				},
				spritemap
			});

			jest.runAllTimers();

			component.once('focusedFieldUpdated', ({settingsContext, type}) => {
				expect(type).toBe('checkbox');
				expect(getFieldValue(settingsContext.pages, 'type')).toBe(
					'checkbox'
				);
				expect(getFieldValue(settingsContext.pages, 'label')).toBe(
					'my field'
				);
				expect(getFieldValue(settingsContext.pages, 'showLabel')).toBe(
					false
				);

				done();
			});

			component.changeFieldType('checkbox');
		});

		it('does not keep validation settings between field type', done => {
			const {settingsContext} = mockFieldType;
			let {pages} = settingsContext;

			pages = fillField(pages, 'validation', 'a=b');

			expect(getFieldValue(pages, 'validation')).toEqual('a=b');

			component = new Sidebar({
				fieldTypes,
				focusedField: {
					...mockFieldType,
					settingsContext: {
						...mockFieldType.settingsContext,
						pages
					}
				},
				spritemap
			});

			jest.runAllTimers();

			component.once('focusedFieldUpdated', ({settingsContext}) => {
				expect(
					getFieldValue(settingsContext.pages, 'validation')
				).not.toEqual('a=b');

				done();
			});

			component.changeFieldType('checkbox');
		});

		it('emits an event with new field type settings', done => {
			component = new Sidebar({
				fieldTypes,
				focusedField: mockFieldType,
				spritemap
			});

			jest.runAllTimers();

			component.once('focusedFieldUpdated', ({settingsContext, type}) => {
				expect(type).toBe('checkbox');

				expect(settingsContext).toMatchSnapshot();

				done();
			});

			component.changeFieldType('checkbox');

			jest.runAllTimers();
		});
	});
});
