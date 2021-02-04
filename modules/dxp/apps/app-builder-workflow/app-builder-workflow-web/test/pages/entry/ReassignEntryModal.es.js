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

import ReassignEntryModal from '../../../src/main/resources/META-INF/resources/js/pages/entry/ReassignEntryModal.es';
import AppContextProviderWrapper from '../../AppContextProviderWrapper.es';
import PermissionsContextProviderWrapper from '../../PermissionsContextProviderWrapper.es';

const assignableUsers = {
	items: [
		{
			id: 20126,
			name: 'Test Test',
		},
		{
			id: 37828,
			name: 'Test 2',
		},
	],
};

const context = {
	actionsIds: [],
	appId: 37946,
	dataDefinitionId: 37811,
	dataListViewId: 1,
	showFormView: true,
};

const mockRefetch = jest.fn();

const workflowTasks = {
	items: [
		{
			completed: false,
			dateCreated: '2020-08-19T13:57:22Z',
			description: '',
			id: 37955,
			label: 'Step 1',
			name: 'Step 1',
			objectReviewed: {
				assetTitle: 'Test',
				assetType: 'App Builder Entry',
				id: 37946,
			},
			workflowDefinitionId: 37811,
			workflowDefinitionName: '37800',
			workflowDefinitionVersion: '1',
			workflowInstanceId: 37949,
		},
	],
};

const mockAddItem = jest.fn().mockResolvedValueOnce({});

const mockGetItem = jest
	.fn()
	.mockResolvedValueOnce(workflowTasks)
	.mockResolvedValueOnce(assignableUsers);

jest.mock('data-engine-js-components-web/js/utils/client.es', () => ({
	addItem: () => mockAddItem(),
	getItem: () => mockGetItem(),
}));

const mockToast = jest.fn();

jest.mock('data-engine-js-components-web/js/utils/toast.es', () => ({
	__esModule: true,
	successToast: (message) => mockToast(message),
}));

describe('ReassignEntryModal', () => {
	it('renders correctly', async () => {
		jest.useFakeTimers();

		const {getByText} = render(
			<AppContextProviderWrapper appContext={context}>
				<ReassignEntryModal
					entry={{instanceId: 123}}
					onCloseModal={() => {}}
					refetch={mockRefetch}
				/>
			</AppContextProviderWrapper>,
			{wrapper: PermissionsContextProviderWrapper}
		);

		act(() => {
			jest.runAllTimers();
		});

		await waitForElementToBeRemoved(() =>
			document.querySelector('span.loading-animation')
		);

		expect(getByText('assign-to')).toBeTruthy();
		expect(getByText('new-assignee')).toBeTruthy();
		expect(getByText('comment')).toBeTruthy();
		expect(getByText('Test Test')).toBeTruthy();
		expect(getByText('Test 2')).toBeTruthy();

		await fireEvent.click(getByText('Test Test'));

		await act(async () => {
			await fireEvent.click(getByText('done'));
		});

		expect(mockToast).toHaveBeenCalledWith(
			'this-entry-has-been-reassigned'
		);
		expect(mockRefetch).toHaveBeenCalled();
	});
});
