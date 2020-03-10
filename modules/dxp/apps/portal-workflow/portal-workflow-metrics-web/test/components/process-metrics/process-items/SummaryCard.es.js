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

import '@testing-library/jest-dom/extend-expect';

import {slaStatusConstants} from '../../../../src/main/resources/META-INF/resources/js/components/filter/SLAStatusFilter.es';
import SummaryCard from '../../../../src/main/resources/META-INF/resources/js/components/process-metrics/process-items/SummaryCard.es';
import {MockRouter} from '../../../mock/MockRouter.es';

describe('The SummaryCard component should', () => {
	let getByTestId, renderResult;

	const props = {
		getTitle: () => 'Overdue',
		iconColor: 'danger',
		iconName: 'exclamation-circle',
		processId: 12345,
		slaStatusFilter: slaStatusConstants.overdue,
		total: false,
		totalValue: 858000,
		value: 156403,
	};

	beforeAll(() => {
		cleanup();

		renderResult = render(
			<MockRouter>
				<SummaryCard {...props} />
			</MockRouter>
		);

		getByTestId = renderResult.getByTestId;
	});

	test('Render correct icon and title', () => {
		const instanceIcon = getByTestId('instanceIcon');
		const instanceTitle = getByTestId('instanceTitle');

		expect([...instanceIcon.classList][1]).toContain('exclamation-circle');
		expect(instanceTitle).toHaveTextContent('Overdue');
	});

	test('Render formatted percentage', () => {
		const footer = getByTestId('footer');

		expect(footer).toHaveTextContent('18.23%');
	});

	test('Render formatted value for values with more than 3 digits', () => {
		const formattedValue = getByTestId('formattedValue');

		expect(formattedValue).toHaveTextContent('156.4K');
	});

	test('Show see items only when item is hovered', () => {
		const childLink = getByTestId('childLink');
		const footer = getByTestId('footer');

		fireEvent.mouseOver(childLink);

		expect(footer).toHaveTextContent('see-items');

		fireEvent.mouseOut(childLink);

		expect(footer).toHaveTextContent('18.23%');
	});
});

describe('The SummaryCard component should', () => {
	let getByTestId, renderResult;

	const props = {
		completed: true,
		getTitle: () => 'Total',
		processId: 12345,
		timeRange: {
			dateEnd: '2019-12-09T00:00:00Z',
			dateStart: '2019-12-03T00:00:00Z',
			key: '7',
		},
		total: true,
		totalValue: 3500,
		value: 310,
	};

	beforeAll(() => {
		cleanup();

		renderResult = render(
			<MockRouter>
				<SummaryCard {...props} />
			</MockRouter>
		);

		getByTestId = renderResult.getByTestId;
	});

	test('Not render formatted percentage for total item', () => {
		const footer = getByTestId('footer');

		expect(footer).toHaveTextContent('');
	});

	test('Not render formatted value for values with 3 or less digits', () => {
		const formattedValue = getByTestId('formattedValue');

		expect(formattedValue).toHaveTextContent('310');
	});

	test('Render with correct link', () => {
		const childLink = getByTestId('childLink');

		const href = childLink.getAttribute('href');

		expect(href).toContain('/instance/12345/20/1');
		expect(href).toContain(
			'filters.statuses%5B0%5D=Completed&filters.dateEnd=2019-12-09T00%3A00%3A00Z&filters.dateStart=2019-12-03T00%3A00%3A00Z&filters.timeRange%5B0%5D=7'
		);
	});
});

describe('The SummaryCard component should', () => {
	let getByTestId, renderResult;

	const props = {
		getTitle: () => 'On Time',
		iconColor: 'success',
		iconName: 'check-circle',
		processId: 12345,
		slaStatusFilter: slaStatusConstants.ontime,
		total: false,
		totalValue: 55,
		value: undefined,
	};

	beforeAll(() => {
		cleanup();

		renderResult = render(
			<MockRouter>
				<SummaryCard {...props} />
			</MockRouter>
		);

		getByTestId = renderResult.getByTestId;
	});

	test('Render component with disabled state', () => {
		const childLink = getByTestId('childLink');

		expect([childLink.classList][0]).toContain('disabled');
	});
});
