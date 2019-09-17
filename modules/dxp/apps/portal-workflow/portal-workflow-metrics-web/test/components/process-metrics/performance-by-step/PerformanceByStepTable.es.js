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
import PerformanceByStepCard from '../../../../src/main/resources/META-INF/resources/js/components/process-metrics/performance-by-step/PerformanceByStepCard.es';
import React from 'react';

const items = [
	{
		durationAvg: 10800000,
		instanceCount: 10,
		name: 'Review',
		overdueInstanceCount: 3
	},
	{
		durationAvg: 475200000,
		instanceCount: 31,
		name: 'Update',
		overdueInstanceCount: 7
	}
];

describe('The performance by step table component should', () => {
	let getAllByTestId;

	afterEach(cleanup);

	beforeEach(() => {
		const renderResult = render(
			<PerformanceByStepCard.Table items={items} />
		);

		getAllByTestId = renderResult.getAllByTestId;
	});

	test('Be rendered with "Review" and "Update" names', () => {
		const stepNames = getAllByTestId('stepName');

		expect(stepNames[0].innerHTML).toBe('Review');
		expect(stepNames[1].innerHTML).toBe('Update');
	});

	test('Be rendered with "30%" and "22.58%" percentages', () => {
		const percentages = getAllByTestId('slaBreached');

		expect(percentages[0].innerHTML).toBe('3 (30%)');
		expect(percentages[1].innerHTML).toBe('7 (22.58%)');
	});

	test('Be rendered with "3h" and "5d 12h" durations', () => {
		const durations = getAllByTestId('avgCompletionTime');

		expect(durations[0].innerHTML).toBe('3h');
		expect(durations[1].innerHTML).toBe('5d 12h');
	});
});
