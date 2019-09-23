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
import {AssigneeContext} from '../../../../src/main/resources/META-INF/resources/js/components/process-metrics/filter/store/AssigneeStore.es';
import AssigneeFilter from '../../../../src/main/resources/META-INF/resources/js/components/process-metrics/filter/AssigneeFilter.es';
import {MockRouter} from '../../../mock/MockRouter.es';
import React from 'react';

const assignees = [
	{
		id: 1,
		key: '1',
		name: 'User 1'
	},
	{
		id: 2,
		key: '2',
		name: 'User 2'
	}
];

describe('The assignee filter should', () => {
	let getAllByTestId;

	afterEach(cleanup);

	beforeEach(() => {
		const renderResult = render(
			<MockRouter>
				<AssigneeContext.Provider value={{assignees}}>
					<AssigneeFilter />
				</AssigneeContext.Provider>
			</MockRouter>
		);

		getAllByTestId = renderResult.getAllByTestId;
	});

	test('Be rendered with "User 1" and "User 2" items', () => {
		const filterItemNames = getAllByTestId('filterItemName');

		expect(filterItemNames[0].innerHTML).toBe(
			Liferay.Language.get('User 1')
		);
		expect(filterItemNames[1].innerHTML).toBe(
			Liferay.Language.get('User 2')
		);
	});
});
