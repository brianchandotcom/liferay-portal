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
import React from 'react';

import ListApps from '../../../../src/main/resources/META-INF/resources/js/pages/apps/ListApps.es';
import * as time from '../../../../src/main/resources/META-INF/resources/js/utils/time.es';
import AppContextProviderWrapper from '../../AppContextProviderWrapper.es';
import {RESPONSES} from '../../constants.es';

const DRODOWN_VALUES = {
	items: [
		{
			id: 37568,
			name: {
				'en-US': 'test',
			},
		},
	],
};

describe('ListApps', () => {
	beforeEach(() => {
		jest.spyOn(time, 'fromNow').mockImplementation(() => 'months ago');
	});

	afterEach(() => {
		cleanup();
		jest.restoreAllMocks();
	});

	it('renders', async () => {
		fetch.mockResponseOnce(JSON.stringify(RESPONSES.ONE_ITEM));
		fetch.mockResponse(JSON.stringify(DRODOWN_VALUES));

		const {asFragment} = render(
			<ListApps match={{params: {dataDefinitionId: ''}, url: '/'}} />,
			{wrapper: AppContextProviderWrapper}
		);

		await waitForElementToBeRemoved(() =>
			document.querySelector('span.loading-animation')
		);

		expect(asFragment()).toMatchSnapshot();
	});

	it('renders with dataDefinitionId and 5 apps in the list', async () => {
		fetch.mockResponseOnce(JSON.stringify(RESPONSES.MANY_ITEMS(5)));
		fetch.mockResponse(JSON.stringify(DRODOWN_VALUES));

		const {container} = render(
			<ListApps match={{params: {dataDefinitionId: '1'}, url: '/'}} />,
			{wrapper: AppContextProviderWrapper}
		);

		await waitForElementToBeRemoved(() =>
			document.querySelector('span.loading-animation')
		);

		expect(container.querySelector('tbody').children.length).toEqual(5);
	});

	it('renders with no dataDefinitionId, opens a new app popover and lists 5 apps', async () => {
		fetch.mockResponseOnce(JSON.stringify(RESPONSES.MANY_ITEMS(5)));
		fetch.mockResponse(JSON.stringify(DRODOWN_VALUES));

		const {container} = render(
			<ListApps match={{params: {dataDefinitionId: ''}, url: '/'}} />,
			{wrapper: AppContextProviderWrapper}
		);

		await waitForElementToBeRemoved(() =>
			document.querySelector('span.loading-animation')
		);

		expect(container.querySelector('tbody').children.length).toEqual(5);

		const newAppButton = document.querySelector(
			'.nav-btn.nav-btn-monospaced.btn.btn-monospaced.btn-primary'
		);

		fireEvent.click(newAppButton);

		expect(
			document.querySelector('.popover.apps-popover.mw-100')
		).toBeTruthy();

		fireEvent.click(newAppButton);

		expect(document.querySelector('.popover.apps-popover.mw-100.hide'));

		await fireEvent.click(newAppButton);

		await fireEvent.click(
			document.querySelector('.d-flex.justify-content-end').children[0]
		);

		expect(document.querySelector('.popover.apps-popover.mw-100.hide'));
	});

	it('renders with empty state', async () => {
		fetch.mockResponseOnce(JSON.stringify(RESPONSES.NO_ITEMS));
		fetch.mockResponse(JSON.stringify(DRODOWN_VALUES));

		const {container} = render(
			<ListApps match={{params: {dataDefinitionId: ''}, url: '/'}} />,
			{wrapper: AppContextProviderWrapper}
		);

		await waitForElementToBeRemoved(() =>
			document.querySelector('span.loading-animation')
		);

		expect(
			container.querySelector('.taglib-empty-result-message-title')
				.textContent
		).toEqual('there-are-no-apps-yet');
	});
});
