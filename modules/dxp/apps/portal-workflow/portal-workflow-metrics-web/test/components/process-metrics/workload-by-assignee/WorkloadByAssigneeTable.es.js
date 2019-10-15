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
import WorkloadByAssigneeCard from '../../../../src/main/resources/META-INF/resources/js/components/process-metrics/workload-by-assignee/WorkloadByAssigneeCard.es';

describe('The workload by assignee table should', () => {
	const items = [
		{
			name: 'User 1',
			onTimeTaskCount: 10,
			overdueTaskCount: 5,
			taskCount: 15
		},
		{
			name: 'User 2',
			onTimeTaskCount: 3,
			overdueTaskCount: 7,
			taskCount: 10
		}
	];

	afterEach(cleanup);

	test('Be rendered with "User 1" and "User 2" names when the tab is Total', () => {
		const {getAllByTestId} = render(
			<WorkloadByAssigneeCard.Table currentTab="total" items={items} />
		);

		const assigneeNames = getAllByTestId('assigneeName');

		expect(assigneeNames[0].innerHTML).toBe('User 1');
		expect(assigneeNames[1].innerHTML).toBe('User 2');
	});

	test('Be rendered with "15" and "10" values when the tab is Total', () => {
		const {getAllByTestId} = render(
			<WorkloadByAssigneeCard.Table currentTab="total" items={items} />
		);

		const taskCountValues = getAllByTestId('taskCountValue');

		expect(taskCountValues[0].innerHTML).toBe('15');
		expect(taskCountValues[1].innerHTML).toBe('10');
		expect(() => getAllByTestId('taskCountPercentage')).toThrow();
	});

	test('Be rendered with "10 / 66.67%" and "3 / 30%" values when the tab is On Time', () => {
		const {getAllByTestId} = render(
			<WorkloadByAssigneeCard.Table currentTab="onTime" items={items} />
		);

		const taskCountPercentages = getAllByTestId('taskCountPercentage');
		const taskCountValues = getAllByTestId('taskCountValue');

		expect(taskCountPercentages[0].innerHTML).toBe(' / 66.67%');
		expect(taskCountPercentages[1].innerHTML).toBe(' / 30%');
		expect(taskCountValues[0].innerHTML).toBe('10');
		expect(taskCountValues[1].innerHTML).toBe('3');
	});

	test('Be rendered with "5 / 33.33%" and "7 / 70%" values when the tab is Overdue', () => {
		const {getAllByTestId} = render(
			<WorkloadByAssigneeCard.Table currentTab="overdue" items={items} />
		);

		const taskCountPercentages = getAllByTestId('taskCountPercentage');
		const taskCountValues = getAllByTestId('taskCountValue');

		expect(taskCountPercentages[0].innerHTML).toBe(' / 33.33%');
		expect(taskCountPercentages[1].innerHTML).toBe(' / 70%');
		expect(taskCountValues[0].innerHTML).toBe('5');
		expect(taskCountValues[1].innerHTML).toBe('7');
	});

	test('Be rendered with no rows when items list is empty', () => {
		const {getAllByTestId} = render(
			<WorkloadByAssigneeCard.Table currentTab="overdue" />
		);

		expect(() => getAllByTestId('assigneeName')).toThrow();
		expect(() => getAllByTestId('taskCountPercentage')).toThrow();
		expect(() => getAllByTestId('taskCountValue')).toThrow();
	});
});
