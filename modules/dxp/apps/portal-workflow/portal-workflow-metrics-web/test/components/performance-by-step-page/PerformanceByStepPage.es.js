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

import PerformanceByStepPage from '../../../src/main/resources/META-INF/resources/js/components/performance-by-step-page/PerformanceByStepPage.es';
import {MockRouter} from '../../mock/MockRouter.es';

describe('The PerformanceByStepPage component having data should', () => {
	let getAllByTestId;

	const items = [
		{
			breachedInstanceCount: 4,
			durationAvg: 10800000,
			instanceCount: 4,
			key: 'review',
			name: 'Review',
			onTimeInstanceCount: 4,
			overdueInstanceCount: 4
		},
		{
			breachedInstanceCount: 2,
			durationAvg: 475200000,
			instanceCount: 2,
			key: 'update',
			name: 'Update',
			onTimeInstanceCount: 2,
			overdueInstanceCount: 2
		}
	];

	const data = {items, totalCount: items.length};

	const clientMock = {
		get: jest.fn().mockResolvedValue({data})
	};

	afterEach(cleanup);

	const wrapper = ({children}) => (
		<MockRouter client={clientMock} getClient={jest.fn(() => clientMock)}>
			{children}
		</MockRouter>
	);

	beforeEach(() => {
		const renderResult = render(
			<PerformanceByStepPage routeParams={{processId: '1234'}} />,
			{wrapper}
		);

		getAllByTestId = renderResult.getAllByTestId;
	});

	test('Be rendered with step names', async () => {
		const stepName = await waitForElement(() => getAllByTestId('stepName'));

		expect(stepName[0].innerHTML).toEqual('Review');
		expect(stepName[1].innerHTML).toEqual('Update');
	});

	test('Be rendered with SLA Breached (%)', () => {
		const slas = getAllByTestId('stepSla');

		expect(slas[0].innerHTML).toEqual('4 (0%)');
		expect(slas[1].innerHTML).toEqual('2 (0%)');
	});

	test('Be rendered with average completion time', () => {
		const durations = getAllByTestId('durationTaskAvg');

		expect(durations[0].innerHTML).toEqual('3h');
		expect(durations[1].innerHTML).toEqual('5d 12h');
	});
});
