/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';

import ManagementToolbar from '../../../../src/main/resources/META-INF/resources/js/components/management-toolbar/ManagementToolbar.es';
import SearchContext from '../../../../src/main/resources/META-INF/resources/js/components/management-toolbar/SearchContext.es';

const addButton = (onClick) => <button onClick={onClick}>add</button>;

describe('ManagementToolbar', () => {
	afterEach(() => {
		cleanup();
		jest.restoreAllMocks();
	});

	it('renders ManagementToolbar', () => {
		const dispatch = jest.fn();
		const onClickButtonCallback = jest.fn();
		const query = {
			keywords: '',
		};

		const columns = [
			{
				key: 'field1',
			},
			{
				asc: true,
				key: 'field2',
				sortable: true,
				value: 'field2',
			},
		];

		const {container, queryByText} = render(
			<SearchContext.Provider value={[query, dispatch]}>
				<ManagementToolbar
					addButton={() => addButton(onClickButtonCallback)}
					columns={columns}
				/>
			</SearchContext.Provider>
		);

		const dropDown = queryByText('filter-and-order');
		const field1 = queryByText('field1');
		const field2 = queryByText('field2');
		const add = queryByText('add');

		expect(field1).toBeFalsy();
		expect(field2).toBeTruthy();
		expect(dropDown).toBeTruthy();

		fireEvent.click(dropDown);
		fireEvent.click(field2);

		expect(dispatch.mock.calls.length).toBe(1);

		const sort = container.querySelector(
			'ul .nav-item:nth-child(2) button'
		);

		fireEvent.click(sort);

		expect(dispatch.mock.calls.length).toBe(2);

		const setMobile = container.querySelector(
			'.nav-item.navbar-breakpoint-d-none > button'
		);

		fireEvent.click(setMobile);
		fireEvent.click(add);

		expect(onClickButtonCallback.mock.calls.length).toBe(1);
	});
});
