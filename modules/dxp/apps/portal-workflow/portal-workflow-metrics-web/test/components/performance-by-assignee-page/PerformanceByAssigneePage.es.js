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

import {render} from '@testing-library/react';
import React from 'react';

import PerformanceByAssigneePage from '../../../src/main/resources/META-INF/resources/js/components/performance-by-assignee-page/PerformanceByAssigneePage.es';
import {MockRouter} from '../../mock/MockRouter.es';

import '@testing-library/jest-dom/extend-expect';

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

const data = {items, totalCount: items.length};

const clientMock = {
	get: jest.fn().mockResolvedValue({data}),
	post: jest.fn().mockResolvedValue({data}),
	request: jest.fn().mockResolvedValue({data}),
};

const wrapper = ({children}) => (
	<MockRouter client={clientMock}>{children}</MockRouter>
);

describe('The PerformanceByAssigneePage component having data should', () => {
	let getAllByTestId;

	beforeAll(() => {
		const renderResult = render(
			<PerformanceByAssigneePage routeParams={{processId: 12345}} />,
			{wrapper}
		);

		getAllByTestId = renderResult.getAllByTestId;
	});

	test('Be rendered with user avatar or lexicon user icon', async () => {
		const assigneeProfileInfo = getAllByTestId('assigneeProfileInfo');

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
		const assigneeName = getAllByTestId('assigneeName');

		expect(assigneeName[0]).toHaveTextContent('User Test First');
		expect(assigneeName[1]).toHaveTextContent('User Test Second');
		expect(assigneeName[2]).toHaveTextContent('User Test Third');
	});

	test('Be rendered with average completion time', () => {
		const durations = getAllByTestId('durationTaskAvg');

		expect(durations[0]).toHaveTextContent('3h');
		expect(durations[1]).toHaveTextContent('5d 12h');
		expect(durations[2]).toHaveTextContent('0min');
	});
});
