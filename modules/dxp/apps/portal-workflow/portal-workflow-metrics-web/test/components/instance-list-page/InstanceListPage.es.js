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

import {cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';

import InstanceListPage from '../../../src/main/resources/META-INF/resources/js/components/instance-list-page/InstanceListPage.es';
import {MockRouter} from '../../mock/MockRouter.es';

const items = [
	{
		assetTitle: 'New Post 1',
		assetType: 'Blog',
		dateCreated: new Date('2019-01-01'),
		id: 1,
		taskNames: []
	},
	{
		assetTitle: 'New Post 2',
		assetType: 'Blog',
		creatorUser: {
			name: 'User 1'
		},
		dateCreated: new Date('2019-01-03'),
		id: 2,
		taskNames: ['Update']
	}
];

describe('The instance list card should', () => {
	const clientMock = {
		get: jest
			.fn()
			.mockResolvedValue({data: {items, totalCount: items.length}})
	};
	let getByTestId, getAllByTestId;

	afterEach(cleanup);

	beforeEach(() => {
		const renderResult = render(
			<MockRouter
				client={clientMock}
				getClient={jest.fn(() => clientMock)}
			>
				<InstanceListPage />
			</MockRouter>
		);

		getByTestId = renderResult.getByTestId;
		getAllByTestId = renderResult.getAllByTestId;
	});

	test('Be rendered with "sla-status", "process-status", and "process-step" filters', () => {
		const filterNames = getAllByTestId('filterName');

		expect(filterNames[0].innerHTML).toBe('sla-status');
		expect(filterNames[1].innerHTML).toBe('process-status');
		expect(filterNames[2].innerHTML).toBe('process-step');
	});

	test('Select all instances by clicking on check all button', () => {
		const checkAllButton = getByTestId('checkAllButton');
		const instanceCheckbox = getAllByTestId('instanceCheckbox');

		expect(checkAllButton.checked).toEqual(false);
		expect(instanceCheckbox[0].checked).toEqual(false);
		expect(instanceCheckbox[1].checked).toEqual(false);

		fireEvent.click(checkAllButton);

		expect(checkAllButton.checked).toEqual(true);
		expect(instanceCheckbox[0].checked).toEqual(true);
		expect(instanceCheckbox[1].checked).toEqual(true);

		fireEvent.click(checkAllButton);

		expect(checkAllButton.checked).toEqual(false);
		expect(instanceCheckbox[0].checked).toEqual(false);
		expect(instanceCheckbox[1].checked).toEqual(false);
	});

	test('Select remaining instances by clicking on select remaining button', () => {
		const checkAllButton = getByTestId('checkAllButton');
		const instanceCheckbox = getAllByTestId('instanceCheckbox');

		expect(checkAllButton.checked).toEqual(false);
		expect(instanceCheckbox[0].checked).toEqual(false);
		expect(instanceCheckbox[1].checked).toEqual(false);

		fireEvent.click(instanceCheckbox[0]);

		expect(checkAllButton.checked).toEqual(false);
		expect(instanceCheckbox[0].checked).toEqual(true);
		expect(instanceCheckbox[1].checked).toEqual(false);

		const selectRemainingButton = getByTestId('selectRemainingButton');
		fireEvent.click(selectRemainingButton);

		expect(checkAllButton.checked).toEqual(true);
		expect(instanceCheckbox[0].checked).toEqual(true);
		expect(instanceCheckbox[1].checked).toEqual(true);
	});
});
