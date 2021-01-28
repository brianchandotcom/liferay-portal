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

import {PagesVisitor} from 'dynamic-data-mapping-form-renderer';

import Sidebar from '../../../../src/main/resources/META-INF/resources/js/components/Sidebar/Sidebar.es';
import mockFieldType from '../../__mock__/mockFieldType.es';
import withContextMock from '../../__mock__/withContextMock.es';

const mockFieldTypes = [
	{
		description: 'Custom field description.',
		group: 'customized',
		icon: 'icon',
		label: 'Custom Field',
		name: 'custom_field',
	},
	{
		description: 'Select date from a Datepicker.',
		group: 'basic',
		icon: 'calendar',
		label: 'Date',
		name: 'date',
	},
	{
		description: 'Single line or multiline text area.',
		group: 'basic',
		icon: 'text',
		label: 'Text Field',
		name: 'text',
	},
	{
		description: 'Select only one item with a radio button.',
		group: 'basic',
		icon: 'radio-button',
		label: 'Single Selection',
		name: 'radio',
	},
	{
		description: 'Choose one or more options from a list.',
		group: 'basic',
		icon: 'list',
		label: 'Select from list',
		name: 'select',
	},
	{
		description: 'Select options from a matrix.',
		group: 'basic',
		icon: 'grid',
		label: 'Grid',
		name: 'grid',
	},
	{
		description: 'Select multiple options using a checkbox.',
		group: 'basic',
		icon: 'select-from-list',
		label: 'Multiple Selection',
		name: 'checkbox',
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
											value: 'Multiple Selection',
											visible: true,
										},
										{
											fieldName: 'name',
											value: 'MultipleSelection',
											visible: true,
										},
										{
											fieldName: 'required',
											value: false,
											visible: true,
										},
										{
											fieldName: 'showLabel',
											value: true,
											visible: false,
										},
									],
								},
							],
						},
					],
				},
			],
		},
	},
].map((fieldType) => ({
	...mockFieldType,
	...fieldType,
}));

const changeField = ({settingsContext}, fieldName, value, visible = true) => {
	const visitor = new PagesVisitor(settingsContext.pages);

	return visitor.mapFields((field) => {
		if (field.fieldName === fieldName) {
			field = {
				...field,
				value,
				visible,
			};
		}

		return field;
	});
};

const getFieldValue = (pages, fieldName) => {
	const visitor = new PagesVisitor(pages);
	let fieldValue;

	visitor.mapFields((field) => {
		if (field.fieldName === fieldName) {
			fieldValue = field.value;
		}
	});

	return fieldValue;
};

let component;

const defaultSidebarConfig = {
	fieldTypes: mockFieldTypes,
	spritemap: 'icons.svg',
};

const SidebarWithContextMock = withContextMock(Sidebar);

describe('Sidebar', () => {
	beforeEach(() => {
		fetch.mockResponse(JSON.stringify({}), {
			status: 200,
		});

		jest.useFakeTimers();
	});

	afterEach(() => {
		if (component) {
			component.dispose();
		}

		jest.clearAllTimers();
	});

	it('renders a Sidebar opened', () => {
		component = new Sidebar(defaultSidebarConfig);
		component.open();

		expect(component.state.open).toBeTruthy();
	});

	it('renders a Sidebar closed', () => {
		component = new Sidebar(defaultSidebarConfig);
		component.open();
		component.close();

		expect(component.state.open).toBeFalsy();
	});

	it('renders a Sidebar with fieldTypes', () => {
		component = new Sidebar(defaultSidebarConfig);
		component.open();

		expect(component).toMatchSnapshot();
	});

	it('renders a Sidebar with fieldTypes separated by category', () => {
		component = new Sidebar(defaultSidebarConfig);
		component.open();

		const basicTab = document.querySelector(
			'#ddm-field-types-basic-header'
		);

		expect(basicTab).toEqual(expect.anything());
		expect(basicTab.classList.value).toEqual(
			'collapse-icon panel-header panel-header-link'
		);
	});

	it('closes the sidebar when the mouse down event is not on it', () => {
		component = new Sidebar(defaultSidebarConfig);
		component.open();
		component._handleDocumentMouseDown({
			target: null,
		});

		expect(component.state.open).toBeFalsy();
	});

	it('emits the fieldDuplicated event when the duplicate field option is clicked on the sidebar settings', () => {
		component = new SidebarWithContextMock({
			...defaultSidebarConfig,
			editingLanguageId: 'en_US',
			focusedField: mockFieldType,
			portletNamespace: 'portletNamespace',
		});

		const data = {
			item: {
				settingsItem: 'duplicate-field',
			},
		};
		const spy = jest.spyOn(component, 'emit');

		component.open();
		component._handleElementSettingsClicked({data});

		expect(spy).toHaveBeenCalled();
		expect(component.context.dispatch).toHaveBeenCalledWith(
			'fieldDuplicated',
			expect.anything()
		);
	});

	describe('fieldChangesCanceled(state, event)', () => {
		it('emits event when the cancel field chages option is clicked on the sidebar settings', () => {
			component = new SidebarWithContextMock({
				...defaultSidebarConfig,
				editingLanguageId: 'en_US',
				focusedField: mockFieldType,
				portletNamespace: 'portletNamespace',
			});

			const data = {
				item: {
					settingsItem: 'cancel-field-changes',
				},
			};
			const spy = jest.spyOn(component, 'emit');

			component.open();
			component._handleElementSettingsClicked({data});

			expect(spy).toHaveBeenCalled();
		});

		it('shows modal when cancel field chages option is clicked on the sidebar settings', () => {
			component = new SidebarWithContextMock({
				...defaultSidebarConfig,
				editingLanguageId: 'en_US',
				focusedField: mockFieldType,
				portletNamespace: 'portletNamespace',
			});

			const data = {
				item: {
					settingsItem: 'cancel-field-changes',
				},
			};

			component.open();
			component._handleElementSettingsClicked({data});

			const {cancelChangesModal} = component.refs;

			expect(cancelChangesModal.body).toEqual(
				'are-you-sure-you-want-to-cancel'
			);
			expect(cancelChangesModal).toMatchSnapshot();
		});

		it('emits fieldChangesCanceled event when yes is clicked in the modal', () => {
			component = new SidebarWithContextMock({
				...defaultSidebarConfig,
				editingLanguageId: 'en_US',
				focusedField: mockFieldType,
				portletNamespace: 'portletNamespace',
			});

			const data = {
				item: {
					settingsItem: 'cancel-field-changes',
				},
			};

			component.open();
			component._handleElementSettingsClicked({data});

			document
				.querySelector(
					'.modal-content .btn-group .btn-group-item .btn-primary'
				)
				.click();

			expect(component.context.dispatch).toHaveBeenCalledWith(
				'fieldChangesCanceled',
				{}
			);
		});
	});

	it('closes the sidebar in edition mode', () => {
		component = new Sidebar(defaultSidebarConfig);

		component.open();
		component._handlePreviousButtonClicked();

		expect(component.state.open).toBeFalsy();
		expect(component).toMatchSnapshot();
	});

	it('propagates evaluator changed event', () => {
		component = new SidebarWithContextMock({
			...defaultSidebarConfig,
			editingLanguageId: 'en_US',
			focusedField: mockFieldType,
			portletNamespace: 'portletNamespace',
		});

		const changedFocusedField = {
			...mockFieldType,
			settingsContext: {
				...mockFieldType.settingsContext,
				pages: changeField(mockFieldType, 'required', false),
			},
		};

		component.open();
		component._handleEvaluatorChanged(
			changedFocusedField.settingsContext.pages
		);

		expect(component.context.dispatch).toHaveBeenCalledWith(
			'focusedFieldEvaluationEnded',
			changedFocusedField
		);
	});

	it('propagates field edited event', () => {
		component = new SidebarWithContextMock({
			...defaultSidebarConfig,
			editingLanguageId: 'en_US',
			focusedField: mockFieldType,
			portletNamespace: 'portletNamespace',
		});

		component.open();
		component._handleSettingsFieldEdited({
			fieldInstance: {
				fieldName: 'label',
				isDisposed: () => false,
			},
			value: 'Text Field 2',
		});

		expect(component.context.dispatch).toHaveBeenCalledWith('fieldEdited', {
			editingLanguageId: 'en_US',
			propertyName: 'label',
			propertyValue: 'Text Field 2',
		});
	});

	describe('Interaction with markup', () => {
		it('closes Sidebar when click the button close', () => {
			component = new Sidebar(defaultSidebarConfig);
			component.open();

			expect(component.state.open).toBeTruthy();

			const spy = jest.spyOn(component, 'close');
			const {closeButton} = component.refs;

			closeButton.click();

			expect(component.state.open).toBeFalsy();
			expect(spy).toHaveBeenCalled();
		});
	});

	describe('Changing field type', () => {
		it.skip('is always enabled when editingLanguageId is equal to defaultLanguageId', () => {
			component = new SidebarWithContextMock({
				...defaultSidebarConfig,
				defaultLanguageId: 'en_US',
				editingLanguageId: 'en_US',
				focusedField: mockFieldType,
				portletNamespace: 'portletNamespace',
			});

			expect(component.isChangeFieldTypeEnabled()).toBeTruthy();
		});

		it.skip('is not enabled when editingLanguageId is not equal to defaultLanguageId', () => {
			component = new SidebarWithContextMock({
				...defaultSidebarConfig,
				defaultLanguageId: 'en_US',
				editingLanguageId: 'pt_BR',
				focusedField: mockFieldType,
				portletNamespace: 'portletNamespace',
			});

			expect(component.isChangeFieldTypeEnabled()).toBeFalsy();
		});

		it('keeps basic properties after changing field type', () => {
			component = new SidebarWithContextMock({
				...defaultSidebarConfig,
				editingLanguageId: 'en_US',
				focusedField: mockFieldType,
				portletNamespace: 'portletNamespace',
			});

			component.open();
			component.changeFieldType('checkbox');

			const changedFieldType = {
				...mockFieldType,
				description: 'Select multiple options using a checkbox.',
				icon: 'select-from-list',
				settingsContext: {
					...mockFieldType.settingsContext,
					pages: changeField(mockFieldType, 'showLabel', true, false),
				},
				type: 'checkbox',
			};

			expect(component.context.dispatch).toHaveBeenCalledWith(
				'focusedFieldEvaluationEnded',
				{
					...changedFieldType,
					changedFieldType: true,
					instanceId: expect.any(String),
					settingsContext: {
						...changedFieldType.settingsContext,
						pages: [
							{
								rows: [
									{
										columns: [
											{
												fields: changedFieldType.settingsContext.pages[0].rows[0].columns[0].fields.map(
													(field) => ({
														...field,
														instanceId: expect.any(
															String
														),
														name: expect.any(
															String
														),
													})
												),
											},
										],
									},
								],
							},
						],
					},
				}
			);
		});

		it('does not keep validation settings between field type', () => {
			component = new SidebarWithContextMock({
				...defaultSidebarConfig,
				editingLanguageId: 'en_US',
				focusedField: {
					...mockFieldType,
					settingsContext: {
						pages: [
							{
								rows: [
									{
										columns: [
											{
												fields: [
													...mockFieldType
														.settingsContext
														.pages[0].rows[0]
														.columns[0].fields,
													{
														fieldName: 'validation',
														value: 'a=b',
													},
												],
											},
										],
									},
								],
							},
						],
					},
				},
				portletNamespace: 'portletNamespace',
			});

			expect(
				getFieldValue(
					component.props.focusedField.settingsContext.pages,
					'validation'
				)
			).toEqual('a=b');

			component.open();
			component.changeFieldType('checkbox');

			const changedFieldType = {
				...mockFieldType,
				description: 'Select multiple options using a checkbox.',
				icon: 'select-from-list',
				settingsContext: {
					...mockFieldType.settingsContext,
					pages: changeField(mockFieldType, 'showLabel', true, false),
				},
				type: 'checkbox',
			};

			expect(
				getFieldValue(
					changedFieldType.settingsContext.pages,
					'validation'
				)
			).not.toEqual('a=b');
			expect(component.context.dispatch).toHaveBeenCalledWith(
				'focusedFieldEvaluationEnded',
				{
					...changedFieldType,
					changedFieldType: true,
					instanceId: expect.any(String),
					settingsContext: {
						...changedFieldType.settingsContext,
						pages: [
							{
								rows: [
									{
										columns: [
											{
												fields: changedFieldType.settingsContext.pages[0].rows[0].columns[0].fields.map(
													(field) => ({
														...field,
														instanceId: expect.any(
															String
														),
														name: expect.any(
															String
														),
													})
												),
											},
										],
									},
								],
							},
						],
					},
				}
			);
		});

		it('emits an event with new field type settings', () => {
			component = new SidebarWithContextMock({
				...defaultSidebarConfig,
				editingLanguageId: 'en_US',
				focusedField: mockFieldType,
				portletNamespace: 'portletNamespace',
			});

			component.open();
			component.changeFieldType('checkbox');

			expect(component.context.dispatch).toHaveBeenCalledWith(
				'focusedFieldEvaluationEnded',
				expect.anything()
			);
		});
	});
});
