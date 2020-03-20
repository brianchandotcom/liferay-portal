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

import {AppContext} from '../../../../src/main/resources/META-INF/resources/js/components/AppContext.es';
import PerformanceByAssigneeCard from '../../../../src/main/resources/META-INF/resources/js/components/process-metrics/performance-by-assignee-card/PerformanceByAssigneeCard.es';
import {MockRouter} from '../../../mock/MockRouter.es';

const wrapper = ({children}) => (
	<AppContext.Provider value={{defaultDelta: 20}}>
		<MockRouter>{children}</MockRouter>
	</AppContext.Provider>
);

describe('The performance by assignee body component with data should', () => {
	let getAllByTestId;

	const items = [
		{
			assignee: {
				image: 'path/to/image',
				name: 'User Test First',
			},
			durationTaskAvg: 10800000,
			taskCount: 10,
		},
		{
			assignee: {
				image: 'path/to/image',
				name: 'User Test Second',
			},
			durationTaskAvg: 475200000,
			taskCount: 31,
		},
		{
			assignee: {
				name: 'User Test Third',
			},
			durationTaskAvg: 0,
			taskCount: 1,
		},
	];
	const data = {items, totalCount: items.length};

	afterEach(cleanup);

	beforeEach(() => {
		const renderResult = render(
			<PerformanceByAssigneeCard.Body {...data} />,
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

	test('Be rendered with assignee name', async () => {
		const assigneeName = await waitForElement(() =>
			getAllByTestId('assigneeName')
		);

		expect(assigneeName[0].innerHTML).toEqual('User Test First');
		expect(assigneeName[1].innerHTML).toEqual('User Test Second');
		expect(assigneeName[2].innerHTML).toEqual('User Test Third');
	});

	test('Be rendered with average completion time', async () => {
		const durations = await waitForElement(() =>
			getAllByTestId('durationTaskAvg')
		);

		expect(durations[0].innerHTML).toEqual('3h');
		expect(durations[1].innerHTML).toEqual('5d 12h');
		expect(durations[2].innerHTML).toEqual('0min');
	});
});

describe('The performance by assignee body component without data should', () => {
	let getByTestId;

	const items = [];
	const data = {items, totalCount: 0};

	afterEach(cleanup);

	beforeEach(() => {
		const renderResult = render(
			<PerformanceByAssigneeCard.Body {...data} />,
			{wrapper}
		);

		getByTestId = renderResult.getByTestId;
	});

	test('Render empty state', async () => {
		const emptyStateDiv = await waitForElement(() =>
			getByTestId('emptyState')
		);

		expect(emptyStateDiv.children[0].children[0].innerHTML).toBe(
			'there-is-no-data-at-the-moment'
		);
	});
});
