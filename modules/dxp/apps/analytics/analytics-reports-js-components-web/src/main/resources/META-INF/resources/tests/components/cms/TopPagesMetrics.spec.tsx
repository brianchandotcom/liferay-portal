/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {render, screen, within} from '@testing-library/react';
import React from 'react';

import {TopPagesMetricsTable} from '../../../js/components/cms/TopPagesMetrics';
import {MetricType} from '../../../js/types/global';

describe('TopPagesMetrics', () => {
	it('render table with data on screen', () => {
		render(
			<TopPagesMetricsTable
				data={{
					pages: [
						{
							canonicalUrl: 'http://example.com/page1',
							defaultMetric: {
								metricType: MetricType.Views,
								value: 500,
							},
							pageTitle: 'Page 1',
							siteName: 'Site A',
						},
						{
							canonicalUrl: 'http://example.com/page2',
							defaultMetric: {
								metricType: MetricType.Views,
								value: 300,
							},
							pageTitle: 'Page 2',
							siteName: 'Site B',
						},
					],
					totalCount: 1000,
				}}
			/>
		);

		expect(
			screen.getByText('top-pages-asset-appears-on')
		).toBeInTheDocument();
		expect(
			screen.getByText(
				'this-metric-calculates-the-top-three-pages-that-generated-the-highest-number-of-views-for-the-asset'
			)
		).toBeInTheDocument();

		const headers = screen.getAllByRole('columnheader');
		expect(headers[0]).toHaveTextContent(/site/i);
		expect(headers[1]).toHaveTextContent(/views/i);
		expect(headers[2]).toHaveTextContent('x-of-x');

		const rows = screen.getAllByRole('row');

		const firstRow = within(rows[1]);
		expect(
			firstRow.getByRole('link', {name: 'Site A | Page 1'})
		).toHaveAttribute('href', 'http://example.com/page1');
		expect(firstRow.getByText('500')).toBeInTheDocument();
		expect(firstRow.getByText('50%')).toBeInTheDocument();

		const secondRow = within(rows[2]);
		expect(
			secondRow.getByRole('link', {name: 'Site B | Page 2'})
		).toHaveAttribute('href', 'http://example.com/page2');
		expect(secondRow.getByText('300')).toBeInTheDocument();
		expect(secondRow.getByText('30%')).toBeInTheDocument();
	});

	it('renders empty table when pages=[]', () => {
		render(
			<TopPagesMetricsTable
				data={{
					pages: [],
					totalCount: 0,
				}}
			/>
		);

		expect(
			screen.getByText('top-pages-asset-appears-on')
		).toBeInTheDocument();

		const headers = screen.getAllByRole('columnheader');
		expect(headers[0]).toHaveTextContent(/site/i);
		expect(headers[1]).toHaveTextContent(/views/i);
		expect(headers[2]).toHaveTextContent('x-of-x');

		expect(screen.queryByRole('link')).not.toBeInTheDocument();
	});
});
