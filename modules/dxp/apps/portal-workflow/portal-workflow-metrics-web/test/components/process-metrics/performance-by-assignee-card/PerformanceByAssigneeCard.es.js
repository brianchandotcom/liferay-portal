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

import PerformanceByAssigneeCard from '../../../../src/main/resources/META-INF/resources/js/components/process-metrics/performance-by-assignee-card/PerformanceByAssigneeCard.es';
import {stringify} from '../../../../src/main/resources/META-INF/resources/js/shared/components/router/queryString.es';
import {jsonSessionStorage} from '../../../../src/main/resources/META-INF/resources/js/shared/util/storage.es';
import {MockRouter} from '../../../mock/MockRouter.es';

import '@testing-library/jest-dom/extend-expect';

const {filters, processId} = {
	filters: {
		assigneeDateEnd: '2019-12-09T00:00:00Z',
		assigneeDateStart: '2019-12-03T00:00:00Z',
		assigneeTaskNames: ['update'],
		assigneeTimeRange: ['7'],
	},
	processId: 12345,
};
const items = [
	{
		assignee: {
			image: 'path/to/image',
			name: 'User Test First',
		},
		durationTaskAvg: 10800000,
		taskCount: 10,
	},
	{
		assignee: {
			image: 'path/to/image',
			name: 'User Test Second',
		},
		durationTaskAvg: 475200000,
		taskCount: 31,
	},
	{
		assignee: {
			name: 'User Test Third',
		},
		durationTaskAvg: 0,
		taskCount: 1,
	},
];
const data = {items, totalCount: items.length};
const processStepsData = {
	items: [
		{
			label: 'Review',
			name: 'review',
		},
		{
			label: 'Update',
			name: 'update',
		},
	],
	totalCount: 2,
};
const query = stringify({filters});
const timeRangeData = {
	items: [
		{
			dateEnd: '2019-12-09T00:00:00Z',
			dateStart: '2019-12-03T00:00:00Z',
			defaultTimeRange: false,
			id: 7,
			name: 'Last 7 Days',
		},
		{
			dateEnd: '2019-12-09T00:00:00Z',
			dateStart: '2019-11-10T00:00:00Z',
			defaultTimeRange: true,
			id: 30,
			name: 'Last 30 Days',
		},
	],
	totalCount: 2,
};

describe('The performance by assignee card component should', () => {
	let container, getByTestId, getByText;

	beforeAll(() => {
		jsonSessionStorage.set('timeRanges', timeRangeData);
	});

	describe('Be rendered with results', () => {
		afterEach(cleanup);

		beforeEach(() => {
			const clientMock = {
				post: jest.fn().mockResolvedValue({data}),
				request: jest.fn().mockResolvedValue({data: processStepsData}),
			};

			const wrapper = ({children}) => (
				<MockRouter client={clientMock} query={query}>
					{children}
				</MockRouter>
			);

			const renderResult = render(
				<PerformanceByAssigneeCard routeParams={{processId}} />,
				{wrapper}
			);

			container = renderResult.container;
			getByTestId = renderResult.getByTestId;
			getByText = renderResult.getByText;
		});

		test('Be rendered with "View All Assignees" button and total "(3)"', () => {
			const viewAllAssignees = getByTestId('viewAllAssignees');

			expect(viewAllAssignees).toHaveTextContent(
				'view-all-assignees (3)'
			);
			expect(viewAllAssignees.parentNode.getAttribute('href')).toContain(
				'filters.dateEnd=2019-12-09T00%3A00%3A00Z&filters.dateStart=2019-12-03T00%3A00%3A00Z&filters.timeRange%5B0%5D=7&filters.taskNames%5B0%5D=update'
			);
		});

		test('Be rendered with process step filter', async () => {
			const processStepFilter = getByText('all-steps');
			const activeItem = container.querySelectorAll('.active')[0];

			expect(processStepFilter).not.toBeNull();
			expect(activeItem).toHaveTextContent('Update');
		});

		test('Be rendered with time range filter', async () => {
			const timeRangeFilter = getByText('Last 30 Days');
			const activeItem = container.querySelectorAll('.active')[1];

			expect(timeRangeFilter).not.toBeNull();
			expect(activeItem).toHaveTextContent('Last 7 Days');
		});
	});

	describe('Be rendered without results', () => {
		beforeAll(() => {
			const clientMock = {
				post: jest
					.fn()
					.mockResolvedValue({data: {items: [], totalCount: 0}}),
				request: jest.fn().mockResolvedValue({data: processStepsData}),
			};

			const wrapper = ({children}) => (
				<MockRouter client={clientMock} query={query}>
					{children}
				</MockRouter>
			);

			const renderResult = render(
				<PerformanceByAssigneeCard routeParams={{processId}} />,
				{wrapper}
			);

			getByTestId = renderResult.getByTestId;
		});

		test('Be rendered with empty state view', () => {
			const emptyStateDiv = getByTestId('emptyState');

			expect(emptyStateDiv.children[0].children[0]).toHaveTextContent(
				'no-results-were-found'
			);
		});
	});
});
