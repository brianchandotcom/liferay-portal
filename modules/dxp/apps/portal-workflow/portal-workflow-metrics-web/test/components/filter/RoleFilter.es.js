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
import {act, cleanup, render} from '@testing-library/react';
import React from 'react';

import RoleFilter from '../../../src/main/resources/META-INF/resources/js/components/filter/RoleFilter.es';
import {MockRouter} from '../../mock/MockRouter.es';

const query = '?filters.roleIds%5B0%5D=2';

const items = [
	{id: 1, name: 'Admin'},
	{id: 2, name: 'User'},
];

const wrapper = ({children}) => (
	<MockRouter query={query}>{children}</MockRouter>
);

describe('The role filter component should', () => {
	afterEach(cleanup);

	beforeEach(async () => {
		fetch.mockResolvedValueOnce({
			json: () => Promise.resolve({items, totalCount: items.length}),
			ok: true,
		});

		render(<RoleFilter processId={12345} />, {
			wrapper,
		});

		await act(async () => {
			jest.runAllTimers();
		});
	});

	it('Be rendered with filter item names', () => {
		const filterItems = document.querySelectorAll('.dropdown-item');

		expect(filterItems[0].innerHTML).toContain('Admin');
		expect(filterItems[1].innerHTML).toContain('User');
	});

	it('Be rendered with active option "User"', () => {
		const activeItem = document.querySelector('.active');

		expect(activeItem).toHaveTextContent('User');
	});
});
