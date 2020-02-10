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

import {
	cleanup,
	findAllByTestId,
	findByTestId,
	render
} from '@testing-library/react';
import React from 'react';

import CompletedItemsCard from '../../../../src/main/resources/META-INF/resources/js/components/process-metrics/process-items/CompletedItemsCard.es';
import {jsonSessionStorage} from '../../../../src/main/resources/META-INF/resources/js/shared/util/storage.es';
import {MockRouter} from '../../../mock/MockRouter.es';

import '@testing-library/jest-dom/extend-expect';

const {processId, query} = {
	processId: 12345,
	query: '?filters.completedtimeRange%5B0%5D=7'
};

const data = {
	id: 38803,
	instanceCount: 6,
	onTimeInstanceCount: 2,
	overdueInstanceCount: 1,
	title: 'Single Approver',
	untrackedInstanceCount: 3
};

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

describe('The completed items card component should', () => {
	let getByTestId;

	afterEach(cleanup);

	beforeAll(() => {
		jsonSessionStorage.set('timeRanges', timeRangeData);
	});

	beforeEach(() => {
		const clientMock = {
			get: jest.fn().mockResolvedValue({data})
		};

		const wrapper = ({children}) => (
			<MockRouter client={clientMock} query={query}>
				{children}
			</MockRouter>
		);

		const renderResult = render(
			<CompletedItemsCard routeParams={{processId}} />,
			{wrapper}
		);

		getByTestId = renderResult.getByTestId;
	});

	test('Be rendered with time range filter', async () => {
		const timeRangeFilter = getByTestId('timeRangeFilter');
		const filterItems = await findAllByTestId(
			timeRangeFilter,
			'filterItem'
		);
		const activeItem = filterItems.find(item =>
			item.className.includes('active')
		);
		const activeItemName = await findByTestId(activeItem, 'filterItemName');

		expect(timeRangeFilter).not.toBeNull();
		expect(activeItemName).toHaveTextContent('Last 7 Days');
	});

	test('Be rendered with overdue count "1"', () => {
		const panelBody = getByTestId('panelBody');

		const overdueLink = panelBody.children[0].children[0];
		const overdueHeader = overdueLink.children[0].children[0];
		const overdueBody = overdueLink.children[0].children[1];
		const overdueFooter = overdueLink.children[0].children[2].children[0];

		expect(overdueHeader).toHaveTextContent('overdue');
		expect(overdueBody).toHaveTextContent('1');
		expect(overdueFooter).toHaveTextContent('16.67%');
		expect(overdueLink.getAttribute('href')).toContain(
			'filters.statuses%5B0%5D=Completed&filters.slaStatuses%5B0%5D=Overdue&filters.dateEnd=2019-12-09&filters.dateStart=2019-12-03&filters.timeRange%5B0%5D=7'
		);
	});

	test('Be rendered with ontime count "2"', () => {
		const panelBody = getByTestId('panelBody');

		const ontimeLink = panelBody.children[0].children[1];
		const ontimeHeader = ontimeLink.children[0].children[0];
		const ontimeBody = ontimeLink.children[0].children[1];
		const ontimeFooter = ontimeLink.children[0].children[2].children[0];

		expect(ontimeHeader).toHaveTextContent('on-time');
		expect(ontimeBody).toHaveTextContent('2');
		expect(ontimeFooter).toHaveTextContent('33.33%');
		expect(ontimeLink.getAttribute('href')).toContain(
			'filters.statuses%5B0%5D=Completed&filters.slaStatuses%5B0%5D=OnTime&filters.dateEnd=2019-12-09&filters.dateStart=2019-12-03&filters.timeRange%5B0%5D=7'
		);
	});

	test('Be rendered with untracked count "3"', () => {
		const panelBody = getByTestId('panelBody');

		const untrackedLink = panelBody.children[0].children[2];
		const untrackedHeader = untrackedLink.children[0].children[0];
		const untrackedBody = untrackedLink.children[0].children[1];
		const untrackedFooter =
			untrackedLink.children[0].children[2].children[0];

		expect(untrackedHeader).toHaveTextContent('untracked');
		expect(untrackedBody).toHaveTextContent('3');
		expect(untrackedFooter).toHaveTextContent('50%');
		expect(untrackedLink.getAttribute('href')).toContain(
			'filters.statuses%5B0%5D=Completed&filters.slaStatuses%5B0%5D=Untracked&filters.dateEnd=2019-12-09&filters.dateStart=2019-12-03&filters.timeRange%5B0%5D=7'
		);
	});

	test('Be rendered with total completed count "6"', () => {
		const panelBody = getByTestId('panelBody');

		const totalLink = panelBody.children[0].children[3];
		const totalHeader = totalLink.children[0].children[0];
		const totalBody = totalLink.children[0].children[1];

		expect(totalHeader).toHaveTextContent('total-completed');
		expect(totalBody).toHaveTextContent('6');
		expect(totalLink.getAttribute('href')).toContain(
			'filters.statuses%5B0%5D=Completed&filters.dateEnd=2019-12-09&filters.dateStart=2019-12-03&filters.timeRange%5B0%5D=7'
		);
	});
});
