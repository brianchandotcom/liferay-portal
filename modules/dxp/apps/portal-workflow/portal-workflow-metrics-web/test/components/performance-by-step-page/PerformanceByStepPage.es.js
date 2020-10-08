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

import PerformanceByStepPage from '../../../src/main/resources/META-INF/resources/js/components/performance-by-step-page/PerformanceByStepPage.es';
import {jsonSessionStorage} from '../../../src/main/resources/META-INF/resources/js/shared/util/storage.es';
import {MockRouter} from '../../mock/MockRouter.es';

import '@testing-library/jest-dom/extend-expect';

describe('The PerformanceByStepPage component having data should', () => {
	let getAllByRole, rows;

	const items = [
		{
			breachedInstanceCount: 4,
			durationAvg: 10800000,
			instanceCount: 4,
			node: {key: 'review', label: 'Review', name: 'Review'},
			onTimeInstanceCount: 4,
			overdueInstanceCount: 4,
		},
		{
			breachedInstanceCount: 2,
			durationAvg: 475200000,
			instanceCount: 2,
			node: {key: 'update', label: 'Update', name: 'Update'},
			onTimeInstanceCount: 2,
			overdueInstanceCount: 2,
		},
	];

	const data = {items, totalCount: items.length};

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

	const clientMock = {
		get: jest.fn().mockResolvedValue({data}).mockResolvedValueOnce({data}),
	};

	const wrapper = ({children}) => (
		<MockRouter client={clientMock}>{children}</MockRouter>
	);

	beforeAll(() => {
		jsonSessionStorage.set('timeRanges', timeRangeData);
		const renderResult = render(
			<PerformanceByStepPage routeParams={{processId: '1234'}} />,
			{wrapper}
		);

		getAllByRole = renderResult.getAllByRole;
	});

	test('Be rendered with step names', async () => {
		rows = getAllByRole('row');

		expect(rows[1]).toHaveTextContent('Review');
		expect(rows[2]).toHaveTextContent('Update');
	});

	test('Be rendered with SLA Breached (%)', () => {
		expect(rows[1]).toHaveTextContent('4 (0%)');
		expect(rows[2]).toHaveTextContent('2 (0%)');
	});

	test('Be rendered with average completion time', () => {
		expect(rows[1]).toHaveTextContent('3h');
		expect(rows[2]).toHaveTextContent('5d 12h');
	});
});
