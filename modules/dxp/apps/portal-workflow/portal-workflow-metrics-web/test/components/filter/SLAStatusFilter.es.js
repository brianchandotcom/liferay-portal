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

import SLAStatusFilter from '../../../src/main/resources/META-INF/resources/js/components/filter/SLAStatusFilter.es';
import {MockRouter} from '../../mock/MockRouter.es';

const query = '?filters.slaStatuses%5B0%5D=Overdue';

const wrapper = ({children}) => (
	<MockRouter query={query}>{children}</MockRouter>
);

describe('The sla status filter component should', () => {
	afterEach(cleanup);

	beforeEach(() => {
		render(<SLAStatusFilter processId={12345} />, {
			wrapper,
		});
	});

	test('Be rendered with filter item names', () => {
		const filterItems = document.querySelectorAll('.dropdown-item');

		expect(filterItems[0].innerHTML).toContain('on-time');
		expect(filterItems[1].innerHTML).toContain('overdue');
		expect(filterItems[2].innerHTML).toContain('untracked');
	});

	test('Be rendered with active option "Overdue"', () => {
		const activeItem = document.querySelector('.active');

		expect(activeItem).toHaveTextContent('overdue');
	});
});
