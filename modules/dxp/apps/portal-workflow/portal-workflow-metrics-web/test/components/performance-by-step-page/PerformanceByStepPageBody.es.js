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

import PerformanceByStepPage from '../../../src/main/resources/META-INF/resources/js/components/performance-by-step-page/PerformanceByStepPage.es';
import PromisesResolver from '../../../src/main/resources/META-INF/resources/js/shared/components/promises-resolver/PromisesResolver.es';
import {MockRouter} from '../../mock/MockRouter.es';

const items = [
	{
		breachedInstanceCount: 4,
		durationAvg: 10800000,
		instanceCount: 4,
		node: {key: 'review', label: 'Review', name: 'Review'},
		onTimeInstanceCount: 4,
		overdueInstanceCount: 4,
	},
	{
		breachedInstanceCount: 2,
		durationAvg: 475200000,
		instanceCount: 2,
		node: {key: 'update', label: 'Update', name: 'Update'},
		onTimeInstanceCount: 2,
		overdueInstanceCount: 2,
	},
];

const wrapper = ({children}) => (
	<MockRouter>
		<PromisesResolver promises={[Promise.resolve()]}>
			{children}
		</PromisesResolver>
	</MockRouter>
);

describe('The performance by step page body should', () => {
	let getAllByRole;

	afterEach(cleanup);

	beforeEach(() => {
		const renderResult = render(
			<PerformanceByStepPage.Body
				{...{items, totalCount: items.length}}
				filtered={false}
			/>,
			{wrapper}
		);

		getAllByRole = renderResult.getAllByRole;
	});

	test('Be rendered with step names', () => {
		const rows = getAllByRole('row');

		expect(rows[1]).toHaveTextContent('Review');
		expect(rows[2]).toHaveTextContent('Update');
	});
});

describe('The subcomponents from workload by assignee page body should', () => {
	afterEach(cleanup);

	test('Be rendered with empty view and no content message', async () => {
		const {getByText} = render(
			<PerformanceByStepPage.Body items={[]} totalCount={0} />
		);

		const emptyStateMessage = getByText('there-is-no-data-at-the-moment');

		expect(emptyStateMessage).toBeTruthy();
	});

	test('Be rendered with empty view and no results message', async () => {
		const {getByText} = render(
			<PerformanceByStepPage.Body filtered items={[]} totalCount={0} />
		);

		const emptyStateMessage = getByText('no-results-were-found');

		expect(emptyStateMessage).toBeTruthy();
	});
});
