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
import InstanceListCard from '../../../../src/main/resources/META-INF/resources/js/components/process-metrics/instance-list/InstanceListCard.es';
import {MockRouter} from '../../../mock/MockRouter.es';
import React from 'react';

const items = [
	{
		key: 'review',
		name: 'Review'
	},
	{
		key: 'update',
		name: 'Update'
	}
];

describe('The instance list card should', () => {
	const clientMock = {
		get: jest.fn().mockResolvedValue({data: {items}})
	};
	let renderResult;

	afterEach(cleanup);

	beforeEach(() => {
		renderResult = render(
			<MockRouter client={clientMock}>
				<InstanceListCard />
			</MockRouter>
		);
	});

	test('Be rendered with "sla-status", "process-status", and "process-step" filters', () => {
		const {getAllByTestId} = renderResult;

		const filterNames = getAllByTestId('filterName');

		expect(filterNames[0].innerHTML).toBe('sla-status');
		expect(filterNames[1].innerHTML).toBe('process-status');
		expect(filterNames[2].innerHTML).toBe('process-step');
	});
});
