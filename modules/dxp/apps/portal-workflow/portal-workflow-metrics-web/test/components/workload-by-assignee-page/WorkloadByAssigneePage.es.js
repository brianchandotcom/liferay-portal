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
import React from 'react';

import WorkloadByAssigneePage from '../../../src/main/resources/META-INF/resources/js/components/workload-by-assignee-page/WorkloadByAssigneePage.es';
import {MockRouter} from '../../mock/MockRouter.es';

const items = [
	{
		name: 'User 1',
		onTimeTaskCount: 10,
		overdueTaskCount: 5,
		taskCount: 15
	},
	{
		image: 'path/to/image.jpg',
		name: 'User 2',
		onTimeTaskCount: 3,
		overdueTaskCount: 7,
		taskCount: 10
	}
];

const clientMock = {
	get: jest.fn().mockResolvedValue({data: {items, totalCount: 2}})
};

const MockContext = ({children}) => (
	<MockRouter client={clientMock}>{children}</MockRouter>
);

describe('The workload by assignee page body should', () => {
	let getAllByTestId;

	afterEach(() => cleanup);

	beforeEach(() => {
		const routeParams = {
			page: '1',
			pageSize: '5',
			processId: '12345',
			sort: 'overdueTaskCount:desc'
		};

		const renderResult = render(
			<MockContext>
				<WorkloadByAssigneePage routeParams={routeParams} />
			</MockContext>
		);

		getAllByTestId = renderResult.getAllByTestId;
	});

	test('Be rendered with "User 1" and "User 2" names', async () => {
		const assigneeNames = await waitForElement(() =>
			getAllByTestId('assigneeName')
		);

		expect(assigneeNames[0].innerHTML).toBe('User 1');
		expect(assigneeNames[1].innerHTML).toBe('User 2');
	});
});
