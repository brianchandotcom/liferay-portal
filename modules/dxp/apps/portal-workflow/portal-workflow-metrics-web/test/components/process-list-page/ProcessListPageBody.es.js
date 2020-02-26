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
import React from 'react';

import ProcessListPage from '../../../src/main/resources/META-INF/resources/js/components/process-list-page/ProcessListPage.es';
import PromisesResolver from '../../../src/main/resources/META-INF/resources/js/shared/components/promises-resolver/PromisesResolver.es';
import {MockRouter} from '../../mock/MockRouter.es';

const items = [
	{
		instancesCount: 0,
		title: 'Single Approver 1',
	},
	{
		instancesCount: 0,
		title: 'Single Approver 2',
	},
	{
		instancesCount: 0,
		title: 'Single Approver 3',
	},
];

const wrapper = ({children}) => (
	<MockRouter>
		<PromisesResolver promises={[Promise.resolve()]}>
			{children}
		</PromisesResolver>
	</MockRouter>
);

describe('The performance by assignee page body should', () => {
	let getAllByTestId;

	afterEach(cleanup);

	beforeEach(() => {
		const renderResult = render(
			<ProcessListPage.Body
				data={{items, totalCount: items.length}}
				page="1"
				pageSize="5"
			/>,
			{wrapper}
		);

		getAllByTestId = renderResult.getAllByTestId;
	});

	test('Be rendered with process names', async () => {
		const processName = await waitForElement(() =>
			getAllByTestId('processName')
		);

		expect(processName[0].children[0].innerHTML).toEqual(
			'Single Approver 1'
		);
		expect(processName[1].children[0].innerHTML).toEqual(
			'Single Approver 2'
		);
		expect(processName[2].children[0].innerHTML).toEqual(
			'Single Approver 3'
		);
	});
});

describe('The subcomponents from workload by assignee page body should', () => {
	afterEach(cleanup);

	test('Be rendered with empty view and no content message', async () => {
		const {getByTestId} = render(<ProcessListPage.Body.Empty />);

		const emptyStateDiv = getByTestId('emptyState');

		expect(emptyStateDiv.children[1].innerHTML).toBe('no-current-metrics');
	});

	test('Be rendered with empty view and no results message', async () => {
		const {getByTestId} = render(
			<ProcessListPage.Body.Empty search={true} />
		);

		const emptyStateDiv = getByTestId('emptyState');

		expect(emptyStateDiv.children[1].children[0].innerHTML).toBe(
			'no-results-were-found'
		);
	});

	test('Be rendered with error view and the expected message', () => {
		const {getByTestId} = render(<ProcessListPage.Body.Error />);

		const emptyStateDiv = getByTestId('emptyState');
		expect(emptyStateDiv.children[0].children[0].innerHTML).toBe(
			'there-was-a-problem-retrieving-data-please-try-reloading-the-page'
		);
	});

	test('Be rendered with loading view', async () => {
		const {getByTestId} = render(<ProcessListPage.Body.Loading />);

		const loadingStateDiv = getByTestId('loadingState');

		expect(loadingStateDiv).not.toBeNull();
	});
});
