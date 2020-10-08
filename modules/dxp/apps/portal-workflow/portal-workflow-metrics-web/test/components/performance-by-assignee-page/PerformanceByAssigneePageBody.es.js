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

import '@testing-library/jest-dom/extend-expect';
import {cleanup, render} from '@testing-library/react';
import React from 'react';

import PerformanceByAssigneePage from '../../../src/main/resources/META-INF/resources/js/components/performance-by-assignee-page/PerformanceByAssigneePage.es';
import PromisesResolver from '../../../src/main/resources/META-INF/resources/js/shared/components/promises-resolver/PromisesResolver.es';
import {MockRouter} from '../../mock/MockRouter.es';

const items = [
	{
		assignee: {image: 'path/to/image', name: 'User Test First'},
		durationTaskAvg: 10800000,
		taskCount: 10,
	},
	{
		assignee: {image: 'path/to/image', name: 'User Test Second'},
		durationTaskAvg: 475200000,
		taskCount: 31,
	},
	{
		assignee: {name: 'User Test Third'},
		durationTaskAvg: 0,
		taskCount: 1,
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
	let getAllByRole;

	afterEach(cleanup);

	beforeEach(() => {
		const renderResult = render(
			<PerformanceByAssigneePage.Body
				{...{items, totalCount: items.length}}
				page="1"
				pageSize="5"
			/>,
			{wrapper}
		);

		getAllByRole = renderResult.getAllByRole;
	});

	test('Be rendered with assignees names', () => {
		const rows = getAllByRole('row');

		expect(rows[1]).toHaveTextContent('User Test First');
		expect(rows[2]).toHaveTextContent('User Test Second');
		expect(rows[3]).toHaveTextContent('User Test Third');
	});
});

describe('The subcomponents from workload by assignee page body should', () => {
	afterEach(cleanup);

	test('Be rendered with empty view and no content message', async () => {
		const {getByText} = render(
			<PerformanceByAssigneePage.Body items={[]} totalCount={0} />
		);

		const emptyStateMessage = getByText('there-is-no-data-at-the-moment');

		expect(emptyStateMessage).toBeTruthy();
	});

	test('Be rendered with empty view and no results message', async () => {
		const {getByText} = render(
			<PerformanceByAssigneePage.Body
				filtered={true}
				items={[]}
				totalCount={0}
			/>
		);

		const emptyStateMessage = getByText('no-results-were-found');

		expect(emptyStateMessage).toBeTruthy();
	});
});
