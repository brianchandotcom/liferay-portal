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
import React, {useMemo} from 'react';

import {TimeRangeContext} from '../../../../src/main/resources/META-INF/resources/js/components/process-metrics/filter/store/TimeRangeStore.es';
import PerformanceByStepCard from '../../../../src/main/resources/META-INF/resources/js/components/process-metrics/performance-by-step-card/PerformanceByStepCard.es';
import Request from '../../../../src/main/resources/META-INF/resources/js/shared/components/request/Request.es';
import {MockRouter} from '../../../mock/MockRouter.es';

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

describe('The performance by step body component should', () => {
	let getAllByTestId;

	afterEach(cleanup);

	beforeEach(() => {
		const wrapper = mockTimeRangeContext();

		const renderResult = render(
			<Request>
				<MockRouter>
					<PerformanceByStepCard.Body
						data={{items, totalCount: items.length}}
						processId={123456}
					/>
				</MockRouter>
			</Request>,
			{wrapper}
		);

		getAllByTestId = renderResult.getAllByTestId;
	});

	test('Be rendered with "View All Steps" button and total "(3)"', async () => {
		const viewAllSteps = await waitForElement(() =>
			getAllByTestId('viewAllSteps')
		);

		expect(viewAllSteps[0].innerHTML).toBe('view-all-steps (3)');
	});

	test('Be rendered with "Review" and "Update" names', async () => {
		const stepNames = await waitForElement(() =>
			getAllByTestId('stepName')
		);

		expect(stepNames[0].innerHTML).toBe('Review');
		expect(stepNames[1].innerHTML).toBe('Update');
	});

	test('Be rendered with "30%" and "22.58%" percentages', async () => {
		const percentages = await waitForElement(() =>
			getAllByTestId('slaBreached')
		);

		expect(percentages[0].innerHTML).toBe('3 (30%)');
		expect(percentages[1].innerHTML).toBe('7 (22.58%)');
	});

	test('Be rendered with "3h" and "5d 12h" durations', async () => {
		const durations = await waitForElement(() =>
			getAllByTestId('avgCompletionTime')
		);

		expect(durations[0].innerHTML).toBe('3h');
		expect(durations[1].innerHTML).toBe('5d 12h');
	});
});

const mockTimeRangeContext = () => ({children}) => {
	const timeRange = useMemo(
		() => ({
			dateEnd: new Date('2019-01-07'),
			dateStart: new Date('2019-01-01'),
			id: 7,
			name: 'Last 7 days'
		}),
		[]
	);

	return (
		<TimeRangeContext.Provider
			value={{
				getSelectedTimeRange: () => timeRange
			}}
		>
			{children}
		</TimeRangeContext.Provider>
	);
};
