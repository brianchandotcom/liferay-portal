/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import '@testing-library/jest-dom/extend-expect';
import {waitForElementToBeRemoved} from '@testing-library/dom';
import {act, fireEvent, render} from '@testing-library/react';
import React from 'react';

import ListEntries from '../../../src/main/resources/META-INF/resources/js/pages/entry/ListEntries.es';
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

const instances = {
	items: [
		{
			assignees: [{id: -1, name: 'Unassigned'}],
			classPK: 0,
			completed: false,
			taskNames: ['Review'],
		},
		{
			assignees: [{name: 'User 2'}],
			classPK: 1,
			completed: false,
			taskNames: ['Update'],
		},
		{
			assignees: [{name: 'Unassigned'}],
			classPK: 2,
			completed: false,
			taskNames: ['Awaiting Review'],
		},
		{
			classPK: 3,
			completed: true,
		},
	],
	totalCount: 4,
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
	it('renders with 5 entries and calls navigate after clicking on add button', async () => {
		fetch.mockResponseOnce(JSON.stringify(ENTRY.APP_WORKFLOW));
		fetch.mockResponseOnce(JSON.stringify(ENTRY.DATA_DEFINITION));
		fetch.mockResponseOnce(JSON.stringify(ENTRY.DATA_LIST_VIEW));
		fetch.mockResponseOnce(JSON.stringify(ENTRY.DATA_RECORDS(5)));
		fetch.mockResponseOnce(JSON.stringify(instances));
		fetch.mockResponseOnce(JSON.stringify(ENTRY.DATA_RECORD_APPS(1)));

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

		expect(entries.length).toEqual(5);

		expect(entries[0].children[0]).toHaveTextContent('Name Test 0');
		expect(entries[0].children[1]).toHaveTextContent('pending');
		expect(entries[0].children[2]).toHaveTextContent('Review');
		expect(entries[0].children[3]).toHaveTextContent('Administrator');

		expect(entries[1].children[0]).toHaveTextContent('Name Test 1');
		expect(entries[1].children[1]).toHaveTextContent('pending');
		expect(entries[1].children[2]).toHaveTextContent('Update');
		expect(entries[1].children[3]).toHaveTextContent('User 2');

		expect(entries[2].children[0]).toHaveTextContent('Name Test 2');
		expect(entries[2].children[1]).toHaveTextContent('pending');
		expect(entries[2].children[2]).toHaveTextContent('Awaiting Review');
		expect(entries[2].children[3]).toHaveTextContent('Unassigned');

		expect(entries[3].children[0]).toHaveTextContent('Name Test 3');
		expect(entries[3].children[1]).toHaveTextContent('completed');
		expect(entries[3].children[2]).toHaveTextContent('--');
		expect(entries[3].children[3]).toHaveTextContent('--');

		expect(entries[4].children[0]).toHaveTextContent('Name Test 4');
		expect(entries[4].children[1]).toHaveTextContent('--');
		expect(entries[4].children[2]).toHaveTextContent('--');
		expect(entries[4].children[3]).toHaveTextContent('--');

		await act(async () => {
			await fireEvent.click(
				container.querySelector('.lexicon-icon-plus')
			);
		});

		expect(mockNavigate).toHaveBeenCalledWith(context.basePortletURL);

		expect(mockRenderURL).toHaveBeenCalledWith('portlet_url', {
			dataRecordId: 0,
			mvcPath: '/edit_entry.jsp',
		});
	});

	it('renders with empty state', async () => {
		fetch.mockResponseOnce(JSON.stringify(ENTRY.APP_WORKFLOW));
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
		fetch.mockResponseOnce(JSON.stringify(ENTRY.APP_WORKFLOW));
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

	it('shows error toast when an error happens while trying to get App Workflow', async () => {
		fetch.mockRejectedValueOnce({});
		fetch.mockResponseOnce(JSON.stringify(ENTRY.DATA_DEFINITION));
		fetch.mockResponseOnce(JSON.stringify(ENTRY.DATA_LIST_VIEW));
		fetch.mockResponse(JSON.stringify(ENTRY.DATA_RECORDS(0)));

		await act(async () => {
			await render(
				<AppContextProviderWrapper appContext={context}>
					<ListEntries />
				</AppContextProviderWrapper>,
				{wrapper: PermissionsContextProviderWrapper}
			);
		});

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
