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

import WorkloadByStepCard from '../../../../src/main/resources/META-INF/resources/js/components/process-metrics/workload-by-step-card/WorkloadByStepCard.es';
import {MockRouter} from '../../../mock/MockRouter.es';

import '@testing-library/jest-dom/extend-expect';

const routeParams = {
	page: 1,
	pageSize: 10,
	processId: '12345',
	sort: 'overdueTaskCount:desc'
};

describe('The WorkloadByStepCard component should', () => {
	afterAll(cleanup);

	const data = {
		items: [
			{
				instanceCount: 1,
				name: 'Task Name',
				onTimeInstanceCount: 1,
				overdueInstanceCount: 0
			}
		],
		totalCount: 1
	};

	let getByTestId;

	const clientMock = {
		get: jest.fn().mockResolvedValue({data})
	};

	beforeAll(() => {
		const component = render(
			<MockRouter client={clientMock}>
				<WorkloadByStepCard {...routeParams}></WorkloadByStepCard>
			</MockRouter>
		);

		getByTestId = component.getByTestId;
	});

	test('Load table component with request data and navigation links', () => {
		const workloadByStepTable = getByTestId('workloadByStepTable');
		const tableItems = workloadByStepTable.children[1].children[0].children;

		expect(tableItems[0]).toHaveTextContent('Task Name');
		expect(tableItems[1]).toHaveTextContent(0);
		expect(tableItems[2]).toHaveTextContent(1);
		expect(tableItems[3]).toHaveTextContent(1);

		expect(tableItems[1].innerHTML).toContain(
			'/instance/12345/20/1?backPath=%2F1%2F20%2Ftitle%253Aasc%3FbackPath%3D%252F&amp;filters.statuses%5B0%5D=Pending&amp;filters.slaStatuses%5B0%5D=Overdue'
		);
		expect(tableItems[2].innerHTML).toContain(
			'/instance/12345/20/1?backPath=%2F1%2F20%2Ftitle%253Aasc%3FbackPath%3D%252F&amp;filters.statuses%5B0%5D=Pending&amp;filters.slaStatuses%5B0%5D=OnTime'
		);
		expect(tableItems[3].innerHTML).toContain(
			'/instance/12345/20/1?backPath=%2F1%2F20%2Ftitle%253Aasc%3FbackPath%3D%252F&amp;filters.statuses%5B0%5D=Pending'
		);
	});
});

describe('When request fails the WorkloadByStepCard component should', () => {
	const clientMock = {
		get: jest.fn().mockRejectedValueOnce(new Error('request-failure'))
	};
	let getByTestId;

	beforeAll(() => {
		const component = render(
			<MockRouter client={clientMock}>
				<WorkloadByStepCard {...routeParams}></WorkloadByStepCard>
			</MockRouter>
		);

		getByTestId = component.getByTestId;
	});

	test('Show error view when request fails', () => {
		const errorState = getByTestId('emptyState');
		expect(errorState).toHaveTextContent(
			'there-was-a-problem-retrieving-data-please-try-reloading-the-page'
		);

		const reloadPage = getByTestId('reloadPage');
		expect(reloadPage).toHaveTextContent('reload-page');
	});
});

describe('When request is pending the WorkloadByStepCard component should', () => {
	test('Show loading view', () => {
		const {getByTestId} = render(<WorkloadByStepCard.Loading />);

		const loadingState = getByTestId('loadingState');
		expect(loadingState.children[0]).toHaveClass('loading-animation');
		cleanup();
	});
});

describe('When request returns do data the WorkloadByStepCard component should', () => {
	const data = {};

	const clientMock = {
		get: jest.fn().mockResolvedValue({data})
	};

	let getByTestId;

	beforeAll(() => {
		const component = render(
			<MockRouter client={clientMock}>
				<WorkloadByStepCard />
			</MockRouter>
		);
		getByTestId = component.getByTestId;
	});

	test('Show empty view', () => {
		const emptyState = getByTestId('emptyState');
		expect(emptyState).toHaveTextContent(
			'there-are-no-pending-items-at-the-moment'
		);
	});
});
