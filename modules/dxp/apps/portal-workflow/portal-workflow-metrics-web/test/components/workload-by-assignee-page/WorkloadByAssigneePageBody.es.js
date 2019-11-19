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
import PromisesResolver from '../../../src/main/resources/META-INF/resources/js/shared/components/request/PromisesResolver.es';
import {MockRouter} from '../../mock/MockRouter.es';

const items = [
	{
		name: 'User 1',
		onTimeTaskCount: 10,
		overdueTaskCount: 5,
		taskCount: 15
	},
	{
		image: 'path/to/image.jpg',
		name: 'User 2',
		onTimeTaskCount: 3,
		overdueTaskCount: 7,
		taskCount: 10
	}
];

const wrapper = ({children}) => (
	<MockRouter>
		<PromisesResolver promises={[Promise.resolve()]}>
			{children}
		</PromisesResolver>
	</MockRouter>
);

describe('The workload by assignee page body should', () => {
	let getAllByTestId;

	afterEach(cleanup);

	beforeEach(() => {
		const renderResult = render(
			<WorkloadByAssigneePage.Body
				data={{items, totalCount: items.length}}
				page="1"
				pageSize="5"
			/>,
			{wrapper}
		);

		getAllByTestId = renderResult.getAllByTestId;
	});

	test('Be rendered with "User 1" and "User 2" names', () => {
		const assigneeNames = getAllByTestId('assigneeName');

		expect(assigneeNames[0].innerHTML).toBe('User 1');
		expect(assigneeNames[1].innerHTML).toBe('User 2');
	});
});

describe('The subcomponents from workload by assignee page body should', () => {
	afterEach(cleanup);

	test('Be rendered with empty view and the expected message', async () => {
		const {getByTestId} = render(<WorkloadByAssigneePage.Empty />);

		const emptyStateDiv = getByTestId('emptyState');

		expect(emptyStateDiv.children[1].children[0].innerHTML).toBe(
			'no-results-were-found'
		);
	});

	test('Be rendered with error view and the expected message', () => {
		const {getByTestId} = render(<WorkloadByAssigneePage.Error />);

		const emptyStateDiv = getByTestId('emptyState');

		expect(emptyStateDiv.children[0].children[0].innerHTML).toBe(
			'there-was-a-problem-retrieving-data-please-try-reloading-the-page'
		);
	});

	test('Be rendered with loading view', async () => {
		const {getByTestId} = render(<WorkloadByAssigneePage.Loading />);

		const loadingStateDiv = getByTestId('loadingState');

		expect(loadingStateDiv).not.toBeNull();
	});
});
