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

import {fireEvent, render} from '@testing-library/react';
import React from 'react';

import App from '../../src/main/resources/META-INF/resources/js/components/App.es';

import '@testing-library/jest-dom/extend-expect';

const processItems = [
	{
		id: 1,
		instancesCount: 5,
		title: 'Single Approver',
	},
];

const pending = {
	id: 1,
	instanceCount: 0,
	onTimeInstanceCount: 0,
	overdueInstanceCount: 0,
	title: 'Single Approver',
	untrackedInstanceCount: 0,
};

const jestEmpty = jest
	.fn()
	.mockResolvedValue({data: {items: [], totalCount: 0}});

const client = {
	get: jest
		.fn()
		.mockResolvedValueOnce({
			data: {
				items: processItems,
				totalCount: processItems.length,
			},
		})
		.mockResolvedValueOnce({data: pending})
		.mockResolvedValue({data: {items: [], totalCount: 0}}),
	post: jestEmpty,
	request: jestEmpty,
};

const mockProps = {
	client,
	companyId: 12345,
	defaultDelta: 20,
	deltaValues: [5, 10, 20, 30, 50, 75],
	getClient: jest.fn(() => client),
	isAmPm: false,
	maxPages: 15,
	namespace: 'WorkflowMetricsPortlet',
};

describe('The App component should', () => {
	let container, getAllByTestId;

	beforeAll(() => {
		const renderResult = render(<App {...mockProps} />);

		container = renderResult.container;
		getAllByTestId = renderResult.getAllByTestId;
	});

	test('Render the process list page', () => {
		const processName = getAllByTestId('processName');
		const processNameLink = processName[0].children[0];

		expect(processNameLink).toHaveTextContent('Single Approver');

		fireEvent.click(processNameLink);
	});

	test('Render the process metrics page on dashboard tab', () => {
		const tabs = container.querySelectorAll('a.nav-link');

		expect(tabs[0]).toHaveTextContent('dashboard');
		expect(tabs[0].className.includes('active')).toBe(true);
		expect(tabs[1]).toHaveTextContent('performance');

		expect(window.location.hash).toContain(
			'#/metrics/1/dashboard/20/1/overdueInstanceCount%3Aasc'
		);

		fireEvent.click(tabs[1]);
	});

	test('Render the process metrics page on performance tab', () => {
		const tabs = container.querySelectorAll('a.nav-link');

		expect(tabs[0]).toHaveTextContent('dashboard');
		expect(tabs[1]).toHaveTextContent('performance');
		expect(tabs[1].className.includes('active')).toBe(true);

		expect(window.location.hash).toContain('#/metrics/1/performance');
	});
});
