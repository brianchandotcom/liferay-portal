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
import {MockRouter} from '../../../mock/MockRouter.es';
import {ProcessStepContext} from '../../../../src/main/resources/META-INF/resources/js/components/process-metrics/filter/store/ProcessStepStore.es';
import ProcessStepFilter from '../../../../src/main/resources/META-INF/resources/js/components/process-metrics/filter/ProcessStepFilter.es';
import React from 'react';

const processSteps = [
	{
		key: 'review',
		name: 'Review'
	},
	{
		key: 'update',
		name: 'Update'
	}
];

describe('The time range filter should', () => {
	let getAllByTestId;

	afterEach(cleanup);

	beforeEach(() => {
		const renderResult = render(
			<MockRouter>
				<ProcessStepContext.Provider
					value={{getSelectedProcessSteps: jest.fn(), processSteps}}
				>
					<ProcessStepFilter />
				</ProcessStepContext.Provider>
			</MockRouter>
		);

		getAllByTestId = renderResult.getAllByTestId;
	});

	test('Be rendered with "Review" and "Update" items', () => {
		const filterItemNames = getAllByTestId('filterItemName');

		expect(filterItemNames[0].innerHTML).toBe('Review');
		expect(filterItemNames[1].innerHTML).toBe('Update');
	});
});
