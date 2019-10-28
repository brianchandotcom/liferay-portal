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

import {renderHook} from '@testing-library/react-hooks';
import React from 'react';

import {usePerformanceData} from '../../../../src/main/resources/META-INF/resources/js/components/performance-by-step-page/store/PerformanceByStepPageStore.es';
import {TimeRangeProvider} from '../../../../src/main/resources/META-INF/resources/js/components/process-metrics/filter/store/TimeRangeStore.es';
import Request from '../../../../src/main/resources/META-INF/resources/js/shared/components/request/Request.es';
import {MockRouter} from '../../../mock/MockRouter.es';

const data = {
	items: [
		{
			breachedInstanceCount: 4,
			durationAvg: 0,
			instanceCount: 4,
			key: 'review',
			name: 'Review',
			onTimeInstanceCount: 0,
			overdueInstanceCount: 4
		},
		{
			breachedInstanceCount: 0,
			durationAvg: 0,
			instanceCount: 0,
			key: 'update',
			name: 'Update',
			onTimeInstanceCount: 0,
			overdueInstanceCount: 0
		}
	],
	totalCount: 2
};

const clientMock = {
	get: jest.fn().mockResolvedValue({data})
};

const wrapper = ({children}) => (
	<MockRouter client={clientMock}>
		<Request>
			<TimeRangeProvider timeRangeKeys={['30']}>
				{children}
			</TimeRangeProvider>
		</Request>
	</MockRouter>
);

describe('The performance by step store should', () => {
	test('Return 2 items after request finish', async () => {
		const {result, unmount, waitForNextUpdate} = renderHook(
			() => usePerformanceData('2019-01-07', '2019-01-01', ['30']),
			{wrapper}
		);

		result.current.fetchData(1, 10, 12345, 'e');

		await waitForNextUpdate();

		expect(result.current.items.length).toBe(2);
		unmount();
	});
});
