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

import {waitForElementToBeRemoved} from '@testing-library/dom';
import {act, cleanup, fireEvent, render} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import {createMemoryHistory} from 'history';
import React from 'react';
import {DndProvider} from 'react-dnd';
import HTML5Backend from 'react-dnd-html5-backend';
import {HashRouter} from 'react-router-dom';

import {AppContextProvider} from '../../../../src/main/resources/META-INF/resources/js/AppContext.es';
import EditTableView from '../../../../src/main/resources/META-INF/resources/js/pages/table-view/EditTableView.es';
import EditTableViewContextProvider from '../../../../src/main/resources/META-INF/resources/js/pages/table-view/EditTableViewContextProvider.es';
import * as time from '../../../../src/main/resources/META-INF/resources/js/utils/time.es';
import * as toast from '../../../../src/main/resources/META-INF/resources/js/utils/toast.es';

const fieldTypeResponse = [
	{
		system: false,
		scope: 'app-builder,forms',
		displayOrder: 6,
		icon: 'calendar',
		name: 'date',
		description: 'Select date from a date picker.',
		javaScriptModule:
			'dynamic-data-mapping-form-field-type@5.0.20/DatePicker/DatePicker.es',
		label: 'Date',
		group: 'basic',
	},
	{
		system: false,
		scope: 'forms',
		displayOrder: 1,
		icon: 'paragraph',
		name: 'paragraph',
		description: 'Add a title and/or a body text in your form.',
		javaScriptModule:
			'dynamic-data-mapping-form-field-type@5.0.20/Paragraph/Paragraph.es',
		label: 'Paragraph',
		group: 'interface',
	},
	{
		system: false,
		scope: 'layout',
		displayOrder: 11,
		icon: 'link',
		name: 'link_to_layout',
		description: 'Link to another page in the current site.',
		javaScriptModule:
			'layout-dynamic-data-mapping-form-field-type@1.0.7/LayoutSelector',
		label: 'Link to Page',
		group: 'basic',
	},
	{
		system: false,
		scope: 'app-builder,forms',
		displayOrder: 2,
		icon: 'list',
		name: 'select',
		description: 'Select options from a list.',
		javaScriptModule:
			'dynamic-data-mapping-form-field-type@5.0.20/Select/Select.es',
		label: 'Select from List',
		group: 'basic',
	},
	{
		system: false,
		scope: 'forms',
		displayOrder: 9,
		icon: 'color-picker',
		name: 'color',
		description: 'Select color from a color picker.',
		javaScriptModule:
			'dynamic-data-mapping-form-field-type@5.0.20/ColorPicker/ColorPicker.es',
		label: 'Color',
		group: 'basic',
	},
	{
		system: true,
		scope: 'app-builder,forms',
		displayOrder: 8,
		icon: 'adjust',
		name: 'fieldset',
		description: 'fieldset-field-type-description',
		javaScriptModule:
			'dynamic-data-mapping-form-field-type@5.0.20/FieldSet/FieldSet.es',
		label: 'Fields Group',
		group: 'basic',
	},
	{
		system: false,
		scope: 'app-builder,forms',
		displayOrder: 7,
		icon: 'integer',
		name: 'numeric',
		description: 'It only accepts numbers.',
		javaScriptModule:
			'dynamic-data-mapping-form-field-type@5.0.20/Numeric/Numeric.es',
		label: 'Numeric',
		group: 'basic',
	},
	{
		system: false,
		scope: 'app-builder,forms',
		displayOrder: 4,
		icon: 'check-circle-full',
		name: 'checkbox_multiple',
		description: 'Select multiple options.',
		javaScriptModule:
			'dynamic-data-mapping-form-field-type@5.0.20/CheckboxMultiple/CheckboxMultiple.es',
		label: 'Multiple Selection',
		group: 'basic',
	},
	{
		system: false,
		scope: 'app-builder,forms',
		displayOrder: 3,
		icon: 'radio-button',
		name: 'radio',
		description: 'Select only one option.',
		javaScriptModule:
			'dynamic-data-mapping-form-field-type@5.0.20/Radio/Radio.es',
		label: 'Single Selection',
		group: 'basic',
	},
	{
		system: false,
		scope: 'journal',
		displayOrder: 10,
		icon: 'web-content',
		name: 'journal_article',
		description: 'Select a web content article.',
		javaScriptModule:
			'journal-article-dynamic-data-mapping-form-field-type@1.0.5/JournalArticleSelector',
		label: 'Web Content',
		group: 'basic',
	},
	{
		system: true,
		scope: '',
		displayOrder: 9,
		icon: 'password-policies',
		name: 'password',
		description: '',
		javaScriptModule:
			'dynamic-data-mapping-form-field-type@5.0.20/Password/Password.es',
		label: 'Password',
		group: '',
	},
	{
		system: true,
		scope: '',
		displayOrder: 2147483647,
		icon: '',
		name: 'captcha',
		description: '',
		javaScriptModule:
			'dynamic-data-mapping-form-field-type@5.0.20/Captcha/Captcha.es',
		label: '',
		group: '',
	},
	{
		system: true,
		scope: '',
		displayOrder: 8,
		icon: 'check-circle',
		name: 'checkbox',
		description: 'Answer yes or no questions using a checkbox.',
		javaScriptModule:
			'dynamic-data-mapping-form-field-type@5.0.20/Checkbox/Checkbox.es',
		label: 'Boolean Question',
		group: 'basic',
	},
	{
		system: true,
		scope: '',
		displayOrder: 2147483647,
		icon: '',
		name: 'options',
		description: '',
		javaScriptModule:
			'dynamic-data-mapping-form-field-type@5.0.20/Options/Options.es',
		label: '',
		group: '',
	},
	{
		system: false,
		scope: 'app-builder,forms',
		displayOrder: 1,
		icon: 'text',
		name: 'text',
		description: 'Single line or multi-line text area.',
		javaScriptModule:
			'dynamic-data-mapping-form-field-type@5.0.20/Text/Text.es',
		label: 'Text',
		group: 'basic',
	},
	{
		system: true,
		scope: '',
		displayOrder: 2147483647,
		icon: 'icon-font',
		name: 'key_value',
		description: '',
		javaScriptModule:
			'dynamic-data-mapping-form-field-type@5.0.20/KeyValue/KeyValue.es',
		label: '',
		group: '',
	},
	{
		system: true,
		scope: '',
		displayOrder: 2147483647,
		icon: '',
		name: 'validation',
		description: '',
		javaScriptModule:
			'dynamic-data-mapping-form-field-type@5.0.20/Validation/Validation.es',
		label: '',
		group: '',
	},
	{
		system: false,
		scope: 'forms',
		displayOrder: 8,
		icon: 'picture',
		name: 'image',
		description: 'Send an image file.',
		javaScriptModule:
			'dynamic-data-mapping-form-field-type@5.0.20/ImagePicker/ImagePicker.es',
		label: 'Image',
		group: 'basic',
	},
	{
		system: true,
		scope: '',
		displayOrder: 2147483647,
		icon: 'icon-font',
		name: 'editor',
		description: '',
		javaScriptModule:
			'dynamic-data-mapping-form-field-type@5.0.20/Editor/Editor.es',
		label: '',
		group: '',
	},
	{
		system: false,
		scope: 'forms',
		displayOrder: 11,
		icon: 'separator',
		name: 'separator',
		description: 'Add a separator to organize the content.',
		javaScriptModule:
			'dynamic-data-mapping-form-field-type@5.0.20/Separator/Separator.es',
		label: 'Separator',
		group: 'basic',
	},
	{
		system: true,
		scope: '',
		displayOrder: 1,
		icon: 'text',
		name: 'localizable_text',
		description: 'localizable-text-field-type-description',
		javaScriptModule:
			'dynamic-data-mapping-form-field-type@5.0.20/LocalizableText/LocalizableText.es',
		label: 'localizable-text-field-type-label',
		group: 'basic',
	},
	{
		system: false,
		scope: 'forms',
		displayOrder: 5,
		icon: 'table2',
		name: 'grid',
		description: 'Select options from a matrix.',
		javaScriptModule:
			'dynamic-data-mapping-form-field-type@5.0.20/Grid/Grid.es',
		label: 'Grid',
		group: 'basic',
	},
	{
		system: false,
		scope: 'forms',
		displayOrder: 8,
		icon: 'textbox',
		name: 'rich_text',
		description: 'Create rich text content.',
		javaScriptModule:
			'dynamic-data-mapping-form-field-type@5.0.20/RichText/RichText.es',
		label: 'Rich Text',
		group: 'basic',
	},
	{
		system: false,
		scope: 'app-builder,forms',
		displayOrder: 8,
		icon: 'upload',
		name: 'document_library',
		description: 'Send documents and media files.',
		javaScriptModule:
			'dynamic-data-mapping-form-field-type@5.0.20/DocumentLibrary/DocumentLibrary.es',
		label: 'Upload',
		group: 'basic',
	},
	{
		system: false,
		scope: 'forms',
		displayOrder: 10,
		icon: 'globe',
		name: 'geolocation',
		description: 'Select a location from a map.',
		javaScriptModule:
			'dynamic-data-mapping-form-field-type@5.0.20/Geolocation/Geolocation.es',
		label: 'Geolocation',
		group: 'basic',
	},
];

const tableViewResponseOneItem = {
	availableLanguageIds: ['en_US'],
	dataDefinitionFields: [
		{
			customProperties: {
				displayStyle: 'singleline',
				fieldNamespace: '',
				visibilityExpression: '',
				autocomplete: false,
				ddmDataProviderInstanceOutput: '[]',
				ddmDataProviderInstanceId: '[]',
				dataType: 'string',
				options: {
					en_US: [
						{
							label: 'Option',
							value: 'Option',
						},
					],
				},
				tooltip: {
					en_US: '',
				},
				placeholder: {
					en_US: '',
				},
				dataSourceType: 'manual',
			},
			defaultValue: {
				en_US: '',
			},
			description: {
				en_US: 'Say your name Player',
			},
			fieldType: 'text',
			indexType: 'keyword',
			indexable: true,
			label: {
				en_US: 'Player',
			},
			localizable: true,
			name: 'Text',
			nestedDataDefinitionFields: [],
			readOnly: false,
			repeatable: false,
			required: false,
			showLabel: true,
			tip: {
				en_US: '',
			},
		},
	],
	dataDefinitionKey: '36601',
	dateCreated: '2020-04-24T13:50:04Z',
	dateModified: '2020-04-24T13:50:13Z',
	defaultLanguageId: 'en_US',
	description: {},
	id: 36602,
	name: {
		en_US: 'teste',
	},
	siteId: 20125,
	storageType: 'json',
	userId: 20127,
};

const tableViewResponseTwoItens = {
	availableLanguageIds: ['en_US'],
	dataDefinitionFields: [
		{
			customProperties: {
				displayStyle: 'singleline',
				fieldNamespace: '',
				visibilityExpression: '',
				autocomplete: false,
				ddmDataProviderInstanceOutput: '[]',
				ddmDataProviderInstanceId: '[]',
				dataType: 'string',
				options: {
					en_US: [
						{
							label: 'Option',
							value: 'Option',
						},
					],
				},
				tooltip: {
					en_US: '',
				},
				placeholder: {
					en_US: '',
				},
				dataSourceType: 'manual',
			},
			defaultValue: {
				en_US: '',
			},
			fieldType: 'text',
			indexType: 'keyword',
			indexable: true,
			label: {
				en_US: 'Player',
			},
			localizable: true,
			name: 'Text',
			nestedDataDefinitionFields: [],
			readOnly: false,
			repeatable: false,
			required: false,
			showLabel: true,
			tip: {
				en_US: '',
			},
		},
		{
			customProperties: {
				fieldNamespace: '',
				visibilityExpression: '',
				ddmDataProviderInstanceOutput: '[]',
				ddmDataProviderInstanceId: '[]',
				dataType: 'string',
				options: {
					en_US: [
						{
							label: 'INTZ',
							value: 'INTZ',
						},
						{
							label: 'PAIN',
							value: 'PAIN',
						},
						{
							label: 'SKT',
							value: 'SKT',
						},
					],
				},
				multiple: false,
				dataSourceType: '[manual]',
			},
			defaultValue: {
				en_US: [],
			},
			fieldType: 'select',
			indexType: 'keyword',
			indexable: true,
			label: {
				en_US: 'Team',
			},
			localizable: true,
			name: 'SelectFromList',
			nestedDataDefinitionFields: [],
			readOnly: false,
			repeatable: false,
			required: false,
			showLabel: true,
			tip: {
				en_US: '',
			},
		},
	],
	dataDefinitionKey: '36715',
	dateCreated: '2020-04-29T16:48:28Z',
	dateModified: '2020-04-29T16:49:03Z',
	defaultLanguageId: 'en_US',
	description: {},
	id: 36716,
	name: {
		en_US: 'teste',
	},
	siteId: 20125,
	storageType: 'json',
	userId: 20127,
};

describe('EditTableView', () => {
	const state = {
		dataDefinition: {
			dataDefinitionFields: [],
		},
		dataListView: {
			name: {
				en_US: '',
			},
		},
	};
	const dispatch = jest.fn();

	const EditTableViewWithRouter = ({history = createMemoryHistory()}) => (
		<AppContextProvider value={{}}>
			<div className="tools-control-group">
				<div className="control-menu-level-1-heading" />
			</div>
			<HashRouter>
				<EditTableViewContextProvider value={[state, dispatch]}>
					<EditTableView history={history} />
				</EditTableViewContextProvider>
			</HashRouter>
		</AppContextProvider>
	);

	let spySuccessToast;
	let spyFromNow;

	beforeEach(() => {
		jest.useFakeTimers();
		spyFromNow = jest
			.spyOn(time, 'fromNow')
			.mockImplementation(() => 'months ago');
		spySuccessToast = jest
			.spyOn(toast, 'successToast')
			.mockImplementation();
	});

	afterEach(() => {
		cleanup();
		jest.clearAllTimers();
		jest.restoreAllMocks();
	});

	afterAll(() => {
		jest.useRealTimers();
	});

	xit('renders', async () => {
		fetch.mockResponseOnce(JSON.stringify(fieldTypeResponse));
		fetch.mockResponseOnce(JSON.stringify(tableViewResponseOneItem));
		fetch.mockResponseOnce(JSON.stringify(fieldTypeResponse));
		fetch.mockResponseOnce(JSON.stringify(tableViewResponseOneItem));

		const {asFragment} = render(
			<DndProvider backend={HTML5Backend}>
				<EditTableViewWithRouter />
			</DndProvider>
		);

		await act(async () => {
			jest.runAllTimers();
		});

		expect(asFragment()).toMatchSnapshot();
	});

	it('renders with one item', async () => {
		fetch.mockResponseOnce(JSON.stringify(fieldTypeResponse));
		fetch.mockResponseOnce(JSON.stringify(tableViewResponseOneItem));
		fetch.mockResponseOnce(JSON.stringify(fieldTypeResponse));
		fetch.mockResponseOnce(JSON.stringify(tableViewResponseOneItem));

		const {
			container,
			debug,
			queryAllByText,
			queryByPlaceholderText,
			queryByText,
		} = render(
			<DndProvider backend={HTML5Backend}>
				<EditTableViewWithRouter />
			</DndProvider>
		);

		await act(async () => {
			jest.runAllTimers();
		});

		expect(queryAllByText('Player').length).toBe(1);

		const [column] = queryAllByText('Player');
		expect(column).toBeTruthy();

		expect(
			queryByText('drag-columns-from-the-sidebar-and-drop-here')
		).toBeTruthy();

		userEvent.dblClick(column);

		expect(
			queryByText('drag-columns-from-the-sidebar-and-drop-here')
		).toBeFalsy();

		expect(queryAllByText('Player').length).toBe(2);

		const tableName = queryByPlaceholderText('untitled-table-view');
		const saveButton = queryByText('save');

		expect(tableName.value).toBe('');
		// aqui um teste para ver se o disabled do save button está true
		//console.log(saveButton);

		fireEvent.change(tableName, {target: {value: 'Players'}});

		expect(tableName.value).toBe('Players');

		//fazer um teste para ver se o disabled do botão está falso

		//salvar

		//debug();
	});

	xit('renders with two itens, search for one field and set filters', async () => {
		fetch.mockResponseOnce(JSON.stringify(fieldTypeResponse));
		fetch.mockResponseOnce(JSON.stringify(tableViewResponseTwoItens));
		fetch.mockResponseOnce(JSON.stringify(fieldTypeResponse));
		fetch.mockResponseOnce(JSON.stringify(tableViewResponseTwoItens));

		const {
			container,
			debug,
			queryAllByPlaceholderText,
			queryAllByText,
			queryByText,
		} = render(
			<DndProvider backend={HTML5Backend}>
				<EditTableViewWithRouter />
			</DndProvider>
		);

		await act(async () => {
			jest.runAllTimers();
		});

		expect(queryAllByText('Player').length).toBe(1);
		expect(queryAllByText('Team').length).toBe(1);

		const [columnPlayer] = queryAllByText('Player');
		expect(columnPlayer).toBeTruthy();
		const [columnTeam] = queryAllByText('Team');
		expect(columnTeam).toBeTruthy();

		expect(
			queryByText('drag-columns-from-the-sidebar-and-drop-here')
		).toBeTruthy();

		userEvent.dblClick(columnPlayer);
		userEvent.dblClick(columnTeam);

		expect(
			queryByText('drag-columns-from-the-sidebar-and-drop-here')
		).toBeFalsy();

		expect(queryAllByText('Player').length).toBe(2);
		expect(queryAllByText('Team').length).toBe(2);

		const [search] = queryAllByPlaceholderText('search...');
		expect(search.value).toBe('');

		fireEvent.change(search, {target: {value: 'Player'}});

		expect(queryAllByText('Player').length).toBe(2);
		expect(queryAllByText('Team').length).toBe(1);

		const [filtersButton] = queryAllByText('filters');
		expect(filtersButton).toBeTruthy();

		fireEvent.click(filtersButton);
		// não tá aparecendo no html os outros filtros como INTZ, SKT, PAIN
		// só aparece o select all
		// o label ta como SelectFromList mas deveria ser Team

		expect(queryByText('filter-entries-by-columns')).toBeTruthy();

		const chooseOptionsButton = container.querySelector(
			'span.multiple-select-filter-values'
		);
		expect(chooseOptionsButton).toBeTruthy();
		fireEvent.click(chooseOptionsButton);

		const selectAll = queryByText('select-all');
		expect(selectAll).toBeTruthy();
		fireEvent.click(selectAll);
		//não está aparecendo a opção clear depois de colocar select all

		//console.log(chooseOptionsButton);

		debug();
	});
});
