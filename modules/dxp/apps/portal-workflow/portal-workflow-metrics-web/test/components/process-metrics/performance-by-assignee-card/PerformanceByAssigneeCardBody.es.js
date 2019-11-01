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

import {ProcessStepContext} from '../../../../src/main/resources/META-INF/resources/js/components/process-metrics/filter/store/ProcessStepStore.es';
import {TimeRangeContext} from '../../../../src/main/resources/META-INF/resources/js/components/process-metrics/filter/store/TimeRangeStore.es';
import PerformanceByAssigneeCard from '../../../../src/main/resources/META-INF/resources/js/components/process-metrics/performance-by-assignee-card/PerformanceByAssigneeCard.es';
import Request from '../../../../src/main/resources/META-INF/resources/js/shared/components/request/Request.es';
import {formatDuration} from '../../../../src/main/resources/META-INF/resources/js/shared/util/duration.es';
import {MockRouter} from '../../../mock/MockRouter.es';

const clientMock = {
	get: jest.fn().mockResolvedValue({data: {}})
};

const processStepContextMock = {
	getSelectedProcessSteps: () => [{key: 'review'}]
};

const timeRange = {
	dateEnd: new Date('2019-01-07'),
	dateStart: new Date('2019-01-01'),
	id: 7,
	name: 'Last 7 days'
};
const timeRangeContextMock = {
	getSelectedTimeRange: () => timeRange
};

describe('The performance by assignee body component with data should', () => {
	let getAllByTestId;
	let getByTestId;

	const items = [
		{
			durationTaskAvg: 10800000,
			image: 'path/to/image',
			name: 'User Test First',
			taskCount: 10
		},
		{
			durationTaskAvg: 475200000,
			image: 'path/to/image',
			name: 'User Test Second',
			taskCount: 31
		},
		{
			durationTaskAvg: 0,
			name: 'User Test Third',
			taskCount: 1
		}
	];
	const data = {items, totalCount: items.length};

	afterEach(() => cleanup);

	beforeEach(() => {
		clientMock.get.mockResolvedValueOnce({
			data
		});

		const wrapper = ({children}) => (
			<Request>
				<MockRouter client={clientMock}>
					<ProcessStepContext.Provider value={processStepContextMock}>
						<TimeRangeContext.Provider value={timeRangeContextMock}>
							{children}
						</TimeRangeContext.Provider>
					</ProcessStepContext.Provider>
				</MockRouter>
			</Request>
		);

		const renderResult = render(
			<PerformanceByAssigneeCard.Body data={data} processId={123456} />,
			{wrapper}
		);

		getAllByTestId = renderResult.getAllByTestId;
		getByTestId = renderResult.getByTestId;
	});

	test('Be rendered with "View All Steps" button and total "(3)"', async () => {
		const viewAllAssigneesButton = await waitForElement(() =>
			getByTestId('viewAllAssignees')
		);

		expect(viewAllAssigneesButton.innerHTML).toBe('view-all-assignees (3)');
	});

	test('Be rendered with user avatar or lexicon user icon', async () => {
		const assigneeProfileInfo = await waitForElement(() =>
			getAllByTestId('assigneeProfileInfo')
		);

		expect(assigneeProfileInfo[0].children[0].innerHTML).toContain(
			'path/to/image'
		);
		expect(assigneeProfileInfo[1].children[0].innerHTML).toContain(
			'path/to/image'
		);
		expect(assigneeProfileInfo[2].children[0].innerHTML).toContain(
			'lexicon-icon-user'
		);
	});

	test('Be rendered with assignee name', async () => {
		const assigneeName = await waitForElement(() =>
			getAllByTestId('assigneeName')
		);

		expect(assigneeName[0].innerHTML).toEqual('User Test First');
		expect(assigneeName[1].innerHTML).toEqual('User Test Second');
		expect(assigneeName[2].innerHTML).toEqual('User Test Third');
	});

	test('Be rendered with average completion time', async () => {
		const durations = await getAllByTestId('durationTaskAvg');

		expect(durations[0].innerHTML).toEqual(formatDuration(10800000));
		expect(durations[1].innerHTML).toEqual(formatDuration(475200000));
		expect(durations[2].innerHTML).toEqual(formatDuration(0));
	});
});

describe('The performance by assignee body component without data should', () => {
	let getByTestId;

	const items = [];
	const data = {items, totalCount: items.length};

	afterEach(() => cleanup);

	beforeEach(() => {
		clientMock.get.mockResolvedValueOnce({
			data
		});

		const wrapper = ({children}) => (
			<Request>
				<MockRouter client={clientMock}>
					<ProcessStepContext.Provider value={processStepContextMock}>
						<TimeRangeContext.Provider value={timeRangeContextMock}>
							{children}
						</TimeRangeContext.Provider>
					</ProcessStepContext.Provider>
				</MockRouter>
			</Request>
		);

		const renderResult = render(
			<PerformanceByAssigneeCard.Body data={data} processId={123456} />,
			{wrapper}
		);

		getByTestId = renderResult.getByTestId;
	});

	test('Render empty state', async () => {
		const emptyState = await waitForElement(() =>
			getByTestId('emptyState')
		);

		expect(emptyState).not.toBeNull();
	});
});
