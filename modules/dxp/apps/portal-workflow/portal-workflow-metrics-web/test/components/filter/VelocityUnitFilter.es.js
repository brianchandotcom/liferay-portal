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

import VelocityUnitFilter from '../../../src/main/resources/META-INF/resources/js/components/filter/VelocityUnitFilter.es';
import {MockRouter} from '../../mock/MockRouter.es';

const query = '?filters.velocityUnit%5B0%5D=weeks';

const wrapper = ({children}) => (
	<MockRouter query={query}>{children}</MockRouter>
);

describe('The velocity unit filter component should', () => {
	let getAllByTestId;

	afterEach(cleanup);

	beforeEach(() => {
		const renderResult = render(
			<VelocityUnitFilter
				dispatch={() => {}}
				processId={12345}
				timeRange={{
					dateEnd: '2019-12-10T20:19:34Z',
					dateStart: '2019-09-12T00:00:00Z'
				}}
			/>,
			{wrapper}
		);

		getAllByTestId = renderResult.getAllByTestId;
	});

	test('Be rendered with filter item names', async () => {
		const filterItems = await getAllByTestId('filterItem');

		expect(filterItems[0].innerHTML).toContain('inst-day');
		expect(filterItems[1].innerHTML).toContain('inst-week');
		expect(filterItems[2].innerHTML).toContain('inst-month');
	});

	test('Be rendered with active option "Weeks"', async () => {
		const filterItems = getAllByTestId('filterItem');

		const activeItem = filterItems.find(item =>
			item.className.includes('active')
		);
		const activeItemName = await findByTestId(activeItem, 'filterItemName');

		expect(activeItemName.innerHTML).toBe('inst-week');
	});
});
