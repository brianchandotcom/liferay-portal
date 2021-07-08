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
import {act, render} from '@testing-library/react';
import React from 'react';

import ProcessVersionFilter from '../../../src/main/resources/META-INF/resources/js/components/filter/ProcessVersionFilter.es';
import {MockRouter} from '../../mock/MockRouter.es';

const query = '?filters.processVersion%5B0%5D=1.0';

const items = [{name: '1.0'}, {name: '2.0'}];

const clientMock = {
	request: jest
		.fn()
		.mockResolvedValue({data: {items, totalCount: items.length}}),
};

const wrapper = ({children}) => (
	<MockRouter client={clientMock} query={query}>
		{children}
	</MockRouter>
);

describe('The process version filter component should', () => {
	beforeEach(async () => {
		render(
			<ProcessVersionFilter
				options={{
					hideControl: true,
					multiple: false,
					withAllVersions: true,
					withSelectionTitle: true,
				}}
				processId={12345}
			/>,
			{
				wrapper,
			}
		);

		await act(async () => {
			jest.runAllTimers();
		});
	});

	it('Render with filter item names', () => {
		const filterItems = document.querySelectorAll('.dropdown-item');

		expect(filterItems[0].innerHTML).toContain('all-versions');
		expect(filterItems[1].innerHTML).toContain('1.0');
		expect(filterItems[2].innerHTML).toContain('2.0');
	});

	it('Render with active option "1.0"', () => {
		const activeItem = document.querySelector('.active');

		expect(activeItem).toHaveTextContent('1.0');
	});
});
