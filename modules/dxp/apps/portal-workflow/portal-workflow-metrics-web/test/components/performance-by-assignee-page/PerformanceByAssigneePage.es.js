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

import PerformanceByAssigneePage from '../../../src/main/resources/META-INF/resources/js/components/performance-by-assignee-page/PerformanceByAssigneePage.es';
import {MockRouter} from '../../mock/MockRouter.es';

describe('The PerformanceByAssigneePage component having data should', () => {
	let getAllByTestId;

	const items = [
		{
			durationTaskAvg: 10800000,
			image: 'path/to/image',
			name: 'User Test First',
			taskCount: 10
		},
		{
			durationTaskAvg: 475200000,
			image: 'path/to/image',
			name: 'User Test Second',
			taskCount: 31
		},
		{
			durationTaskAvg: 0,
			name: 'User Test Third',
			taskCount: 1
		}
	];

	const data = {items, totalCount: items.length};

	const clientMock = {
		get: jest.fn().mockResolvedValue({data})
	};

	afterEach(() => cleanup);

	const wrapper = ({children}) => (
		<MockRouter client={clientMock}>{children}</MockRouter>
	);

	beforeEach(() => {
		const renderResult = render(
			<PerformanceByAssigneePage routeParams={{processId: '1234'}} />,
			{wrapper}
		);

		getAllByTestId = renderResult.getAllByTestId;
	});

	test('Be rendered with user avatar or lexicon user icon', async () => {
		const assigneeProfileInfo = await waitForElement(() =>
			getAllByTestId('assigneeProfileInfo')
		);

		expect(assigneeProfileInfo[0].children[0].innerHTML).toContain(
			'path/to/image'
		);
		expect(assigneeProfileInfo[1].children[0].innerHTML).toContain(
			'path/to/image'
		);
		expect(assigneeProfileInfo[2].children[0].innerHTML).toContain(
			'lexicon-icon-user'
		);
	});

	test('Be rendered with assignee names', async () => {
		const assigneeName = await waitForElement(() =>
			getAllByTestId('assigneeName')
		);

		expect(assigneeName[0].innerHTML).toEqual('User Test First');
		expect(assigneeName[1].innerHTML).toEqual('User Test Second');
		expect(assigneeName[2].innerHTML).toEqual('User Test Third');
	});

	test('Be rendered with average completion time', () => {
		const durations = getAllByTestId('durationTaskAvg');

		expect(durations[0].innerHTML).toEqual('3h');
		expect(durations[1].innerHTML).toEqual('5d 12h');
		expect(durations[2].innerHTML).toEqual('0min');
	});
});
