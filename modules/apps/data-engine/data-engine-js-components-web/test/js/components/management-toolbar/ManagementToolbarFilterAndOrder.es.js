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

import ManagementToolbarFilterAndOrder from '../../../../src/main/resources/META-INF/resources/js/components/management-toolbar/ManagementToolbarFilterAndOrder.es';
import SearchContextProviderWrapper from '../../SearchContextProviderWrapper.es';
import {FILTERS} from '../../constants.es';

describe('ManagementToolbarFilterAndOrder', () => {
	afterEach(cleanup);

	const columns = [
		{asc: false, key: 'field', sortable: true, value: 'field'},
		{key: 'field1', sortable: true, value: 'field1'},
	];

	it('renders empty', () => {
		const {container} = render(<ManagementToolbarFilterAndOrder />, {
			wrapper: SearchContextProviderWrapper,
		});

		expect(container.innerHTML).toBe('');
	});

	it('renders disabled', () => {
		const {container} = render(
			<ManagementToolbarFilterAndOrder columns={columns} disabled />,
			{wrapper: SearchContextProviderWrapper}
		);

		const filter = container.querySelector('#filter-and-order');
		const sort = container.querySelector(
			'ul .nav-item:nth-child(2) button'
		);

		expect(filter.disabled).toBeTruthy();
		expect(sort.disabled).toBeTruthy();
	});

	it('renders', () => {
		const {container, queryByText} = render(
			<ManagementToolbarFilterAndOrder columns={columns} />,
			{
				wrapper: SearchContextProviderWrapper,
			}
		);

		const field = queryByText('field');
		const field1 = queryByText('field1');

		expect(field).toBeTruthy();
		expect(field1).toBeTruthy();
		expect(field.classList).toContain('active');
		expect(field1.classList).not.toContain('active');

		const sort = container.querySelector(
			'ul .nav-item:nth-child(2) button'
		);

		expect(sort.classList).toContain('order-arrow-down-active');

		sort.click();

		expect(sort.classList).toContain('order-arrow-up-active');
	});

	it('renders with filters and without addButton', () => {
		const dispatch = jest.fn();

		const columns = [
			{
				key: 'field1',
			},
			{
				key: 'field2',
				sortable: true,
				value: 'field2',
			},
			{
				asc: true,
				key: 'field3',
				sortable: true,
				value: 'field3',
			},
		];

		const query = {
			filters: {
				deploymentTypes: ['standalone'],
			},
			keywords: '',
		};

		const {
			container,
			queryAllByLabelText,
			queryByLabelText,
			queryByText,
		} = render(
			<SearchContextProviderWrapper
				defaultQuery={query}
				dispatch={dispatch}
			>
				<ManagementToolbarFilterAndOrder
					columns={columns}
					filters={FILTERS}
				/>
			</SearchContextProviderWrapper>
		);

		const anyOption = queryByLabelText('any');
		const doneButton = queryByText('done');

		const field1 = queryByLabelText('field1');
		const field2 = queryByLabelText('field2');
		const field3 = queryByLabelText('field3');

		const filterAndOrder = queryByText('filter-and-order');
		const deploymentTypes = queryAllByLabelText(
			/product menu|standalone|widget/i
		);
		const statuses = queryAllByLabelText(/deployed|undeployed/i);

		expect(doneButton).toBeTruthy();
		expect(field1).toBeFalsy();
		expect(field2).toBeTruthy();
		expect(field3).toBeTruthy();
		expect(field2.checked).toBeFalsy();
		expect(field3.checked).toBeTruthy();

		expect(filterAndOrder).toBeTruthy();
		expect(deploymentTypes.length).toBe(3);
		expect(deploymentTypes[0].checked).toBeFalsy();
		expect(deploymentTypes[1].checked).toBeTruthy();
		expect(deploymentTypes[2].checked).toBeFalsy();

		expect(statuses.length).toBe(2);
		expect(anyOption).toBeTruthy();
		expect(anyOption.checked).toBeTruthy();
		expect(statuses[0].checked).toBeFalsy();
		expect(statuses[1].checked).toBeFalsy();

		fireEvent.click(deploymentTypes[0]);
		fireEvent.click(deploymentTypes[1]);
		fireEvent.click(deploymentTypes[2]);

		expect(deploymentTypes[0].checked).toBeTruthy();
		expect(deploymentTypes[1].checked).toBeFalsy();
		expect(deploymentTypes[2].checked).toBeTruthy();

		fireEvent.click(statuses[0]);

		expect(anyOption.checked).toBeFalsy();
		expect(statuses[0].checked).toBeTruthy();
		expect(statuses[1].checked).toBeFalsy();

		fireEvent.click(field2);

		expect(field2.checked).toBeTruthy();
		expect(field3.checked).toBeFalsy();

		fireEvent.click(doneButton);

		expect(dispatch.mock.calls.length).toBe(1);
		expect(dispatch.mock.calls[0][0]).toEqual({
			filters: {
				active: 'true',
				deploymentTypes: ['productMenu', 'widget'],
			},
			sort: 'field2:asc',
			type: 'UPDATE_FILTERS_AND_SORT',
		});

		const sort = container.querySelector(
			'ul .nav-item:nth-child(2) button'
		);

		fireEvent.click(sort);

		expect(dispatch.mock.calls.length).toBe(2);
		expect(dispatch.mock.calls[1][0]).toEqual({
			sort: 'field2:desc',
			type: 'SORT',
		});
	});
});
