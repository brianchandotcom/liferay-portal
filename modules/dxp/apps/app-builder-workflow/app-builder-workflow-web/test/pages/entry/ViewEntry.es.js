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
import {render} from '@testing-library/react';
import React from 'react';

import ViewEntry from '../../../src/main/resources/META-INF/resources/js/pages/entry/ViewEntry.es';
import AppContextProviderWrapper from '../../AppContextProviderWrapper.es';
import PermissionsContextProviderWrapper from '../../PermissionsContextProviderWrapper.es';
import {ENTRY} from '../../constants.es';

const context = {
	appId: 1,
	dataDefinitionId: 1,
	dataLayoutId: 1,
	showFormView: true,
};

const mockToast = jest.fn();
jest.mock('app-builder-web/js/utils/toast.es', () => ({
	__esModule: true,
	errorToast: () => mockToast(),
}));

describe('ViewEntry', () => {
	it('renders workflow info correctly with pending entry', async () => {
		fetch.mockResponseOnce(JSON.stringify(ENTRY.DATA_DEFINITION));
		fetch.mockResponseOnce(JSON.stringify(ENTRY.DATA_RECORDS(1)));
		fetch.mockResponseOnce(JSON.stringify(ENTRY.DATA_RECORD_APPS(1)));
		fetch.mockResponseOnce(
			JSON.stringify({
				items: [
					{
						assignees: [
							{id: -1, name: 'Unassigned', reviewer: true},
						],
						classPK: 0,
						completed: false,
						taskNames: ['Review'],
					},
				],
				totalCount: 4,
			})
		);
		fetch.mockResponseOnce(JSON.stringify(ENTRY.DATA_LAYOUT));

		const {container, getAllByRole} = render(
			<AppContextProviderWrapper appContext={context}>
				<ViewEntry match={{params: {entryIndex: 1}}} />
			</AppContextProviderWrapper>,
			{wrapper: PermissionsContextProviderWrapper}
		);

		await waitForElementToBeRemoved(() =>
			document.querySelector('span.loading-animation')
		);

		expect(
			container.querySelector('.data-record-field-preview')
		).toHaveTextContent('Name Test 0');

		const infoItems = container.querySelectorAll('.info-item');

		expect(infoItems.length).toBe(4);
		expect(infoItems[0]).toHaveTextContent('status: pending');
		expect(infoItems[1]).toHaveTextContent('step: Review');
		expect(infoItems[2]).toHaveTextContent('assignee: Administrator');
		expect(infoItems[3]).toHaveTextContent('version: 1.0');

		const buttons = getAllByRole('button');

		expect(buttons.length).toBe(5);
		expect(buttons[0].title).toBe('previous-entry');
		expect(buttons[1].title).toBe('next-entry');
		expect(buttons[2].title).toBe('delete');
		expect(buttons[3].title).toBe('edit');
		expect(buttons[4].title).toBe('assign-to');
	});

	it('renders workflow info correctly with completed entry', async () => {
		fetch.mockResponseOnce(JSON.stringify(ENTRY.DATA_DEFINITION));
		fetch.mockResponseOnce(JSON.stringify(ENTRY.DATA_RECORDS(1)));
		fetch.mockResponseOnce(JSON.stringify(ENTRY.DATA_RECORD_APPS(1)));
		fetch.mockResponseOnce(
			JSON.stringify({
				items: [
					{
						classPK: 0,
						completed: true,
					},
				],
				totalCount: 1,
			})
		);
		fetch.mockResponseOnce(JSON.stringify(ENTRY.DATA_LAYOUT));

		const {container} = render(
			<AppContextProviderWrapper appContext={context}>
				<ViewEntry match={{params: {entryIndex: 1}}} />
			</AppContextProviderWrapper>,
			{wrapper: PermissionsContextProviderWrapper}
		);

		await waitForElementToBeRemoved(() =>
			document.querySelector('span.loading-animation')
		);

		const infoItems = container.querySelectorAll('.info-item');

		expect(infoItems.length).toBe(4);
		expect(infoItems[0]).toHaveTextContent('status: completed');
		expect(infoItems[1]).toHaveTextContent('step: --');
		expect(infoItems[2]).toHaveTextContent('assignee: --');
		expect(infoItems[3]).toHaveTextContent('version: 1.0');
	});

	it('renders workflow info correctly without task names', async () => {
		fetch.mockResponseOnce(JSON.stringify(ENTRY.DATA_DEFINITION));
		fetch.mockResponseOnce(JSON.stringify(ENTRY.DATA_RECORDS(1)));
		fetch.mockResponseOnce(JSON.stringify(ENTRY.DATA_RECORD_APPS(1)));
		fetch.mockResponseOnce(
			JSON.stringify({
				items: [
					{
						assignees: [
							{id: -1, name: 'Unassigned', reviewer: true},
						],
						classPK: 0,
						completed: false,
					},
				],
				totalCount: 1,
			})
		);
		fetch.mockResponseOnce(JSON.stringify(ENTRY.DATA_LAYOUT));

		const {container} = render(
			<AppContextProviderWrapper appContext={context}>
				<ViewEntry match={{params: {entryIndex: 1}}} />
			</AppContextProviderWrapper>,
			{wrapper: PermissionsContextProviderWrapper}
		);

		await waitForElementToBeRemoved(() =>
			document.querySelector('span.loading-animation')
		);

		const infoItems = container.querySelectorAll('.info-item');

		expect(infoItems.length).toBe(4);
		expect(infoItems[0]).toHaveTextContent('status: pending');
		expect(infoItems[1]).toHaveTextContent('step: --');
		expect(infoItems[2]).toHaveTextContent('assignee: --');
		expect(infoItems[3]).toHaveTextContent('version: 1.0');
	});

	it('shows error toast when an error happens while trying to get Data Records', async () => {
		fetch.mockResponseOnce(JSON.stringify(ENTRY.DATA_DEFINITION));
		fetch.mockRejectedValue({});

		render(
			<AppContextProviderWrapper appContext={context}>
				<ViewEntry match={{params: {entryIndex: 1}}} />
			</AppContextProviderWrapper>,
			{wrapper: PermissionsContextProviderWrapper}
		);

		await waitForElementToBeRemoved(() =>
			document.querySelector('span.loading-animation')
		);

		expect(mockToast).toHaveBeenCalledTimes(1);
	});

	it('shows error toast when an error happens while trying to get instances', async () => {
		fetch.mockResponseOnce(JSON.stringify(ENTRY.DATA_DEFINITION));
		fetch.mockResponseOnce(JSON.stringify(ENTRY.DATA_RECORDS(1)));
		fetch.mockResponseOnce(JSON.stringify({items: []}));
		fetch.mockRejectedValueOnce({});
		fetch.mockResponseOnce(JSON.stringify(ENTRY.DATA_LAYOUT));

		render(
			<AppContextProviderWrapper appContext={context}>
				<ViewEntry match={{params: {entryIndex: 1}}} />
			</AppContextProviderWrapper>,
			{wrapper: PermissionsContextProviderWrapper}
		);

		await waitForElementToBeRemoved(() =>
			document.querySelector('span.loading-animation')
		);

		expect(mockToast).toHaveBeenCalledTimes(2);
	});
});
