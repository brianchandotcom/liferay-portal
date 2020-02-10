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

import {findAllByTestId, findByTestId, render} from '@testing-library/react';
import React from 'react';

import CompletionVelocityCard from '../../../../src/main/resources/META-INF/resources/js/components/process-metrics/completion-velocity/CompletionVelocityCard.es';
import {jsonSessionStorage} from '../../../../src/main/resources/META-INF/resources/js/shared/util/storage.es';
import {MockRouter} from '../../../mock/MockRouter.es';

import '@testing-library/jest-dom/extend-expect';

const {processId, query} = {
	processId: 12345,
	query:
		'?filters.completionvelocityUnit%5B0%5D=Days&filters.completiontimeRange%5B0%5D=7'
};

const data = {
	histograms: [
		{
			key: '2019-12-03T00:00',
			value: 0.0
		},
		{
			key: '2019-12-04T00:00',
			value: 0.0
		},
		{
			key: '2019-12-05T00:00',
			value: 0.0
		},
		{
			key: '2019-12-06T00:00',
			value: 0.0
		},
		{
			key: '2019-12-07T00:00',
			value: 0.0
		},
		{
			key: '2019-12-08T00:00',
			value: 0.8
		},
		{
			key: '2019-12-09T00:00',
			value: 0.0
		}
	],
	value: 0.36
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

describe('The completion velocity card component should', () => {
	let getByTestId;

	beforeAll(() => {
		jsonSessionStorage.set('timeRanges', timeRangeData);

		const clientMock = {
			get: jest.fn().mockResolvedValue({data})
		};

		const renderResult = render(
			<MockRouter client={clientMock} query={query}>
				<CompletionVelocityCard routeParams={{processId}} />
			</MockRouter>
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

	test('Be rendered with time range filter', async () => {
		const velocityUnitFilter = await getByTestId('velocityUnitFilter');
		const filterItems = await findAllByTestId(
			velocityUnitFilter,
			'filterItem'
		);

		const activeItem = filterItems.find(item =>
			item.className.includes('active')
		);
		const activeItemName = await findByTestId(activeItem, 'filterItemName');

		expect(velocityUnitFilter).not.toBeNull();
		expect(activeItemName).toHaveTextContent('inst-day');
	});
});
