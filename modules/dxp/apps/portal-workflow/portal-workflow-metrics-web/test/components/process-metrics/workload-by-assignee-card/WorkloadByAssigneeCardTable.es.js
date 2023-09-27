/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import {stringify} from 'qs';
import React from 'react';

import WorkloadByAssigneeCard from '../../../../src/main/resources/META-INF/resources/js/components/process-metrics/workload-by-assignee-card/WorkloadByAssigneeCard.es';
import {MockRouter} from '../../../mock/MockRouter.es';

describe('The workload by assignee table should', () => {
	const items = [
		{
			assignee: {
				id: 1,
				name: 'User 1',
			},
			onTimeTaskCount: 10,
			overdueTaskCount: 5,
			taskCount: 15,
		},
		{
			assignee: {
				id: 2,
				name: 'User 2',
			},
			onTimeTaskCount: 3,
			overdueTaskCount: 7,
			taskCount: 10,
		},
	];

	afterEach(cleanup);

	test('Be rendered with "User 1" and "User 2" names when the tab is Total', () => {
		const {getByText} = render(
			<WorkloadByAssigneeCard.Body.Table
				currentTab="total"
				items={items}
			/>,
			{wrapper: MockRouter}
		);

		expect(getByText('User 1')).toBeTruthy();
		expect(getByText('User 2')).toBeTruthy();
	});

	test('Be rendered with "15" and "10" values when the tab is Total', () => {
		const {container} = render(
			<WorkloadByAssigneeCard.Body.Table
				currentTab="total"
				items={items}
			/>,
			{wrapper: MockRouter}
		);

		const taskCountValues = container.querySelectorAll('.task-count-value');

		expect(taskCountValues[0].innerHTML).toBe('15');
		expect(taskCountValues[1].innerHTML).toBe('10');
	});

	test('Be rendered with "10 / 66.67%" and "3 / 30%" values when the tab is On Time', () => {
		const {container} = render(
			<WorkloadByAssigneeCard.Body.Table
				currentTab="onTime"
				items={items}
			/>,
			{wrapper: MockRouter}
		);

		const taskCountPercentages = container.querySelectorAll(
			'.task-count-percentage'
		);
		const taskCountValues = container.querySelectorAll('.task-count-value');

		expect(taskCountPercentages[0].innerHTML).toBe(' / 66.67%');
		expect(taskCountPercentages[1].innerHTML).toBe(' / 30%');
		expect(taskCountValues[0].innerHTML).toBe('10');
		expect(taskCountValues[1].innerHTML).toBe('3');
	});

	test('Be rendered with "5 / 33.33%" and "7 / 70%" values when the tab is Overdue', () => {
		const {container} = render(
			<WorkloadByAssigneeCard.Body.Table
				currentTab="overdue"
				items={items}
			/>,
			{wrapper: MockRouter}
		);

		const taskCountPercentages = container.querySelectorAll(
			'.task-count-percentage'
		);
		const taskCountValues = container.querySelectorAll('.task-count-value');

		expect(taskCountPercentages[0].innerHTML).toBe(' / 33.33%');
		expect(taskCountPercentages[1].innerHTML).toBe(' / 70%');
		expect(taskCountValues[0].innerHTML).toBe('5');
		expect(taskCountValues[1].innerHTML).toBe('7');
	});

	test('Be rendered with no rows when items list is empty', () => {
		const {container} = render(
			<WorkloadByAssigneeCard.Body.Table currentTab="overdue" />,
			{
				wrapper: MockRouter,
			}
		);

		expect(stringify(container.querySelectorAll('.assignee-name'))).toEqual(
			''
		);
		expect(
			stringify(container.querySelectorAll('.task-count-percentage'))
		).toEqual('');
		expect(
			stringify(container.querySelectorAll('.task-count-value'))
		).toEqual('');
	});
});
