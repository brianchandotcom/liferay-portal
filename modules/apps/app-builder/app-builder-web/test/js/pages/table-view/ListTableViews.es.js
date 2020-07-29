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
import {cleanup, fireEvent, render} from '@testing-library/react';
import {createMemoryHistory} from 'history';
import React from 'react';

import ListTableViews from '../../../../src/main/resources/META-INF/resources/js/pages/table-view/ListTableViews.es';
import * as time from '../../../../src/main/resources/META-INF/resources/js/utils/time.es';
import AppContextProviderWrapper from '../../AppContextProviderWrapper.es';
import {DATA_DEFINITION_RESPONSES, RESPONSES} from '../../constants.es';

describe('ListTableViews', () => {
	let spyFromNow;

	beforeEach(() => {
		spyFromNow = jest
			.spyOn(time, 'fromNow')
			.mockImplementation(() => 'months ago');
	});

	afterEach(() => {
		cleanup();
		jest.restoreAllMocks();
	});

	it('renders', async () => {
		fetch
			.mockResponseOnce(JSON.stringify(RESPONSES.ONE_ITEM))
			.mockResponseOnce(
				JSON.stringify(DATA_DEFINITION_RESPONSES.ONE_ITEM)
			);

		const {asFragment} = render(
			<ListTableViews
				match={{
					params: {
						dataDefinitionId: 1,
					},
					url: 'table-views',
				}}
			/>,
			{wrapper: AppContextProviderWrapper}
		);

		await waitForElementToBeRemoved(() =>
			document.querySelector('span.loading-animation')
		);

		expect(asFragment()).toMatchSnapshot();
	});

	it('renders with empty state', async () => {
		fetch
			.mockResponseOnce(JSON.stringify(RESPONSES.NO_ITEMS))
			.mockResponseOnce(
				JSON.stringify(DATA_DEFINITION_RESPONSES.ONE_ITEM)
			);

		const {queryByText} = render(
			<ListTableViews
				match={{
					params: {
						dataDefinitionId: 1,
					},
					url: 'table-views',
				}}
			/>,
			{wrapper: AppContextProviderWrapper}
		);

		await waitForElementToBeRemoved(() =>
			document.querySelector('span.loading-animation')
		);

		expect(
			queryByText(
				'create-one-or-more-tables-to-display-the-data-held-in-your-data-object'
			)
		).toBeTruthy();

		expect(queryByText('there-are-no-table-views-yet')).toBeTruthy();
		expect(document.querySelector('.nav-item > a').href).toContain(
			'#/table-views/add'
		);

		expect(fetch.mock.calls.length).toEqual(2);
	});

	it('renders with data and click on actions', async () => {
		fetch
			.mockResponseOnce(JSON.stringify(RESPONSES.ONE_ITEM))
			.mockResponseOnce(
				JSON.stringify(DATA_DEFINITION_RESPONSES.ONE_ITEM)
			);

		const history = createMemoryHistory();

		const {baseElement} = render(
			<ListTableViews
				match={{
					params: {
						dataDefinitionId: 1,
					},
					url: 'table-views',
				}}
			/>,
			{
				wrapper: (props) => (
					<AppContextProviderWrapper history={history} {...props} />
				),
			}
		);

		expect(history.length).toBe(1);
		expect(history.location.pathname).toBe('/');

		await waitForElementToBeRemoved(() =>
			baseElement.querySelector('span.loading-animation')
		);

		expect(spyFromNow).toHaveBeenCalled();

		const dropDownMenu = baseElement.querySelectorAll('.dropdown-menu');
		const actions = dropDownMenu[1].querySelectorAll('.dropdown-item');

		expect(actions.length).toBe(2);
		expect(history.length).toBe(1);
		expect(history.location.pathname).toBe('/');

		const [edit] = actions;

		fireEvent.click(edit);
		expect(history.length).toBe(2);
		expect(history.location.pathname).toBe('/table-views/1');
	});
});
