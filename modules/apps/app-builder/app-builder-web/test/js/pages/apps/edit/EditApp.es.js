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
import React from 'react';

import EditApp from '../../../../../src/main/resources/META-INF/resources/js/pages/apps/edit/EditApp.es';
import * as toast from '../../../../../src/main/resources/META-INF/resources/js/utils/toast.es';
import AppContextProviderWrapper from '../../../AppContextProviderWrapper.es';
import Router from '../../../RouterWrapper.es';
import {RESPONSES} from '../../../constants.es';

const defaultLanguageId = 'en_US';
const availableLanguageIds = [defaultLanguageId];
const rows = 2;
const items = RESPONSES.MANY_ITEMS(rows);
const titleValue = 'app-builder-app';

let spySuccessToast;
let spyErrorToast;

describe('EditApp', () => {
	beforeEach(() => {
		cleanup();
		spySuccessToast = jest
			.spyOn(toast, 'successToast')
			.mockImplementation(() => {});
		spyErrorToast = jest
			.spyOn(toast, 'errorToast')
			.mockImplementation(() => {});
	});

	afterEach(() => {
		jest.restoreAllMocks();
		cleanup();
	});

	it('renders', async () => {
		fetch.mockResponseOnce(JSON.stringify(RESPONSES.NO_ITEMS));
		const {asFragment} = render(
			<Router>
				<EditApp
					availableLanguageIds={availableLanguageIds}
					defaultLanguageId={defaultLanguageId}
				/>
			</Router>,
			{
				wrapper: AppContextProviderWrapper,
			}
		);

		await waitForElementToBeRemoved(() =>
			document.querySelector('span.loading-animation')
		);
		expect(asFragment()).toMatchSnapshot();
	});

	it('create an app standalone with success', async () => {
		fetch
			.mockResponseOnce(JSON.stringify(items))
			.mockResponseOnce(JSON.stringify(items))
			.mockResponseOnce(JSON.stringify(items))
			.mockResponse(JSON.stringify());

		const {
			container,
			queryAllByRole,
			queryByPlaceholderText,
			queryByRole,
			queryByText,
		} = render(
			<Router>
				<EditApp
					availableLanguageIds={availableLanguageIds}
					defaultLanguageId={defaultLanguageId}
				/>
			</Router>,
			{
				wrapper: AppContextProviderWrapper,
			}
		);

		await waitForElementToBeRemoved(() =>
			document.querySelector('span.loading-animation')
		);

		expect(
			container.querySelector('.multi-step-item.active').textContent
		).toBe('1');

		const title = queryByPlaceholderText('untitled-app');
		const formViewTable = queryByRole('table');
		const formViewRows = formViewTable.querySelectorAll('tbody tr');
		let next = queryByText('next');

		expect(fetch.mock.calls.length).toBe(1);
		expect(formViewRows.length).toBe(rows);
		expect(next.disabled).toBe(true);
		expect(title.value).toBe('');

		fireEvent.click(formViewRows[0]);
		fireEvent.change(title, {target: {value: titleValue}});

		expect(title.value).toBe(titleValue);
		expect(next.disabled).toBe(false);

		await act(async () => {
			await fireEvent.click(next);
		});

		expect(
			container.querySelector('.multi-step-item.active').textContent
		).toBe('2');

		expect(fetch.mock.calls.length).toBe(2);

		const tableViewTable = queryByRole('table');
		const tableViewRows = tableViewTable.querySelectorAll('tbody tr');

		next = queryByText('next');

		expect(tableViewRows.length).toBe(rows);
		expect(next.disabled).toBe(true);

		fireEvent.click(tableViewRows[0]);

		expect(next.disabled).toBe(false);

		await act(async () => {
			await fireEvent.click(next);
		});

		expect(
			container.querySelector('.multi-step-item.active').textContent
		).toBe('3');

		const workflowProcessesTable = queryByRole('table');
		const workflowProcessesRows = workflowProcessesTable.querySelectorAll(
			'tbody tr'
		);

		fireEvent.click(workflowProcessesRows[1]);

		next = queryByText('next');

		await act(async () => {
			await fireEvent.click(next);
		});

		expect(
			container.querySelector('.multi-step-item.active').textContent
		).toBe('4');

		const deploy = queryByText('deploy');

		expect(deploy.disabled).toBe(true);

		const [, standalone, productMenu] = queryAllByRole('checkbox');

		expect(productMenu.checked).toBe(false);

		fireEvent.click(productMenu);

		expect(productMenu.checked).toBe(true);

		fireEvent.click(productMenu);

		expect(productMenu.checked).toBe(false);

		fireEvent.click(standalone);

		expect(standalone.checked).toBe(true);

		expect(deploy.disabled).toBe(false);

		await act(async () => {
			await fireEvent.click(deploy);
		});

		const {appDeployments, dataLayoutId, dataListViewId} = JSON.parse(
			fetch.mock.calls[4][1].body
		);
		const itemsId = items.items[0].id;

		expect(spySuccessToast.mock.calls.length).toBe(1);
		expect(fetch.mock.calls.length).toBe(5);

		expect(appDeployments[0]).toStrictEqual({
			settings: {},
			type: 'standalone',
		});
		expect({dataLayoutId, dataListViewId}).toStrictEqual({
			dataLayoutId: itemsId,
			dataListViewId: itemsId,
		});
	});

	it('create an app standalone with error', async () => {
		fetch
			.mockResponseOnce(JSON.stringify(items))
			.mockResponseOnce(JSON.stringify(items))
			.mockResponseOnce(JSON.stringify(items))
			.mockRejectOnce('Bad request');

		const {
			queryAllByRole,
			queryByPlaceholderText,
			queryByRole,
			queryByText,
		} = render(
			<Router>
				<EditApp
					availableLanguageIds={availableLanguageIds}
					defaultLanguageId={defaultLanguageId}
				/>
			</Router>,
			{
				wrapper: AppContextProviderWrapper,
			}
		);

		await waitForElementToBeRemoved(() =>
			document.querySelector('span.loading-animation')
		);

		const formViewRows = queryByRole('table').querySelectorAll('tbody tr');
		const title = queryByPlaceholderText('untitled-app');
		const search = queryByPlaceholderText('search...');
		let next = queryByText('next');

		expect(fetch.mock.calls.length).toBe(1);
		expect(next.disabled).toBe(true);

		fireEvent.click(formViewRows[1]);
		fireEvent.change(title, {target: {value: titleValue}});

		fireEvent.change(search, {target: {value: 'no-items'}});
		expect(queryByText('no-results-were-found')).toBeTruthy();

		expect(title.value).toBe(titleValue);

		await act(async () => {
			await fireEvent.click(next);
		});

		expect(fetch.mock.calls.length).toBe(2);

		const tableViewTable = queryByRole('table');
		const tableViewRows = tableViewTable.querySelectorAll('tbody tr');

		next = queryByText('next');

		fireEvent.click(tableViewRows[1]);

		await act(async () => {
			await fireEvent.click(next);
		});

		next = queryByText('next');

		await act(async () => {
			await fireEvent.click(next);
		});

		const deploy = queryByText('deploy');

		const [, standalone] = queryAllByRole('checkbox');

		fireEvent.click(standalone);

		await act(async () => {
			await fireEvent.click(deploy);
		});

		const {dataLayoutId, dataListViewId} = JSON.parse(
			fetch.mock.calls[3][1].body
		);
		const itemsId = items.items[1].id;

		expect(spyErrorToast.mock.calls.length).toBe(1);
		expect(fetch.mock.calls.length).toBe(4);

		expect({dataLayoutId, dataListViewId}).toStrictEqual({
			dataLayoutId: itemsId,
			dataListViewId: itemsId,
		});
	});
});
