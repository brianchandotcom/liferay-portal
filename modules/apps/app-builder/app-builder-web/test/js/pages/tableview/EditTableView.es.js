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
import {Router} from 'react-router-dom';
import userEvent from '@testing-library/user-event';
import {createMemoryHistory} from 'history';
import React from 'react';
import {DndProvider} from 'react-dnd';
import HTML5Backend from 'react-dnd-html5-backend';
import AppContextProviderWrapper from '../../AppContextProviderWrapper.es';
import EditTableView from '../../../../src/main/resources/META-INF/resources/js/pages/table-view/EditTableView.es';
import * as time from '../../../../src/main/resources/META-INF/resources/js/utils/time.es';
import * as toast from '../../../../src/main/resources/META-INF/resources/js/utils/toast.es';
import {
	fieldTypeResponse,
	tableViewResponseOneItem,
	tableViewResponseTwoItens,
	tableViewWithId,
} from '../../mock';

describe('EditTableView', () => {
	let spySuccessToast;
	let spyErrorToast;
	let spyFromNow;

	beforeEach(() => {
		jest.useFakeTimers();
		spyFromNow = jest
			.spyOn(time, 'fromNow')
			.mockImplementation(() => 'months ago');
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
		fetch.mockResponseOnce(JSON.stringify(fieldTypeResponse));
		fetch.mockResponseOnce(JSON.stringify(tableViewResponseOneItem));

		const {asFragment} = render(
			<DndProvider backend={HTML5Backend}>
				<EditTableView />
			</DndProvider>,
			{
				wrapper: AppContextProviderWrapper,
			}
		);

		await act(async () => {
			jest.runAllTimers();
		});

		expect(asFragment()).toMatchSnapshot();
	});

	it('renders with two fields in the sidebar and saves successfully', async () => {
		fetch.mockResponseOnce(JSON.stringify(fieldTypeResponse));
		fetch.mockResponseOnce(JSON.stringify(tableViewResponseTwoItens));
		fetch.mockResponseOnce();

		const {queryAllByText, queryByPlaceholderText, queryByText} = render(
			<DndProvider backend={HTML5Backend}>
				<EditTableView />
			</DndProvider>,
			{
				wrapper: AppContextProviderWrapper,
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

		userEvent.dblClick(columnName);
		userEvent.dblClick(columnOptions);

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
		fetch.mockResponseOnce(JSON.stringify(fieldTypeResponse));
		fetch.mockResponseOnce(JSON.stringify(tableViewResponseTwoItens));
		fetch.mockRejectOnce(() =>
			Promise.reject(
				JSON.stringify({status: 'BAD_REQUEST', title: 'View is empty'})
			)
		);

		const {queryByPlaceholderText, queryByText} = render(
			<DndProvider backend={HTML5Backend}>
				<EditTableView />
			</DndProvider>,
			{
				wrapper: AppContextProviderWrapper,
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
		fetch.mockResponseOnce(JSON.stringify(fieldTypeResponse));
		fetch.mockResponseOnce(JSON.stringify(tableViewResponseTwoItens));

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
				wrapper: AppContextProviderWrapper,
			}
		);

		await act(async () => {
			jest.runAllTimers();
		});

		const [columnName] = queryAllByText('Name');
		const [columnOptions] = queryAllByText('Options');

		userEvent.dblClick(columnName);
		userEvent.dblClick(columnOptions);

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
		fetch.mockResponseOnce(JSON.stringify(fieldTypeResponse));
		fetch.mockResponseOnce(JSON.stringify(tableViewResponseTwoItens));
		fetch.mockResponseOnce(JSON.stringify(tableViewWithId));

		const history = createMemoryHistory();
		//console.log(history);
		history.push('/abc/ebc');

		const {
			debug,
			queryByPlaceholderText,
			queryByText,
			queryAllByText,
		} = render(
			<DndProvider backend={HTML5Backend}>
				<EditTableView
					location={{
						match: {
							params: {
								dataDefinitionId: 1,
							},
							url: 'table-views',
						},
					}}
				/>
			</DndProvider>,
			{
				wrapper: (props) => (
					<Router history={history}>
						<AppContextProviderWrapper
							history={history}
							{...props}
						/>
					</Router>
				),
			}
		);

		await act(async () => {
			jest.runAllTimers();
		});

		//linhas para apagar depois que o mock do id estiver pegando ----------------
		const [columnName] = queryAllByText('Name');
		userEvent.dblClick(columnName);
		// fim ----------------------------------------------------------------

		expect(
			queryByText('drag-columns-from-the-sidebar-and-drop-here')
		).toBeFalsy();

		expect(queryAllByText('Name').length).toBe(2);
		expect(queryAllByText('Options').length).toBe(1);

		const tableName = queryByPlaceholderText('untitled-table-view');
		//descomentar linha abaixo quando o mock do id estiver pegando
		//expect(tableName.value).toBe('Name');

		fireEvent.change(tableName, {target: {value: 'My Edited Table View'}});

		fireEvent.submit(tableName);

		//debug();
	});
});
