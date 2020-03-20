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
import {MockRouter} from '../../mock/MockRouter.es';

describe('The process list page component having data should', () => {
	let getAllByTestId;

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
