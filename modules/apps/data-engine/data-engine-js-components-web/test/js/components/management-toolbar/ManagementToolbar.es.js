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
import SearchContextProviderWrapper from '../../SearchContextProviderWrapper.es';

const createAddButton = (onClick) => <button onClick={onClick}>add</button>;

describe('ManagementToolbar', () => {
	afterEach(cleanup);

	it('renders disabled', () => {
		const onClickButtonCallback = jest.fn();

		const {queryByPlaceholderText, queryByText} = render(
			<SearchContextProviderWrapper defaultQuery={{sort: 'field1:asc'}}>
				<ManagementToolbar
					addButton={() => createAddButton(onClickButtonCallback)}
					columns={[
						{key: 'field', sortable: true},
						{asc: true, key: 'field1', sortable: true},
					]}
					disabled
				/>
			</SearchContextProviderWrapper>
		);

		const addButton = queryByText('add');
		const filterAndOrder = queryByText('filter-and-order');
		const searchField = queryByPlaceholderText('search...');

		expect(addButton.disabled).toBeFalsy();
		expect(filterAndOrder.parentElement.disabled).toBeTruthy();
		expect(searchField.disabled).toBeTruthy();

		fireEvent.click(addButton);

		expect(onClickButtonCallback).toHaveBeenCalled();
	});

	it('renders without filter and order', () => {
		const {queryByPlaceholderText, queryByText} = render(
			<ManagementToolbar addButton={createAddButton} columns={[]} />,
			{wrapper: SearchContextProviderWrapper}
		);

		const addButton = queryByText('add');
		const filterAndOrder = queryByText('filter-and-order');
		const searchField = queryByPlaceholderText('search...');

		expect(addButton).toBeTruthy();
		expect(filterAndOrder).toBeFalsy();
		expect(searchField).toBeTruthy();
	});
});
