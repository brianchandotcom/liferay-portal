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

import ProcessMetrics from '../../../src/main/resources/META-INF/resources/js/components/process-metrics/ProcessMetrics.es';
import {MockRouter} from '../../mock/MockRouter.es';
import fetchFailure from '../../mock/fetchFailure.es';

import '@testing-library/jest-dom/extend-expect';

const client = {
	get: jest
		.fn()
		.mockResolvedValueOnce({data: {totalCount: 1}})
		.mockResolvedValueOnce({data: {totalCount: 0}}),
};

describe('The ProcessMetrics component should', () => {
	let getByTestId;

	beforeAll(() => {
		const component = render(
			<MockRouter
				client={client}
				initialPath="/metrics/35315/dashboard"
				path="/metrics/:processId"
			>
				<ProcessMetrics />
			</MockRouter>
		);
		getByTestId = component.getByTestId;
	});

	test('Show SLA setup alert and new SLA creation alert when has neither SLA blocked nor SLA created for instances gave', () => {
		const processMetricsDashBoard = getByTestId('processMetricsDashBoard');

		expect(
			processMetricsDashBoard.children[1].children[0]
		).toHaveTextContent(
			'fix-the-sla-configuration-to-resume-accurate-reporting'
		);

		expect(
			processMetricsDashBoard.children[1].children[0]
		).toHaveTextContent('set-up-slas');

		expect(
			processMetricsDashBoard.children[1].children[1]
		).toHaveTextContent('no-slas-are-defined-for-this-process');

		expect(
			processMetricsDashBoard.children[1].children[1]
		).toHaveTextContent('add-a-new-sla');
	});
});

describe('The ProcessMetrics component should', () => {
	let getByTestId;

	beforeAll(() => {
		cleanup();
		const component = render(
			<MockRouter
				client={fetchFailure()}
				initialPath="/metrics/35315"
				path="/metrics/:processId"
			>
				<ProcessMetrics />
			</MockRouter>
		);
		getByTestId = component.getByTestId;
	});

	test('Active the tab by clicking it', () => {
		const tabElements = getByTestId('tabElements');

		fireEvent.click(tabElements.children[1].children[0]);

		expect(tabElements.children[1].children[0]).toHaveClass('active');
	});
});
