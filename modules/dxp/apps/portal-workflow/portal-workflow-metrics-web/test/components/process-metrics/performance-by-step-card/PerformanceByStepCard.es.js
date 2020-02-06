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

import {cleanup, findByTestId, render} from '@testing-library/react';
import React from 'react';

import PerformanceByStepCard from '../../../../src/main/resources/META-INF/resources/js/components/process-metrics/performance-by-step-card/PerformanceByStepCard.es';
import {jsonSessionStorage} from '../../../../src/main/resources/META-INF/resources/js/shared/util/storage.es';
import {MockRouter} from '../../../mock/MockRouter.es';

import '@testing-library/jest-dom/extend-expect';

const {processId, query} = {
	processId: 12345,
	query: '?filters.stepTimeRange%5B0%5D=7'
};

const items = [
	{
		breachedInstanceCount: 3,
		breachedInstancePercentage: 30,
		durationAvg: 10800000,
		name: 'Review'
	},
	{
		breachedInstanceCount: 7,
		breachedInstancePercentage: 22.5806,
		durationAvg: 475200000,
		name: 'Update'
	},
	{
		breachedInstanceCount: 0,
		breachedInstancePercentage: 0,
		durationAvg: 0,
		name: 'Translate'
	}
];
const data = {items, totalCount: items.length};

const timeRangeData = {
	items: [
		{
			dateEnd: '2019-12-09T00:00:00Z',
			dateStart: '2019-12-03T00:00:00Z',
			defaultTimeRange: false,
			id: 7,
			name: 'Last 7 Days'
		},
		{
			dateEnd: '2019-12-09T00:00:00Z',
			dateStart: '2019-11-10T00:00:00Z',
			defaultTimeRange: true,
			id: 30,
			name: 'Last 30 Days'
		}
	],
	totalCount: 2
};

describe('The performance by step card component should', () => {
	let getAllByTestId, getByTestId;

	beforeAll(() => {
		jsonSessionStorage.set('timeRanges', timeRangeData);
	});

	describe('Be rendered with results', () => {
		beforeAll(() => {
			const clientMock = {
				get: jest.fn().mockResolvedValue({data})
			};

			const wrapper = ({children}) => (
				<MockRouter client={clientMock} query={query}>
					{children}
				</MockRouter>
			);

			const renderResult = render(
				<PerformanceByStepCard routeParams={{processId}} />,
				{wrapper}
			);

			getByTestId = renderResult.getByTestId;
			getAllByTestId = renderResult.getAllByTestId;
		});

		test('Be rendered with time range filter', async () => {
			const timeRangeFilter = getByTestId('timeRangeFilter');

			const filterItems = await getAllByTestId('filterItem');

			const activeItem = filterItems.find(item =>
				item.className.includes('active')
			);
			const activeItemName = await findByTestId(
				activeItem,
				'filterItemName'
			);

			expect(timeRangeFilter).not.toBeNull();
			expect(activeItemName).toHaveTextContent('Last 7 Days');
		});

		test('Be rendered with "View All Steps" button and total "(3)"', () => {
			const viewAllSteps = getByTestId('viewAllSteps');

			expect(viewAllSteps).toHaveTextContent('view-all-steps (3)');
			expect(viewAllSteps.parentNode.getAttribute('href')).toContain(
				'filters.dateEnd=2019-12-09&filters.dateStart=2019-12-03&filters.timeRange%5B0%5D=7'
			);
		});
	});

	describe('Be rendered without results', () => {
		afterEach(cleanup);

		beforeEach(() => {
			const clientMock = {
				get: jest
					.fn()
					.mockResolvedValue({data: {items: [], totalCount: 0}})
			};

			const wrapper = ({children}) => (
				<MockRouter client={clientMock} query={query}>
					{children}
				</MockRouter>
			);

			const renderResult = render(
				<PerformanceByStepCard routeParams={{processId}} />,
				{wrapper}
			);

			getByTestId = renderResult.getByTestId;
		});

		test('Be rendered with empty state view', () => {
			const emptyStateDiv = getByTestId('emptyState');

			expect(emptyStateDiv.children[0].children[0]).toHaveTextContent(
				'there-is-no-data-at-the-moment'
			);
		});
	});
});
