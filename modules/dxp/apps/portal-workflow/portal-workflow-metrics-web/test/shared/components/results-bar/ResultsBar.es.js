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

import ResultsBar from '../../../../src/main/resources/META-INF/resources/js/shared/components/results-bar/ResultsBar.es';
import {MockRouter as Router} from '../../../mock/MockRouter.es';

describe('The ResultsBar component should', () => {
	const mockProps = {
		page: 1,
		pageSize: 1,
		sort: encodeURIComponent('overdueTaskCount:asc')
	};

	const mockQuery = '?filters.taskKeys[0]=review&filters.taskKeys[1]=update';

	afterEach(cleanup);

	test('Render with search value "test" and total count "1"', () => {
		const {getByTestId} = render(
			<Router>
				<ResultsBar>
					<ResultsBar.TotalCount search={'test'} totalCount={1} />

					<ResultsBar.Clear {...mockProps} />
				</ResultsBar>
			</Router>
		);

		const clearAll = getByTestId('clearAll');

		const totalCount = getByTestId('totalCount');
		expect(totalCount.innerHTML).toBe('x-result-for-x');
		expect(clearAll.getAttribute('href')).toBe('/?backPath=%2F&filters=');
	});

	test('Render with search value "test" and with 2 selected filter item', async () => {
		const mockFilters = [
			{
				items: [
					{active: true, key: 'review', name: 'Review'},
					{active: true, key: 'update', name: 'Update'}
				],
				key: 'taskKeys',
				name: 'Process Step',
				pinned: false
			}
		];

		const {getAllByTestId, getByTestId} = render(
			<Router query={mockQuery}>
				<ResultsBar>
					<ResultsBar.TotalCount totalCount={2} />

					<ResultsBar.FilterItems
						filters={mockFilters}
						{...mockProps}
					/>

					<ResultsBar.Clear {...mockProps} />
				</ResultsBar>
			</Router>
		);

		const removeFilter = getAllByTestId('removeFilter');
		const totalCount = getByTestId('totalCount');

		expect(removeFilter.length).toBe(2);

		expect(removeFilter[0].getAttribute('href')).toBe(
			'/?filters.taskKeys%5B0%5D=update'
		);

		expect(removeFilter[1].getAttribute('href')).toBe(
			'/?filters.taskKeys%5B0%5D=review'
		);

		expect(totalCount.innerHTML).toBe('x-results-for-x');
	});
});
