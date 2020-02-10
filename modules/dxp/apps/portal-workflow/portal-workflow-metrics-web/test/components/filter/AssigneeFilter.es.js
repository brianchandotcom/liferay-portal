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

import {cleanup, findByTestId, render} from '@testing-library/react';
import React from 'react';

import AssigneeFilter from '../../../src/main/resources/META-INF/resources/js/components/filter/AssigneeFilter.es';
import {MockRouter} from '../../mock/MockRouter.es';

const query = '?filters.assigneeUserIds%5B0%5D=1';

const items = [
	{id: 1, name: 'User 1'},
	{id: 2, name: 'User 2'}
];

const clientMock = {
	get: jest.fn().mockResolvedValue({data: {items, totalCount: items.length}})
};

const wrapper = ({children}) => (
	<MockRouter client={clientMock} query={query}>
		{children}
	</MockRouter>
);

describe('The assignee filter component should', () => {
	let getAllByTestId;

	afterEach(cleanup);

	beforeEach(() => {
		const renderResult = render(
			<AssigneeFilter dispatch={() => {}} processId={12345} />,
			{wrapper}
		);

		getAllByTestId = renderResult.getAllByTestId;
	});

	test('Be rendered with filter item names', () => {
		const filterItems = getAllByTestId('filterItem');

		expect(filterItems[0].innerHTML).toContain('unassigned');
		expect(filterItems[1].innerHTML).toContain('User 1');
		expect(filterItems[2].innerHTML).toContain('User 2');
	});

	test('Be rendered with active option "User 1"', () => {
		const filterItems = getAllByTestId('filterItem');

		const activeItem = filterItems.find(item =>
			item.className.includes('active')
		);

		findByTestId(activeItem, 'filterItemName').then(activeItemName => {
			expect(activeItemName.innerHTML).toBe('User 1');
		});
	});
});
