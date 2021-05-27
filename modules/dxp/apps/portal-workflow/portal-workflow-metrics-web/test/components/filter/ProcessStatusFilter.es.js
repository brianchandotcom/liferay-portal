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

import ProcessStatusFilter from '../../../src/main/resources/META-INF/resources/js/components/filter/ProcessStatusFilter.es';
import {MockRouter} from '../../mock/MockRouter.es';

const query = '?filters.statuses%5B0%5D=Completed';

const wrapper = ({children}) => (
	<MockRouter query={query}>{children}</MockRouter>
);

describe('The process status filter component should', () => {
	afterEach(cleanup);

	beforeEach(() => {
		render(<ProcessStatusFilter processId={12345} />, {
			wrapper,
		});
	});

	test('Be rendered with filter item names', () => {
		const filterItems = document.querySelectorAll('.dropdown-item');

		expect(filterItems[0].innerHTML).toContain('completed');
		expect(filterItems[1].innerHTML).toContain('pending');
	});

	test('Be rendered with active option "Completed"', async () => {
		const activeItem = document.querySelector('.active');

		expect(activeItem).toHaveTextContent('completed');
	});
});
