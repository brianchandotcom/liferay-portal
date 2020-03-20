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

import WorkloadByAssigneePage from '../../../src/main/resources/META-INF/resources/js/components/workload-by-assignee-page/WorkloadByAssigneePage.es';
import {MockRouter} from '../../mock/MockRouter.es';

describe('The workload by assignee page table should', () => {
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
			assignee: {id: 2, image: 'path/to/image.jpg', name: 'User 2'},
			onTimeTaskCount: 3,
			overdueTaskCount: 7,
			taskCount: 10,
		},
	];

	afterEach(cleanup);

	test('Be rendered with "User 1" and "User 2" names', () => {
		const {getAllByTestId} = render(
			<WorkloadByAssigneePage.Body.Table items={items} />,
			{wrapper: MockRouter}
		);

		const assigneeNames = getAllByTestId('assigneeName');

		expect(assigneeNames[0].innerHTML).toBe('User 1');
		expect(assigneeNames[1].innerHTML).toBe('User 2');
	});

	test('Be rendered with "10" and "3" values as "On Time" column', () => {
		const {getAllByTestId} = render(
			<WorkloadByAssigneePage.Body.Table items={items} />,
			{wrapper: MockRouter}
		);

		const onTimeTaskCounts = getAllByTestId('onTimeTaskCount');

		expect(onTimeTaskCounts[0].children[0].innerHTML).toBe('10');
		expect(onTimeTaskCounts[1].children[0].innerHTML).toBe('3');
	});

	test('Be rendered with "5" and "7" values as "Overdue" column', () => {
		const {getAllByTestId} = render(
			<WorkloadByAssigneePage.Body.Table items={items} />,
			{wrapper: MockRouter}
		);

		const overdueTaskCounts = getAllByTestId('overdueTaskCount');

		expect(overdueTaskCounts[0].children[0].innerHTML).toBe('5');
		expect(overdueTaskCounts[1].children[0].innerHTML).toBe('7');
	});

	test('Be rendered with "15" and "10" values as "Total Pending" column', () => {
		const {getAllByTestId} = render(
			<WorkloadByAssigneePage.Body.Table items={items} />,
			{wrapper: MockRouter}
		);

		const taskCounts = getAllByTestId('taskCount');

		expect(taskCounts[0].children[0].innerHTML).toBe('15');
		expect(taskCounts[1].children[0].innerHTML).toBe('10');
	});
});
