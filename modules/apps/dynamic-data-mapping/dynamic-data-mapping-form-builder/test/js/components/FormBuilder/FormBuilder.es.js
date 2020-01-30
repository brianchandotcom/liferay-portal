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

import dom from 'metal-dom';

import Builder from '../../../../src/main/resources/META-INF/resources/js/components/FormBuilder/FormBuilder.es';
import Pages from '../../__mock__/mockPages.es';
import SuccessPageSettings from '../../__mock__/mockSuccessPage.es';

const spritemap = 'icons.svg';

let addButton;
let basicInfo;
let component;
let pages;
let translationManager;
let successPageSettings;

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
										localizable: true
									},
									{
										fieldName: 'name'
									},
									{
										fieldName: 'required'
									},
									{
										fieldName: 'type'
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
		icon: 'calendar',
		label: 'Date',
		name: 'date',
		settingsContext: {
			pages: []
		}
	},
	mockFieldType,
	{
		description: 'Select only one item with a radio button.',
		icon: 'radio-button',
		label: 'Single Selection',
		name: 'radio',
		settingsContext: {
			pages: []
		}
	},
	{
		description: 'Choose one or more options from a list.',
		icon: 'list',
		label: 'Select from list',
		name: 'select',
		settingsContext: {
			pages: []
		}
	},
	{
		description: 'Select options from a matrix.',
		icon: 'grid',
		label: 'Grid',
		name: 'grid',
		settingsContext: {
			pages: []
		}
	},
	{
		description: 'Select multiple options using a checkbox.',
		icon: 'select-from-list',
		label: 'Multiple Selection',
		name: 'checkbox',
		settingsContext: {
			pages: []
		}
	}
];

describe('Builder', () => {
	beforeEach(() => {
		pages = JSON.parse(JSON.stringify(Pages));
		successPageSettings = JSON.parse(JSON.stringify(SuccessPageSettings));

		jest.useFakeTimers();

		dom.enterDocument('<button id="addFieldButton"></button>');
		dom.enterDocument('<div class="ddm-translation-manager"></div>');
		dom.enterDocument('<div class="ddm-form-basic-info"></div>');

		addButton = document.querySelector('#addFieldButton');
		basicInfo = document.querySelector('.ddm-form-basic-info');
		translationManager = document.querySelector('.ddm-translation-manager');

		component = new Builder({
			fieldTypes,
			namespace: '_namespace_',
			pages,
			paginationMode: 'wizard',
			rules: [],
			spritemap,
			successPageSettings
		});
	});

	afterEach(() => {
		dom.exitDocument(addButton);
		dom.exitDocument(basicInfo);
		dom.exitDocument(translationManager);

		if (component) {
			component.dispose();
		}

		jest.clearAllTimers();
	});

	it('renders the default markup', () => {
		expect(component).toMatchSnapshot();
	});

	it('continues to propagate the fieldAdded event', () => {
		const {sidebar} = component.refs;
		const spy = jest.spyOn(component, 'emit');

		sidebar.emit('fieldAdded', {
			data: {
				target: {
					parentElement: {
						parentElement: {
							classList: [
								'row',
								{
									value: 'row'
								}
							]
						}
					}
				}
			},
			fieldType: mockFieldType
		});

		jest.runAllTimers();

		expect(spy).toHaveBeenCalledWith('fieldAdded', expect.anything());
	});

	it('continues to propagate the fieldBlurred event', () => {
		const {sidebar} = component.refs;
		const spy = jest.spyOn(component, 'emit');

		sidebar.emit('fieldBlurred');

		jest.runAllTimers();

		expect(spy).toHaveBeenCalledWith('sidebarFieldBlurred');
	});

	it('continues to propagate the fieldClicked event', () => {
		const {FormRenderer} = component.refs;
		const spy = jest.spyOn(component, 'emit');

		FormRenderer.emit('fieldClicked', {
			columnIndex: 0,
			pageIndex: 0,
			rowIndex: 0
		});

		jest.runAllTimers();

		expect(spy).toHaveBeenCalled();
	});

	it('opens the sidebar when attached and there are no fields on the active page', () => {
		const spy = jest.spyOn(component, 'openSidebar');

		component.props.pages = [{rows: [{columns: [{fields: []}]}]}];

		component.attached();

		expect(spy).toHaveBeenCalled();
	});

	it('opens the sidebar when a field is clicked', () => {
		const {FormRenderer} = component.refs;
		const spy = jest.spyOn(component, 'openSidebar');

		FormRenderer.emit('fieldClicked', {
			columnIndex: 0,
			pageIndex: 0,
			rowIndex: 0
		});

		jest.runAllTimers();

		expect(spy).toHaveBeenCalled();
	});

	it('continues to propagate the pageAdded event', () => {
		const {FormRenderer} = component.refs;
		const spy = jest.spyOn(component, 'emit');

		FormRenderer.emit('pageAdded');

		jest.runAllTimers();

		expect(spy).toHaveBeenCalledWith('pageAdded');
	});

	it('continues to propagate the pageDeleted event', () => {
		const {FormRenderer} = component.refs;
		const spy = jest.spyOn(component, 'emit');

		FormRenderer.emit('pageDeleted');

		jest.runAllTimers();

		expect(spy).toHaveBeenCalledWith('pageDeleted', expect.anything());
	});

	it('continues to propagate the pagesUpdated event', () => {
		const {FormRenderer} = component.refs;
		const spy = jest.spyOn(component, 'emit');

		FormRenderer.emit('pagesUpdated');

		jest.runAllTimers();

		expect(spy).toHaveBeenCalledWith('pagesUpdated', expect.anything());
	});

	it('continues to propagate the activePageUpdated event', () => {
		const {FormRenderer} = component.refs;
		const spy = jest.spyOn(component, 'emit');

		FormRenderer.emit('activePageUpdated');

		jest.runAllTimers();

		expect(spy).toHaveBeenCalledWith(
			'activePageUpdated',
			expect.anything()
		);
	});

	it('continues to propagate the fieldDuplicated event', () => {
		const {FormRenderer} = component.refs;
		const spy = jest.spyOn(component, 'emit');

		FormRenderer.emit('fieldDuplicated');

		jest.runAllTimers();

		expect(spy).toHaveBeenCalledWith('fieldDuplicated', expect.anything());
	});

	it('continues to propagate the fieldEdited event', () => {
		const {sidebar} = component.refs;
		const spy = jest.spyOn(component, 'emit');

		component.props.focusedField = mockFieldType;

		sidebar.emit('settingsFieldEdited', {
			fieldInstance: {
				...mockFieldType,
				fieldName: 'label'
			},
			value: 'new label'
		});

		jest.runAllTimers();

		expect(spy).toHaveBeenCalledWith('fieldEdited', expect.anything());
	});

	it('continues to propagate the fieldEdited event when the edited field is predefined value', () => {
		const {sidebar} = component.refs;
		const spy = jest.spyOn(component, 'emit');

		component.props.focusedField = mockFieldType;

		sidebar.emit('settingsFieldEdited', {
			fieldInstance: {
				...mockFieldType,
				fieldName: 'predefinedValue'
			}
		});

		jest.runAllTimers();

		expect(spy).toHaveBeenCalledWith('fieldEdited', expect.anything());
	});

	it('continues to propagate the fieldMoved event', () => {
		const spy = jest.spyOn(component, 'emit');
		const {FormRenderer} = component.refs;
		const mockEvent = jest.fn();

		FormRenderer.emit('fieldMoved', mockEvent);

		expect(spy).toHaveBeenCalled();
		expect(spy).toHaveBeenCalledWith('fieldMoved', expect.anything());
	});

	it('opens sidebar when the "pageReset" event is received', () => {
		const {FormRenderer, sidebar} = component.refs;

		FormRenderer.emit('pageReset');

		jest.runAllTimers();

		expect(sidebar.state.open).toBeTruthy();
	});

	it('opens sidebar when activePage changes and new page has no fields', () => {
		const spy = jest.spyOn(component, 'openSidebar');

		component.props.pages = [
			...pages,
			{
				rows: []
			}
		];
		component.props.activePage = 1;

		jest.runAllTimers();

		expect(spy).toHaveBeenCalled();
	});

	it('does not open sidebar when activePage changes and new page has fields', () => {
		const spy = jest.spyOn(component, 'openSidebar');

		component.props.pages = [...pages, ...pages];
		component.props.activePage = 1;

		jest.runAllTimers();

		expect(spy).not.toHaveBeenCalled();
	});

	it('shows modal when fieldChangesCanceled event is trigered from sidebar', () => {
		const {cancelChangesModal, sidebar} = component.refs;

		sidebar.emit('fieldChangesCanceled');

		jest.runAllTimers();

		const modal = cancelChangesModal.element;

		expect(modal.classList.contains('show')).toEqual(true);

		expect(component).toMatchSnapshot();
	});

	it('emits fieldChangesCanceled event when yes is clicked in the modal', () => {
		const spy = jest.spyOn(component, 'emit');
		const {cancelChangesModal, sidebar} = component.refs;
		const mockEvent = jest.fn();

		sidebar.emit('fieldChangesCanceled', mockEvent);

		cancelChangesModal.element
			.querySelectorAll(
				'.modal-content .btn-group .btn-group-item button'
			)[1]
			.click();

		jest.runAllTimers();

		expect(spy).toHaveBeenCalled();
		expect(spy).toHaveBeenCalledWith('fieldChangesCanceled', {});
	});

	it('shows modal when trash button gets clicked', () => {
		const {FormRenderer} = component.refs;

		FormRenderer.emit('fieldDeleted', {
			columnIndex: 0,
			pageIndex: 1,
			rowIndex: 0
		});

		jest.runAllTimers();

		const modal = document.querySelector('.modal');

		expect(modal.classList.contains('show')).toEqual(true);

		expect(component).toMatchSnapshot();
	});

	it('emits deleteField event when yes is clicked in the modal', () => {
		const spy = jest.spyOn(component, 'emit');
		const {FormRenderer} = component.refs;
		const mockEvent = jest.fn();

		FormRenderer.emit('deleteFieldClicked', mockEvent);

		component.element
			.querySelectorAll(
				'.modal-content .btn-group .btn-group-item button'
			)[1]
			.click();

		jest.runAllTimers();

		expect(spy).toHaveBeenCalled();
		expect(spy).toHaveBeenCalledWith('fieldDeleted', expect.anything());
	});

	it('propagates successPageChanged event', () => {
		const spy = jest.spyOn(component, 'emit');
		const {FormRenderer} = component.refs;
		const mockEvent = jest.fn();

		FormRenderer.emit('successPageChanged', mockEvent);

		jest.runAllTimers();

		expect(spy).toHaveBeenCalled();
		expect(spy).toHaveBeenCalledWith(
			'successPageChanged',
			expect.anything()
		);
	});

	it('does not open sidebar when the delete current page option item is clicked', () => {
		const spy = jest.spyOn(component, 'openSidebar');

		const componentPages = [...pages, ...pages];

		const builderComponent = new Builder({
			fieldTypes,
			namespace: '_namespace_',
			pages: componentPages,
			paginationMode: 'wizard',
			rules: [],
			spritemap,
			successPageSettings
		});
		const data = {
			item: {
				settingsItem: 'reset-page'
			}
		};
		const {FormRenderer} = builderComponent.refs;

		FormRenderer._handlePageSettingsClicked({
			data
		});

		jest.runAllTimers();

		expect(spy).not.toHaveBeenCalled();
	});
});
