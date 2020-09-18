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

import {AppContext} from '../../../../src/main/resources/META-INF/resources/js/components/AppContext.es';
import PerformanceByStepCard from '../../../../src/main/resources/META-INF/resources/js/components/process-metrics/performance-by-step-card/PerformanceByStepCard.es';
import {MockRouter} from '../../../mock/MockRouter.es';

const items = [
	{
		breachedInstanceCount: 3,
		breachedInstancePercentage: 30,
		durationAvg: 10800000,
		node: {
			label: 'Review',
			name: 'review',
		},
	},
	{
		breachedInstanceCount: 7,
		breachedInstancePercentage: 22.5806,
		durationAvg: 475200000,
		node: {
			label: 'Update',
			name: 'update',
		},
	},
	{
		breachedInstanceCount: 0,
		breachedInstancePercentage: 0,
		durationAvg: 0,
		node: {
			label: 'Translate',
			name: 'translate',
		},
	},
];

describe('The performance by step body component should', () => {
	let cells, container, getAllByRole;

	afterEach(cleanup);

	beforeEach(() => {
		const wrapper = ({children}) => (
			<AppContext.Provider value={{defaultDelta: 20}}>
				<MockRouter>{children}</MockRouter>
			</AppContext.Provider>
		);
		const renderResult = render(
			<PerformanceByStepCard.Body
				{...{items, totalCount: items.length}}
				processId={123456}
			/>,
			{wrapper}
		);

		container = renderResult.container;
		getAllByRole = renderResult.getAllByRole;
	});

	test('Be rendered with "Review" and "Update" names', () => {
		const stepNames = container.querySelectorAll('.table-cell-expand');

		expect(stepNames[0]).toHaveTextContent('Review');
		expect(stepNames[1]).toHaveTextContent('Update');
	});

	test('Be rendered with "30%" and "22.58%" percentages', () => {
		cells = getAllByRole('cell');

		expect(cells[1]).toHaveTextContent('3 (30%)');
		expect(cells[4]).toHaveTextContent('7 (22.58%)');
	});

	test('Be rendered with "3h" and "5d 12h" durations', () => {
		expect(cells[2]).toHaveTextContent('3h');
		expect(cells[5]).toHaveTextContent('5d 12h');
	});
});
