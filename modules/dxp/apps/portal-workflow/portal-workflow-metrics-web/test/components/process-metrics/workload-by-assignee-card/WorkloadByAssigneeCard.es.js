/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {act, cleanup, render} from '@testing-library/react';
import React from 'react';

import WorkloadByAssigneeCard from '../../../../src/main/resources/META-INF/resources/js/components/process-metrics/workload-by-assignee-card/WorkloadByAssigneeCard.es';
import {MockRouter} from '../../../mock/MockRouter.es';

describe('The workload by assignee card should', () => {
	let getByText;

	const items = [
		{
			assignee: {
				name: 'User 1',
			},
			onTimeTaskCount: 10,
			overdueTaskCount: 5,
			taskCount: 15,
		},
		{
			assignee: {
				name: 'User 2',
			},
			onTimeTaskCount: 3,
			overdueTaskCount: 7,
			taskCount: 10,
		},
	];

	afterEach(cleanup);

	beforeEach(async () => {
		fetch.mockResolvedValue({
			json: () => Promise.resolve({items, totalCount: 2}),
			ok: true,
		});

		const renderResult = render(
			<MockRouter>
				<WorkloadByAssigneeCard routeParams={{processId: 12345}} />
			</MockRouter>
		);

		getByText = renderResult.getByText;

		await act(async () => {
			jest.runAllTimers();
		});
	});

	it('Be rendered with "User 1" and "User 2" items', async () => {
		const assigneeName1 = getByText('User 1');
		const assigneeName2 = getByText('User 2');

		expect(assigneeName1).toBeTruthy();
		expect(assigneeName2).toBeTruthy();
	});
});
