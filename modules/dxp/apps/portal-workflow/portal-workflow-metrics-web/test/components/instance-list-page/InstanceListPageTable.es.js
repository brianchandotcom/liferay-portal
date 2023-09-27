/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import {InstanceListContext} from '../../../src/main/resources/META-INF/resources/js/components/instance-list-page/InstanceListPageProvider.es';
import {Table} from '../../../src/main/resources/META-INF/resources/js/components/instance-list-page/InstanceListPageTable.es';
import {ModalContext} from '../../../src/main/resources/META-INF/resources/js/components/instance-list-page/modal/ModalProvider.es';
import {MockRouter} from '../../mock/MockRouter.es';
import FetchMock, {fetchMockResponse} from '../../mock/fetch.es';

const instances = [
	{
		assetTitle: 'New Post 1',
		assetType: 'Blog',
		dateCreated: new Date('2019-01-01'),
		id: 1,
		taskNames: [],
	},
	{
		assetTitle: 'New Post 2',
		assetType: 'Blog',
		creator: {
			name: 'User 1',
		},
		dateCreated: new Date('2019-01-03'),
		id: 1,
		taskNames: ['Update'],
	},
];

const fetchMock = new FetchMock({
	GET: {
		default: fetchMockResponse({}),
	},
});

describe('The instance list table should', () => {
	let container;
	let getAllByRole;

	afterEach(() => {
		fetchMock.reset();

		cleanup();
	});

	beforeEach(() => {
		fetchMock.mock();

		const renderResult = render(
			<MockRouter>
				<InstanceListContext.Provider
					value={{setInstanceId: jest.fn()}}
				>
					<ModalContext.Provider
						value={{closeModal: jest.fn(), openModal: jest.fn()}}
					>
						<Table items={instances} />
					</ModalContext.Provider>
				</InstanceListContext.Provider>
			</MockRouter>
		);

		container = renderResult.container;
		getAllByRole = renderResult.getAllByRole;
	});

	test('Be rendered with two items', () => {
		const instanceRows = getAllByRole('row');

		expect(instanceRows.length).toBe(3);
	});

	test('Should order by Assignee', () => {
		const orderLinks = container.querySelectorAll('.inline-item');

		expect(orderLinks[2].href).toContain(
			'/1/20/assigneeName%3Adesc?backPath=%2F'
		);
	});

	test('Should order by Item Subject', () => {
		const orderLinks = container.querySelectorAll('.inline-item');

		expect(orderLinks[1].href).toContain(
			'/1/20/assetType%3Adesc?backPath=%2F'
		);
	});

	test('Should order by User Creator', () => {
		const orderLinks = container.querySelectorAll('.inline-item');

		expect(orderLinks[3].href).toContain(
			'/1/20/userName%3Adesc?backPath=%2F'
		);
	});
});
