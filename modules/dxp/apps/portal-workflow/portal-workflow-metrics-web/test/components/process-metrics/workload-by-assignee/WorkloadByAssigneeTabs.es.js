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

import {cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';
import WorkloadByAssigneeCard from '../../../../src/main/resources/META-INF/resources/js/components/process-metrics/workload-by-assignee/WorkloadByAssigneeCard.es';

describe('The workload by assignee tabs should', () => {
	afterEach(cleanup);

	test('Call setCurrentTab with "overdue" when Overdue tab is clicked', () => {
		const setCurrentTab = jest.fn();

		const {getAllByTestId} = render(
			<WorkloadByAssigneeCard.Tabs
				currentTab="total"
				setCurrentTab={setCurrentTab}
			/>
		);

		const tabItemSpans = getAllByTestId('tabItemSpan');

		fireEvent.click(tabItemSpans[0]);

		expect(setCurrentTab).toHaveBeenCalledWith('overdue');
	});

	test('Call setCurrentTab with "onTime" when On Time tab is clicked', () => {
		const setCurrentTab = jest.fn();

		const {getAllByTestId} = render(
			<WorkloadByAssigneeCard.Tabs
				currentTab="total"
				setCurrentTab={setCurrentTab}
			/>
		);

		const tabItemSpans = getAllByTestId('tabItemSpan');

		fireEvent.click(tabItemSpans[1]);

		expect(setCurrentTab).toHaveBeenCalledWith('onTime');
	});
});
