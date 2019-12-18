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

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import {ReassignTaskModal} from '../../../../src/main/resources/META-INF/resources/js/components/instance-list-page/modal/InstanceListPageReassignTaskModal.es';
import {MockRouter} from '../../../mock/MockRouter.es';

describe('The ReassignTaskModalTable component should', () => {
	afterEach(() => cleanup);
	const clientMock = {
		get: jest
			.fn()
			.mockResolvedValue({data: {items: [{id: 1, name: 'Test'}]}})
	};
	const data = {
		items: [
			{
				assigneePerson: {
					additionalName: '',
					contentType: 'UserAccount',
					familyName: 'Test',
					givenName: 'Test',
					id: 20124,
					name: 'Test Test',
					profileURL: '/web/test'
				},
				assigneeRoles: [],
				completed: true,
				dateCompletion: '2019-12-10T17:45:38Z',
				dateCreated: '2019-12-10T17:44:45Z',
				definitionId: 38902,
				definitionName: 'Single Approver',
				definitionVersion: '',
				description: '',
				id: 40336,
				instanceId: 40330,
				name: 'review',
				objectReviewed: {
					id: 40324,
					resourceType: 'BlogPosting'
				}
			}
		]
	};

	const setReassignMock = jest.fn();

	test('Render with statuses Completed and Overdue', () => {
		const {getByTestId} = render(
			<MockRouter clientHeadless={clientMock}>
				<ReassignTaskModal.Table
					data={data}
					reassignedTasks={{
						tasks: [{assigneeId: 20124, id: 39347}]
					}}
					setReassignedTasks={setReassignMock}
					{...{
						assetTitle: 'Blog2',
						assetType: 'Blogs Entry',
						assigneeUsers: [{id: 20124, name: 'Test Test'}],
						creatorUser: {id: 20124, name: 'Test Test'},
						dateCreated: '2019-12-10T17:44:44Z',
						id: 40330,
						slaStatus: 'Overdue',
						status: 'Completed'
					}}
				></ReassignTaskModal.Table>
			</MockRouter>
		);
		const reassignTaskModalTable = getByTestId('reassignTaskModalTable');

		expect(reassignTaskModalTable.innerHTML).not.toBeUndefined();
		expect(reassignTaskModalTable.innerHTML).not.toBeNull();
	});

	test('Render with no taskName', () => {
		const {getAllByTestId} = render(
			<MockRouter clientHeadless={clientMock}>
				<ReassignTaskModal.Table
					data={data}
					reassignedTasks={{
						tasks: [{assigneeId: 20124, id: 39347}]
					}}
					setReassignedTasks={setReassignMock}
					{...{
						assetTitle: 'Blog2',
						assetType: 'Blogs Entry',
						assigneeUsers: [{id: 20124, name: 'Test Test'}],
						creatorUser: {id: 20124, name: 'Test Test'},
						dateCreated: '2019-12-10T17:44:44Z',
						id: 40330,
						slaStatus: 'Overdue',
						status: 'Completed',
						taskNames: ['Update']
					}}
				></ReassignTaskModal.Table>
			</MockRouter>
		);
		const reassignTaskModalTable = getAllByTestId('reassignTaskModalTable');

		expect(reassignTaskModalTable[0].innerHTML).not.toBeUndefined();
		expect(reassignTaskModalTable[0].innerHTML).not.toBeNull();
	});

	test('Render with no data', () => {
		const data = {items: undefined};
		const {getAllByTestId} = render(
			<MockRouter clientHeadless={clientMock}>
				<ReassignTaskModal.Table
					data={data}
					reassignedTasks={{
						tasks: [{assigneeId: 20124, id: 39347}]
					}}
					setReassignedTasks={setReassignMock}
					{...{
						assetTitle: 'Blog2',
						assetType: 'Blogs Entry',
						assigneeUsers: [{id: 20124, name: 'Test Test'}],
						creatorUser: {id: 20124, name: 'Test Test'},
						dateCreated: '2019-12-10T17:44:44Z',
						id: 40330,
						slaStatus: 'Overdue',
						status: 'Completed',
						taskNames: ['Update']
					}}
				></ReassignTaskModal.Table>
			</MockRouter>
		);
		const reassignTaskModalTable = getAllByTestId('reassignTaskModalTable');

		expect(reassignTaskModalTable[0].innerHTML).not.toBeUndefined();
		expect(reassignTaskModalTable[0].innerHTML).not.toBeNull();
	});

	test('Render with taskNames', () => {
		const data = {
			items: [
				{
					assigneePerson: {
						additionalName: '',
						contentType: 'UserAccount',
						familyName: 'Test',
						givenName: 'Test',
						id: 20124,
						name: 'Test Test',
						profileURL: '/web/test'
					},
					assigneeRoles: [],
					completed: true,
					dateCompletion: '2019-12-10T17:45:38Z',
					dateCreated: '2019-12-10T17:44:45Z',
					definitionId: 38902,
					definitionName: 'Single Approver',
					definitionVersion: '',
					description: '',
					instanceId: 40330,
					name: 'review',
					objectReviewed: {
						id: 40324,
						resourceType: 'BlogPosting'
					}
				}
			]
		};

		const {getAllByTestId} = render(
			<MockRouter clientHeadless={clientMock}>
				<ReassignTaskModal.Table
					data={data}
					reassignedTasks={{
						tasks: [{assigneeId: 20124, id: 39347}]
					}}
					setReassignedTasks={setReassignMock}
					{...{
						assetTitle: 'Blog2',
						assetType: 'Blogs Entry',
						assigneeUsers: [{id: 20124, name: 'Test Test'}],
						creatorUser: {id: 20124, name: 'Test Test'},
						dateCreated: '2019-12-10T17:44:44Z',
						id: 40330,
						slaStatus: 'Overdue',
						status: 'Pending',
						taskNames: ['Update']
					}}
				></ReassignTaskModal.Table>
			</MockRouter>
		);
		const reassignTaskModalTable = getAllByTestId('reassignTaskModalTable');

		expect(reassignTaskModalTable[0].innerHTML).not.toBeUndefined();
		expect(reassignTaskModalTable[0].innerHTML).not.toBeNull();
	});
});
