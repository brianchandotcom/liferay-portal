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

import {render} from '@testing-library/react';
import React from 'react';

import PerformanceByAssigneePage from '../../../src/main/resources/META-INF/resources/js/components/performance-by-assignee-page/PerformanceByAssigneePage.es';
import {stringify} from '../../../src/main/resources/META-INF/resources/js/shared/components/router/queryString.es';
import {jsonSessionStorage} from '../../../src/main/resources/META-INF/resources/js/shared/util/storage.es';
import {MockRouter} from '../../mock/MockRouter.es';

import '@testing-library/jest-dom/extend-expect';

const {filters, processId} = {
	filters: {
		stepDateEnd: '2019-12-09T00:00:00Z',
		stepDateStart: '2019-12-03T00:00:00Z',
		stepTimeRange: ['7'],
	},
	processId: 12345,
};

const items = [
	{
		assignee: {image: 'path/to/image', name: 'User Test First'},
		durationTaskAvg: 10800000,
		taskCount: 10,
	},
	{
		assignee: {image: 'path/to/image', name: 'User Test Second'},
		durationTaskAvg: 475200000,
		taskCount: 31,
	},
	{
		assignee: {name: 'User Test Third'},
		durationTaskAvg: 0,
		taskCount: 1,
	},
];

const data = {items, totalCount: items.length};
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

describe('The PerformanceByAssigneePage component having data should', () => {
	let getAllByRole, rows;

	beforeAll(() => {
		jsonSessionStorage.set('timeRanges', timeRangeData);

		const clientMock = {
			get: jest.fn().mockResolvedValue({data}),
			post: jest.fn().mockResolvedValue({data}),
			request: jest.fn().mockResolvedValue({data}),
		};

		const wrapper = ({children}) => (
			<MockRouter client={clientMock} query={query}>
				{children}
			</MockRouter>
		);

		const renderResult = render(
			<PerformanceByAssigneePage routeParams={{processId}} />,
			{wrapper}
		);

		getAllByRole = renderResult.getAllByRole;
	});

	test('Be rendered with user avatar or lexicon user icon', async () => {
		rows = getAllByRole('row');

		const assigneeProfileInfo1 = rows[1].children[0].children[0];
		const assigneeProfileInfo2 = rows[1].children[0].children[0];
		const assigneeProfileInfo3 = rows[3].children[0].children[0];

		expect(assigneeProfileInfo1.children[0].innerHTML).toContain(
			'path/to/image'
		);
		expect(assigneeProfileInfo2.children[0].innerHTML).toContain(
			'path/to/image'
		);
		expect(assigneeProfileInfo3.children[0].innerHTML).toContain(
			'lexicon-icon-user'
		);
	});

	test('Be rendered with assignee names', async () => {
		expect(rows[1]).toHaveTextContent('User Test First');
		expect(rows[2]).toHaveTextContent('User Test Second');
		expect(rows[3]).toHaveTextContent('User Test Third');
	});

	test('Be rendered with average completion time', () => {
		expect(rows[1]).toHaveTextContent('3h');
		expect(rows[2]).toHaveTextContent('5d 12h');
		expect(rows[3]).toHaveTextContent('0min');
	});
});
