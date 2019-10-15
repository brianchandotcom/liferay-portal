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

import {cleanup, render, waitForElement} from '@testing-library/react';
import InstanceItemDetail from '../../../../src/main/resources/META-INF/resources/js/components/process-metrics/instance-list/InstanceItemDetail.es';
import {InstanceListContext} from '../../../../src/main/resources/META-INF/resources/js/components/process-metrics/instance-list/store/InstanceListStore.es';
import {MockRouter} from '../../../mock/MockRouter.es';
import React from 'react';

describe('The instance item detail should', () => {
	const baseInstance = {
		assetTitle: 'New Post',
		assetType: 'Blog',
		creatorUser: {
			name: 'User 1'
		},
		dateCreated: new Date('2019-01-03'),
		id: 1,
		taskNames: ['Update']
	};

	const getClientMock = (slaStatus, status, props) => ({
		get: jest.fn().mockResolvedValue({
			data: {
				...baseInstance,
				...props,
				slaStatus,
				status
			}
		})
	});

	afterEach(cleanup);

	test('Be rendered with empty data when no instanceId is defined', async () => {
		const {getAllByTestId} = render(
			<MockRouter
				client={getClientMock('Untracked', 'Completed', [
					{status: 'Running'}
				])}
			>
				<InstanceListContext.Provider value={{instanceId: null}}>
					<InstanceItemDetail processId="12345" />
				</InstanceListContext.Provider>
			</MockRouter>
		);

		const instanceDetailSpans = await waitForElement(() =>
			getAllByTestId('instanceDetailSpan')
		);

		expect(instanceDetailSpans[0].innerHTML).toBe('');
		expect(instanceDetailSpans[1].innerHTML).toBe('');
		expect(instanceDetailSpans[2].innerHTML).toBe('');
		expect(instanceDetailSpans[3].innerHTML).toBe('');
		expect(instanceDetailSpans[4].innerHTML).toBe('');
	});

	test('Be rendered with "OnTime" and "Completed" statuses', async () => {
		const {getAllByTestId} = render(
			<MockRouter
				client={getClientMock('OnTime', 'Completed', {
					dateCompletion: new Date('2019-01-07')
				})}
			>
				<InstanceListContext.Provider value={{instanceId: 12345}}>
					<InstanceItemDetail processId="12345" />
				</InstanceListContext.Provider>
			</MockRouter>
		);

		const instanceDetailSpans = await waitForElement(() =>
			getAllByTestId('instanceDetailSpan')
		);

		expect(instanceDetailSpans[0].innerHTML).toBe('Completed');
		expect(instanceDetailSpans[1].innerHTML).toBe('User 1');
		expect(instanceDetailSpans[2].innerHTML).toBe('Blog');
		expect(instanceDetailSpans[3].innerHTML).toBe('New Post');
	});

	test('Be rendered with "OnTime" and "Pending" statuses', async () => {
		const {getAllByTestId} = render(
			<MockRouter client={getClientMock('Overdue', 'Pending')}>
				<InstanceListContext.Provider value={{instanceId: 12345}}>
					<InstanceItemDetail processId="12345" />
				</InstanceListContext.Provider>
			</MockRouter>
		);

		const instanceDetailSpans = await waitForElement(() =>
			getAllByTestId('instanceDetailSpan')
		);

		expect(instanceDetailSpans[0].innerHTML).toBe('Pending');
		expect(instanceDetailSpans[4].innerHTML).toBe('Update');
	});

	test('Be rendered with "Overdue" and "Pending" statuses', async () => {
		const {getAllByTestId} = render(
			<MockRouter client={getClientMock('Overdue', 'Pending')}>
				<InstanceListContext.Provider value={{instanceId: 12345}}>
					<InstanceItemDetail processId="12345" />
				</InstanceListContext.Provider>
			</MockRouter>
		);

		const instanceDetailSpans = await waitForElement(() =>
			getAllByTestId('instanceDetailSpan')
		);

		expect(instanceDetailSpans[0].innerHTML).toBe('Pending');
	});

	test('Be rendered with "Untracked" and "Paused" statuses', async () => {
		const {getAllByTestId} = render(
			<MockRouter client={getClientMock('Untracked', 'Paused')}>
				<InstanceListContext.Provider value={{instanceId: 12345}}>
					<InstanceItemDetail processId="12345" />
				</InstanceListContext.Provider>
			</MockRouter>
		);

		const instanceDetailSpans = await waitForElement(() =>
			getAllByTestId('instanceDetailSpan')
		);

		expect(instanceDetailSpans[0].innerHTML).toBe('Paused');
	});

	test('Be rendered with "Untracked", "Completed", and "Running" statuses', async () => {
		const {getAllByTestId} = render(
			<MockRouter
				client={getClientMock('Untracked', 'Completed', {
					slaResults: [
						{
							status: 'Running'
						}
					]
				})}
			>
				<InstanceListContext.Provider value={{instanceId: 12345}}>
					<InstanceItemDetail processId="12345" />
				</InstanceListContext.Provider>
			</MockRouter>
		);

		const instanceDetailSpans = await waitForElement(() =>
			getAllByTestId('instanceDetailSpan')
		);

		expect(instanceDetailSpans[0].innerHTML).toBe('Completed');
	});

	test('Be rendered with "Untracked", "Completed", and "Stopped" statuses', async () => {
		const {getAllByTestId} = render(
			<MockRouter
				client={getClientMock('Untracked', 'Completed', {
					slaResults: [
						{
							status: 'Stopped'
						}
					]
				})}
			>
				<InstanceListContext.Provider value={{instanceId: 12345}}>
					<InstanceItemDetail processId="12345" />
				</InstanceListContext.Provider>
			</MockRouter>
		);

		const instanceDetailSpans = await waitForElement(() =>
			getAllByTestId('instanceDetailSpan')
		);

		expect(instanceDetailSpans[0].innerHTML).toBe('Completed');
	});
});
