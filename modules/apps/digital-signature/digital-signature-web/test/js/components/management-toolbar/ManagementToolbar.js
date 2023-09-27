/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';

import ManagementToolbar from '../../../../src/main/resources/META-INF/resources/js/components/management-toolbar/ManagementToolbar';
import SearchContextProviderWrapper from '../../SearchContextProviderWrapper';

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
