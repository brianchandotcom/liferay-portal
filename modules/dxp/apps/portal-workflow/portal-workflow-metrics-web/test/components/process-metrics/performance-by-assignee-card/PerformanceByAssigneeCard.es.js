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

import PerformanceByAssigneeCard from '../../../../src/main/resources/META-INF/resources/js/components/process-metrics/performance-by-assignee-card/PerformanceByAssigneeCard.es';
import {MockRouter} from '../../../mock/MockRouter.es';

const clientMock = {
	get: jest.fn().mockResolvedValue({data: {}})
};

describe('The performance by assignee component should', () => {
	let getByTestId;

	const {processId, query} = {processId: 123456, query: 'queryMock'};

	afterEach(() => cleanup);

	beforeEach(() => {
		clientMock.get.mockResolvedValue({data: {}});

		const renderResult = render(
			<MockRouter client={clientMock}>
				<PerformanceByAssigneeCard
					processId={processId}
					query={query}
				/>
			</MockRouter>
		);

		getByTestId = renderResult.getByTestId;
	});

	test('Render with header and body', () => {
		const body = getByTestId('panelBody');
		const header = getByTestId('panelHeader');

		expect(body).not.toBeNull();
		expect(header).not.toBeNull();
	});
});
