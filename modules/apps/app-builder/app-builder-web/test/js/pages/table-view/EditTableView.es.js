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

import {act, cleanup, fireEvent, render} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import {createMemoryHistory} from 'history';
import React from 'react';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';
import {Route, Router} from 'react-router-dom';

import EditTableView from '../../../../src/main/resources/META-INF/resources/js/pages/table-view/EditTableView.es';
import * as toast from '../../../../src/main/resources/META-INF/resources/js/utils/toast.es';
import AppContextProvider from '../../AppContextProviderWrapper.es';
import {DATA_DEFINITION_RESPONSES} from '../../constants.es';

const fieldTypes = [
	{
		icon: 'calendar',
		javaScriptModule:
			'dynamic-data-mapping-form-field-type@5.0.20/DatePicker/DatePicker.es',
		name: 'date',
	},
	{
		icon: 'list',
		javaScriptModule:
			'dynamic-data-mapping-form-field-type@5.0.20/Select/Select.es',
		name: 'select',
	},
	{
		icon: 'adjust',
		javaScriptModule:
			'dynamic-data-mapping-form-field-type@5.0.20/FieldSet/FieldSet.es',
		name: 'fieldset',
	},
	{
		icon: 'integer',
		javaScriptModule:
			'dynamic-data-mapping-form-field-type@5.0.20/Numeric/Numeric.es',
		name: 'numeric',
	},
	{
		icon: 'check-circle-full',
		javaScriptModule:
			'dynamic-data-mapping-form-field-type@5.0.20/CheckboxMultiple/CheckboxMultiple.es',
		name: 'checkbox_multiple',
	},
	{
		icon: 'radio-button',
		javaScriptModule:
			'dynamic-data-mapping-form-field-type@5.0.20/Radio/Radio.es',
		name: 'radio',
	},
	{
		icon: 'text',
		javaScriptModule:
			'dynamic-data-mapping-form-field-type@5.0.20/Text/Text.es',
		name: 'text',
	},
	{
		icon: 'upload',
		javaScriptModule:
			'dynamic-data-mapping-form-field-type@5.0.20/DocumentLibrary/DocumentLibrary.es',
		name: 'document_library',
	},
];

const fieldTypeResponse = fieldTypes.map((fieldType, index) => ({
	...fieldType,
	displayOrder: index,
	group: 'basic',
	label: fieldType.name.toUpperCase(),
	scope: 'app-builder,forms',
	system: false,
}));

const tableViewWithId = {
	appliedFilters: {},
	dataDefinitionId: 36716,
	dateCreated: '2020-05-12T18:49:56Z',
	dateModified: '2020-05-12T18:49:56Z',
	fieldNames: ['Text'],
	id: 36516,
	name: {
		en_US: 'Name',
	},
	siteId: 20125,
	sortField: '',
	userId: 20127,
};

describe('EditTableView', () => {
	let spySuccessToast;
	let spyErrorToast;

	beforeEach(() => {
		jest.useFakeTimers();
		spySuccessToast = jest
			.spyOn(toast, 'successToast')
			.mockImplementation();
		spyErrorToast = jest.spyOn(toast, 'errorToast').mockImplementation();
	});

	afterEach(() => {
		cleanup();
		jest.clearAllTimers();
		jest.restoreAllMocks();
	});

	afterAll(() => {
		jest.useRealTimers();
	});

	it('renders', async () => {
		fetch
			.mockResponseOnce(JSON.stringify(fieldTypeResponse))
			.mockResponseOnce(
				JSON.stringify(DATA_DEFINITION_RESPONSES.ONE_ITEM)
			);

		const {asFragment} = render(
			<DndProvider backend={HTML5Backend}>
				<EditTableView />
			</DndProvider>,
			{
				wrapper: AppContextProvider,
			}
		);

		await act(async () => {
			jest.runAllTimers();
		});

		expect(asFragment()).toMatchSnapshot();
	});

	it('renders with two fields in the sidebar and saves successfully', async () => {
		fetch
			.mockResponseOnce(JSON.stringify(fieldTypeResponse))
			.mockResponseOnce(
				JSON.stringify(DATA_DEFINITION_RESPONSES.TWO_ITEMS)
			)
			.mockResponseOnce();

		const {queryAllByText, queryByPlaceholderText, queryByText} = render(
			<DndProvider backend={HTML5Backend}>
				<EditTableView />
			</DndProvider>,
			{
				wrapper: AppContextProvider,
			}
		);

		await act(async () => {
			jest.runAllTimers();
		});

		expect(queryAllByText('Name').length).toBe(1);
		expect(queryAllByText('Options').length).toBe(1);

		const [columnName] = queryAllByText('Name');
		expect(columnName).toBeTruthy();

		const [columnOptions] = queryAllByText('Options');
		expect(columnOptions).toBeTruthy();

		expect(
			queryByText('drag-columns-from-the-sidebar-and-drop-here')
		).toBeTruthy();

		act(() => {
			userEvent.dblClick(columnName);
			userEvent.dblClick(columnOptions);
		});

		expect(
			queryByText('drag-columns-from-the-sidebar-and-drop-here')
		).toBeFalsy();

		expect(queryAllByText('Name').length).toBe(2);
		expect(queryAllByText('Options').length).toBe(2);

		const tableName = queryByPlaceholderText('untitled-table-view');

		expect(tableName.value).toBe('');

		fireEvent.change(tableName, {target: {value: 'My Table View'}});

		expect(tableName.value).toBe('My Table View');

		const save = queryByText('save');

		await act(async () => {
			fireEvent.click(save);
		});

		expect(spySuccessToast.mock.calls.length).toBe(1);
	});

	it('renders with two fields in the sidebar and does not save successfully', async () => {
		fetch
			.mockResponseOnce(JSON.stringify(fieldTypeResponse))
			.mockResponseOnce(
				JSON.stringify(DATA_DEFINITION_RESPONSES.TWO_ITEMS)
			)
			.mockRejectOnce(() =>
				Promise.reject(
					JSON.stringify({
						status: 'BAD_REQUEST',
						title: 'View is empty',
					})
				)
			);

		const {queryByPlaceholderText, queryByText} = render(
			<DndProvider backend={HTML5Backend}>
				<EditTableView />
			</DndProvider>,
			{
				wrapper: AppContextProvider,
			}
		);

		await act(async () => {
			jest.runAllTimers();
		});

		expect(
			queryByText('drag-columns-from-the-sidebar-and-drop-here')
		).toBeTruthy();

		const tableName = queryByPlaceholderText('untitled-table-view');

		fireEvent.change(tableName, {target: {value: 'My Table View'}});

		const save = queryByText('save');

		await act(async () => {
			fireEvent.click(save);
		});

		expect(spyErrorToast.mock.calls.length).toBe(1);

		const cancel = queryByText('cancel');

		fireEvent.click(cancel);
	});

	it('renders with two fields in the sidebar and make actions', async () => {
		fetch
			.mockResponseOnce(JSON.stringify(fieldTypeResponse))
			.mockResponseOnce(
				JSON.stringify(DATA_DEFINITION_RESPONSES.TWO_ITEMS)
			);

		const {
			container,
			queryAllByPlaceholderText,
			queryAllByText,
			queryByText,
		} = render(
			<DndProvider backend={HTML5Backend}>
				<EditTableView />
			</DndProvider>,
			{
				wrapper: AppContextProvider,
			}
		);

		await act(async () => {
			jest.runAllTimers();
		});

		const [columnName] = queryAllByText('Name');
		const [columnOptions] = queryAllByText('Options');

		act(() => {
			userEvent.dblClick(columnName);
			userEvent.dblClick(columnOptions);
		});

		const [search] = queryAllByPlaceholderText('search...');
		expect(search.value).toBe('');

		fireEvent.change(search, {target: {value: 'Name'}});

		expect(queryAllByText('Name').length).toBe(2);
		expect(queryAllByText('Options').length).toBe(1);

		const [filtersButton] = queryAllByText('filters');
		expect(filtersButton).toBeTruthy();

		fireEvent.click(filtersButton);

		expect(queryByText('filter-entries-by-columns')).toBeTruthy();

		const chooseOptionsButton = container.querySelector(
			'span.multiple-select-filter-values'
		);
		expect(chooseOptionsButton).toBeTruthy();
		fireEvent.click(chooseOptionsButton);

		const selectAll = queryByText('select-all');
		expect(selectAll).toBeTruthy();
		fireEvent.click(selectAll);
	});

	it('renders with one field already inside the table and saves', async () => {
		fetch
			.mockResponseOnce(JSON.stringify(fieldTypeResponse))
			.mockResponseOnce(
				JSON.stringify(DATA_DEFINITION_RESPONSES.TWO_ITEMS)
			)
			.mockResponseOnce(JSON.stringify(tableViewWithId))
			.mockResponseOnce(JSON.stringify({}));

		const {queryAllByText, queryByPlaceholderText, queryByText} = render(
			<DndProvider backend={HTML5Backend}>
				<Router
					history={createMemoryHistory({
						initialEntries: [
							`dataTableViewId/${tableViewWithId.id}`,
						],
					})}
				>
					<Route path={'dataTableViewId/:dataListViewId'}>
						<EditTableView />
					</Route>
				</Router>
			</DndProvider>,
			{
				wrapper: AppContextProvider,
			}
		);

		await act(async () => {
			jest.runAllTimers();
		});

		expect(
			queryByText('drag-columns-from-the-sidebar-and-drop-here')
		).toBeFalsy();

		expect(queryAllByText('Name').length).toBe(2);
		expect(queryAllByText('Options').length).toBe(1);

		const tableName = queryByPlaceholderText('untitled-table-view');
		expect(tableName.value).toBe('Name');

		await act(async () => {
			fireEvent.submit(tableName);
		});

		expect(fetch.mock.calls.length).toBe(4);
		expect(spySuccessToast.mock.calls.length).toBe(1);
	});

	it('renders and toggle sidebar by clicking in the toggle button', async () => {
		fetch
			.mockResponseOnce(JSON.stringify(fieldTypeResponse))
			.mockResponseOnce(
				JSON.stringify(DATA_DEFINITION_RESPONSES.ONE_ITEM)
			);

		render(
			<DndProvider backend={HTML5Backend}>
				<EditTableView />
			</DndProvider>,
			{
				wrapper: AppContextProvider,
			}
		);

		await act(async () => {
			jest.runAllTimers();
		});

		const closeSidebarButton = document.querySelector('.close-sidebar-btn');

		fireEvent.click(closeSidebarButton);

		expect(
			document.querySelector(
				'.app-builder-table-view__content--sidebar-closed'
			)
		).toBeTruthy();
		expect(
			document.querySelector('.app-builder-table-view__sidebar--closed')
		).toBeTruthy();

		const openSidebarButton = document.querySelector('.open-sidebar-btn');

		fireEvent.click(openSidebarButton);

		expect(
			document.querySelector(
				'.app-builder-table-view__content--sidebar-closed'
			)
		).toBeFalsy();
		expect(
			document.querySelector('.app-builder-table-view__sidebar--closed')
		).toBeFalsy();
	});

	it('renders with defaultLanguageId from the dataDefinition instead of Liferay Object', async () => {
		fetch
			.mockResponseOnce(JSON.stringify(fieldTypeResponse))
			.mockResponseOnce(
				JSON.stringify({
					...DATA_DEFINITION_RESPONSES.ONE_ITEM,
					availableLanguageIds: ['en_US', 'pt_BR'],
					defaultLanguageId: 'pt_BR',
				})
			);

		render(
			<DndProvider backend={HTML5Backend}>
				<EditTableView />
			</DndProvider>,
			{
				wrapper: AppContextProvider,
			}
		);

		await act(async () => {
			jest.runAllTimers();
		});

		const localizableDropdown = document.querySelector(
			'.localizable-dropdown'
		);

		expect(localizableDropdown.textContent).toEqual('pt-BR');

		await act(async () => {
			userEvent.click(localizableDropdown);
		});

		expect(
			document.querySelector('.localizable-item-default .autofit-section')
				.textContent
		).toEqual('pt-BR');
	});
});
