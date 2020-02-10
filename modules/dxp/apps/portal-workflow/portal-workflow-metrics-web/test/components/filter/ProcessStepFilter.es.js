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

import ProcessStepFilter from '../../../src/main/resources/META-INF/resources/js/components/filter/ProcessStepFilter.es';
import {MockRouter} from '../../mock/MockRouter.es';

const query = '?filters.taskKeys%5B0%5D=update';

const items = [
	{key: 'review', name: 'Review'},
	{key: 'update', name: 'Update'}
];

const clientMock = {
	get: jest.fn().mockResolvedValue({data: {items, totalCount: items.length}})
};

const wrapper = ({children}) => (
	<MockRouter client={clientMock} query={query}>
		{children}
	</MockRouter>
);

describe('The process step filter component should', () => {
	let getAllByTestId;

	afterEach(cleanup);

	beforeEach(() => {
		const renderResult = render(
			<ProcessStepFilter dispatch={() => {}} processId={12345} />,
			{wrapper}
		);

		getAllByTestId = renderResult.getAllByTestId;
	});

	test('Be rendered with filter item names', async () => {
		const filterItems = await getAllByTestId('filterItem');

		expect(filterItems[0].innerHTML).toContain('Review');
		expect(filterItems[1].innerHTML).toContain('Update');
	});

	test('Be rendered with active option "Update"', async () => {
		const filterItems = getAllByTestId('filterItem');

		const activeItem = filterItems.find(item =>
			item.className.includes('active')
		);
		const activeItemName = await findByTestId(activeItem, 'filterItemName');

		expect(activeItemName.innerHTML).toBe('Update');
	});
});
