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
import * as toast from 'data-engine-js-components-web/js/utils/toast.es';
import {createMemoryHistory} from 'history';
import React from 'react';

import ListNativeObjects from '../../../../src/main/resources/META-INF/resources/js/pages/native-object/ListNativeObjects.es';
import * as time from '../../../../src/main/resources/META-INF/resources/js/utils/time.es';
import AppContextProviderWrapper from '../../AppContextProviderWrapper.es';
import {RESPONSES} from '../../constants.es';

describe('ListNativeObjects', () => {
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
		fetch.mockResponseOnce(JSON.stringify(RESPONSES.ONE_ITEM));

		const {asFragment} = render(<ListNativeObjects />, {
			wrapper: AppContextProviderWrapper,
		});

		await waitForElementToBeRemoved(() =>
			document.querySelector('span.loading-animation')
		);

		expect(asFragment()).toMatchSnapshot();
	});

	it('renders with one item and updates its permissions', async () => {
		const permissionItem = {
			availableLanguages: ['en-US'],
			dateCreated: '2020-03-05T20:06:51Z',
			dateModified: '2020-03-05T20:06:51Z',
			description: '',
			id: 30302,
			name: 'Account Administrator',
			roleType: 'regular',
		};

		const permissionResponse = {
			...RESPONSES.ONE_ITEM,
			actions: {},
			items: [permissionItem],
		};

		fetch
			.mockResponseOnce(JSON.stringify(RESPONSES.ONE_ITEM))
			.mockResponseOnce(
				JSON.stringify({
					dataDefinitionId: 38408,
					dataRecordCollectionKey: '38407',
					description: {},
					id: 38410,
					name: {
						en_US: 'Name',
					},
					siteId: 20129,
				})
			)
			.mockResponseOnce(JSON.stringify(permissionResponse))
			.mockResponse(JSON.stringify({}));

		const {queryAllByText, queryByText} = render(<ListNativeObjects />, {
			wrapper: AppContextProviderWrapper,
		});

		await waitForElementToBeRemoved(() =>
			document.querySelector('span.loading-animation')
		);

		expect(spyFromNow).toHaveBeenCalled();

		expect(fetch.mock.calls.length).toBe(1);
		expect(queryAllByText('Item 1').length).toBe(1);

		const permission = queryByText('app-permissions');

		await act(async () => {
			fireEvent.click(permission);
		});

		await act(async () => {
			jest.runAllTimers();
		});

		expect(fetch.mock.calls.length).toBe(4);
		expect(queryByText(permissionItem.name)).toBeTruthy();

		const checkboxes = document.querySelectorAll(
			'.modal-body input[type="checkbox"]'
		);

		expect(checkboxes.length).toBe(4);

		const [addPermission] = checkboxes;

		expect(addPermission.checked).toBe(false);

		fireEvent.click(addPermission);

		expect(addPermission.checked).toBe(true);

		const save = queryByText('save');

		await act(async () => {
			fireEvent.click(save);
		});

		expect(fetch.mock.calls.length).toBe(6);
		expect(spySuccessToast.mock.calls.length).toBe(1);
	});

	it('renders with data and click on actions', async () => {
		fetch.mockResponseOnce(JSON.stringify(RESPONSES.ONE_ITEM));

		const history = createMemoryHistory();

		const {baseElement} = render(<ListNativeObjects />, {
			wrapper: (props) => (
				<AppContextProviderWrapper history={history} {...props} />
			),
		});

		expect(history.length).toBe(1);
		expect(history.location.pathname).toBe('/');

		await waitForElementToBeRemoved(() =>
			baseElement.querySelector('span.loading-animation')
		);

		expect(spyFromNow).toHaveBeenCalled();

		const dropDownMenu = baseElement.querySelectorAll('.dropdown-menu');
		const actions = dropDownMenu[1].querySelectorAll('.dropdown-item');

		expect(actions.length).toBe(4);
		expect(history.length).toBe(1);
		expect(history.location.pathname).toBe('/');

		const [formView, tableView, apps] = actions;

		fireEvent.click(formView);

		expect(history.length).toBe(2);
		expect(history.location.pathname).toBe('/native-object/1/form-views');

		fireEvent.click(tableView);

		expect(history.length).toBe(3);
		expect(history.location.pathname).toBe('/native-object/1/table-views');

		fireEvent.click(apps);

		expect(history.length).toBe(4);
		expect(history.location.pathname).toBe('/native-object/1/apps');
	});
});
