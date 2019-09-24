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
import {InstanceListContext} from '../../../../src/main/resources/META-INF/resources/js/components/process-metrics/instance-list/store/InstanceListStore.es';
import InstanceListTable from '../../../../src/main/resources/META-INF/resources/js/components/process-metrics/instance-list/InstanceListTable.es';

const instances = [
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
		id: 1,
		taskNames: ['Update']
	}
];

describe('The instance list table should', () => {
	afterEach(cleanup);

	test('Be rendered with two items', () => {
		const {getAllByTestId} = render(
			<InstanceListContext.Provider value={{setInstanceId: jest.fn()}}>
				<InstanceListTable items={instances} />
			</InstanceListContext.Provider>
		);

		const instanceRows = getAllByTestId('instanceRow');

		expect(instanceRows.length).toBe(2);
	});
});
