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
import HTML5Backend from 'react-dnd-html5-backend';
import {HashRouter} from 'react-router-dom';

import {AppContextProvider} from '../../../../src/main/resources/META-INF/resources/js/AppContext.es';
import EditTableView from '../../../../src/main/resources/META-INF/resources/js/pages/table-view/EditTableView.es';
import * as time from '../../../../src/main/resources/META-INF/resources/js/utils/time.es';
import * as toast from '../../../../src/main/resources/META-INF/resources/js/utils/toast.es';
import { fieldTypeResponse, tableViewResponseOneItem, tableViewResponseTwoItens } from '../../mock';

describe('EditTableView', () => {
	const EditTableViewWithRouter = ({history = createMemoryHistory()}) => (
		<AppContextProvider value={{}}>
			<div className="tools-control-group">
				<div className="control-menu-level-1-heading" />
			</div>
			<HashRouter>
				<EditTableView history={history} />
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

	it('renders', async () => {
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

		// console.log(saveButton);

		fireEvent.change(tableName, {target: {value: 'Players'}});

		expect(tableName.value).toBe('Players');

		// debug();

		//fazer um teste para ver se o disabled do botão está falso

		//salvar

		//debug();
	});

	it('renders with two itens, search for one field and set filters', async () => {
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

		// debug();
	});
});
