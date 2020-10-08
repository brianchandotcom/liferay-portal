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

import ProcessListPage from '../../../src/main/resources/META-INF/resources/js/components/process-list-page/ProcessListPage.es';
import {MockRouter} from '../../mock/MockRouter.es';

describe('The process list page component having data should', () => {
	let container;

	const items = [
		{
			instancesCount: 0,
			process: {
				title: 'Single Approver 1',
			},
		},
		{
			instancesCount: 0,
			process: {
				title: 'Single Approver 2',
			},
		},
		{
			instancesCount: 0,
			process: {
				title: 'Single Approver 3',
			},
		},
	];
	const data = {items, totalCount: items.length};

	const clientMock = {
		get: jest.fn().mockResolvedValue({data}),
	};

	const routeParams = {
		page: 1,
		pageSize: 20,
		query: '',
		sort: 'overdueInstanceCount%3Adesc',
	};

	afterEach(cleanup);

	const wrapper = ({children}) => (
		<MockRouter client={clientMock}>{children}</MockRouter>
	);

	beforeEach(() => {
		const renderResult = render(
			<ProcessListPage routeParams={routeParams} />,
			{wrapper}
		);

		container = renderResult.container;
	});

	test('Be rendered with process names', () => {
		const processName = container.querySelectorAll('.table-title');

		expect(processName[0]).toHaveTextContent('Single Approver 1');
		expect(processName[1]).toHaveTextContent('Single Approver 2');
		expect(processName[2]).toHaveTextContent('Single Approver 3');
	});
});
