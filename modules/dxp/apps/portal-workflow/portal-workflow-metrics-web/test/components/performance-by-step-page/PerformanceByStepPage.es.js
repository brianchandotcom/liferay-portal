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

import PerformanceByStepPage from '../../../src/main/resources/META-INF/resources/js/components/performance-by-step-page/PerformanceByStepPage.es';
import {MockRouter} from '../../mock/MockRouter.es';

const data = {
	items: [
		{
			breachedInstanceCount: 4,
			durationAvg: 0,
			instanceCount: 4,
			key: 'review',
			name: 'Review',
			onTimeInstanceCount: 0,
			overdueInstanceCount: 4
		}
	],
	totalCount: 1
};

const clientMock = {
	get: jest.fn()
};

const wrapper = ({children}) => (
	<MockRouter client={clientMock}>{children}</MockRouter>
);

describe('The performance by step page should', () => {
	let getAllByTestId;

	afterEach(cleanup);

	beforeEach(() => {
		clientMock.get.mockResolvedValue({data});

		const renderResult = render(
			<PerformanceByStepPage
				page={1}
				pageSize={10}
				processId="12345"
				sort="overdueInstanceCount:desc"
			/>,
			{
				wrapper
			}
		);

		getAllByTestId = renderResult.getAllByTestId;
	});

	test('Be rendered with 1 item on table', async () => {
		const stepNameCell = await getAllByTestId('stepName');

		expect(stepNameCell[0].innerHTML).toBe('Review');
	});
});

describe('The performance by step page, when there is no item, should', () => {
	let getByTestId;

	afterEach(cleanup);

	beforeEach(() => {
		clientMock.get.mockResolvedValue({data: {}});

		const renderResult = render(
			<PerformanceByStepPage
				page={1}
				pageSize={10}
				processId="12345"
				search="update"
				sort="overdueInstanceCount:desc"
			/>,
			{
				wrapper
			}
		);

		getByTestId = renderResult.getByTestId;
	});

	test('Be rendered with empty view after search', () => {
		const performanceByStepBody = getByTestId('performanceByStepBody');

		expect(
			performanceByStepBody.children[0].children[1].children[0].innerHTML
		).toBe('no-results-were-found');
	});
});
