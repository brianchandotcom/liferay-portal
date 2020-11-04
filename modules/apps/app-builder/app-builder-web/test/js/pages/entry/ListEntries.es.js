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

import '@testing-library/jest-dom/extend-expect';
import {waitForElementToBeRemoved} from '@testing-library/dom';
import {act, fireEvent, render} from '@testing-library/react';
import React from 'react';

import ListEntries from '../../../../src/main/resources/META-INF/resources/js/pages/entry/ListEntries.es';
import AppContextProviderWrapper from '../../AppContextProviderWrapper.es';
import PermissionsContextProviderWrapper from '../../PermissionsContextProviderWrapper.es';
import {ENTRY} from '../../constants.es';

const context = {
	actionsIds: [],
	appId: 1,
	basePortletURL: 'portlet_url',
	dataDefinitionId: 1,
	dataListViewId: 1,
	showFormView: true,
};

const mockNavigate = jest.fn();
const mockRenderURL = jest.fn().mockImplementation((url) => url);

window.Liferay.Util = {
	PortletURL: {createRenderURL: (...args) => mockRenderURL(...args)},
	navigate: (url) => mockNavigate(url),
};

const mockToast = jest.fn();

jest.mock('app-builder-web/js/utils/toast.es', () => ({
	__esModule: true,
	errorToast: () => mockToast(),
}));

describe('ListEntries', () => {
	it('renders with 2 entries and calls navigate after clicking on add button', async () => {
		fetch.mockResponseOnce(JSON.stringify(ENTRY.DATA_DEFINITION));
		fetch.mockResponseOnce(JSON.stringify(ENTRY.DATA_LIST_VIEW));
		fetch.mockResponseOnce(JSON.stringify(ENTRY.DATA_RECORDS(2)));

		const {container} = render(
			<AppContextProviderWrapper appContext={context}>
				<ListEntries />
			</AppContextProviderWrapper>,
			{wrapper: PermissionsContextProviderWrapper}
		);

		await waitForElementToBeRemoved(() =>
			document.querySelector('span.loading-animation')
		);

		const entries = container.querySelector('tbody').children;

		expect(entries.length).toEqual(2);

		expect(entries[0].children[0]).toHaveTextContent('Name Test 0');
		expect(entries[0].children[1]).toHaveTextContent('approved');

		expect(entries[1].children[0]).toHaveTextContent('Name Test 1');
		expect(entries[1].children[1]).toHaveTextContent('approved');

		await act(async () => {
			await fireEvent.click(
				container.querySelector('.lexicon-icon-plus')
			);
		});

		expect(mockNavigate).toHaveBeenCalledWith(context.basePortletURL);

		expect(mockRenderURL).toHaveBeenCalledWith('portlet_url', {
			backURL: 'http://localhost/#/',
			dataRecordId: 0,
			languageId: undefined,
			mvcPath: '/edit_entry.jsp',
		});
	});

	it('renders with empty state', async () => {
		fetch.mockResponseOnce(JSON.stringify(ENTRY.DATA_DEFINITION));
		fetch.mockResponseOnce(JSON.stringify(ENTRY.DATA_LIST_VIEW));
		fetch.mockResponse(JSON.stringify(ENTRY.DATA_RECORDS(0)));

		const {container} = render(
			<AppContextProviderWrapper appContext={context}>
				<ListEntries />
			</AppContextProviderWrapper>,
			{wrapper: PermissionsContextProviderWrapper}
		);

		await waitForElementToBeRemoved(() =>
			document.querySelector('span.loading-animation')
		);

		expect(
			container.querySelector('.taglib-empty-result-message-title')
				.textContent
		).toContain('there-are-no-entries-yet');

		expect(
			container.querySelector('.taglib-empty-result-message button')
				.textContent
		).toContain('new-entry');
	});

	it('shows error toast when an error happens while trying to get Data Records', async () => {
		fetch.mockResponseOnce(JSON.stringify(ENTRY.DATA_DEFINITION));
		fetch.mockResponseOnce(JSON.stringify(ENTRY.DATA_LIST_VIEW));
		fetch.mockRejectedValueOnce({});

		render(
			<AppContextProviderWrapper appContext={context}>
				<ListEntries />
			</AppContextProviderWrapper>,
			{wrapper: PermissionsContextProviderWrapper}
		);

		await waitForElementToBeRemoved(() =>
			document.querySelector('span.loading-animation')
		);

		expect(mockToast).toHaveBeenCalled();
	});

	it('shows error toast when errors happens while trying to get Data List View, Data Defition Id and Data Records', async () => {
		fetch.mockRejectedValue({});

		await act(async () => {
			await render(
				<AppContextProviderWrapper
					appContext={{
						appId: null,
					}}
				>
					<ListEntries />
				</AppContextProviderWrapper>,
				{wrapper: PermissionsContextProviderWrapper}
			);
		});

		expect(mockToast).toHaveBeenCalledTimes(3);
	});
});
