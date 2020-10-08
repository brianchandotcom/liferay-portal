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

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import {InstanceListContext} from '../../../src/main/resources/META-INF/resources/js/components/instance-list-page/InstanceListPageProvider.es';
import {Table} from '../../../src/main/resources/META-INF/resources/js/components/instance-list-page/InstanceListPageTable.es';
import {ModalContext} from '../../../src/main/resources/META-INF/resources/js/components/instance-list-page/modal/ModalProvider.es';
import {MockRouter} from '../../mock/MockRouter.es';

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

describe('The instance list table should', () => {
	afterEach(cleanup);

	test('Be rendered with two items', () => {
		const {getAllByRole} = render(
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

		const instanceRows = getAllByRole('row');

		expect(instanceRows.length).toBe(3);
	});
});
