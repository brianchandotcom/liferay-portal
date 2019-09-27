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
import {MockRouter} from '../../../mock/MockRouter.es';
import {ProcessStepContext} from '../../../../src/main/resources/META-INF/resources/js/components/process-metrics/filter/store/ProcessStepStore.es';
import React from 'react';
import Request from '../../../../src/main/resources/META-INF/resources/js/shared/components/request/Request.es';
import WorkloadByAssigneeCard from '../../../../src/main/resources/META-INF/resources/js/components/process-metrics/workload-by-assignee/WorkloadByAssigneeCard.es';

const items = [
	{
		name: 'User 1',
		onTimeTaskCount: 10,
		overdueTaskCount: 5,
		taskCount: 15
	},
	{
		name: 'User 2',
		onTimeTaskCount: 3,
		overdueTaskCount: 7,
		taskCount: 10
	}
];

const clientMock = {
	get: jest.fn().mockResolvedValue({data: {items, totalCount: 2}})
};

const processStepContextMock = {
	getSelectedProcessSteps: () => [{key: 'review'}]
};

const MockContext = ({children}) => (
	<MockRouter client={clientMock}>
		<Request>
			<ProcessStepContext.Provider value={processStepContextMock}>
				{children}
			</ProcessStepContext.Provider>
		</Request>
	</MockRouter>
);

describe('The workload by assignee body, when On Time tab is active, should', () => {
	let getAllByTestId;

	afterEach(cleanup);

	beforeEach(() => {
		const renderResult = render(
			<MockContext>
				<WorkloadByAssigneeCard.Body
					currentTab="onTime"
					processId={12345}
				/>
			</MockContext>
		);

		getAllByTestId = renderResult.getAllByTestId;
	});

	test('Be rendered with "User 1" and "User 2" items and On Time tab display as active', async () => {
		const assigneeNames = await waitForElement(() =>
			getAllByTestId('assigneeName')
		);

		expect(assigneeNames[0].innerHTML).toBe('User 1');
		expect(assigneeNames[1].innerHTML).toBe('User 2');
	});
});

describe('The workload by assignee body, when Overdue tab is active, should', () => {
	let getAllByTestId;

	afterEach(cleanup);

	beforeEach(() => {
		const renderResult = render(
			<MockContext>
				<WorkloadByAssigneeCard.Body
					currentTab="overdue"
					processId={12345}
				/>
			</MockContext>
		);

		getAllByTestId = renderResult.getAllByTestId;
	});

	test('Be rendered with "User 1" and "User 2" items', async () => {
		const assigneeNames = await waitForElement(() =>
			getAllByTestId('assigneeName')
		);

		expect(assigneeNames[0].innerHTML).toBe('User 1');
		expect(assigneeNames[1].innerHTML).toBe('User 2');
	});
});

describe('The workload by assignee body, when Total tab is active, should', () => {
	let getAllByTestId;

	afterEach(cleanup);

	beforeEach(() => {
		const renderResult = render(
			<MockContext>
				<WorkloadByAssigneeCard.Body
					currentTab="total"
					processId={12345}
				/>
			</MockContext>
		);

		getAllByTestId = renderResult.getAllByTestId;
	});

	test('Be rendered with "User 1" and "User 2" items', async () => {
		const assigneeNames = await waitForElement(() =>
			getAllByTestId('assigneeName')
		);

		expect(assigneeNames[0].innerHTML).toBe('User 1');
		expect(assigneeNames[1].innerHTML).toBe('User 2');
	});
});
