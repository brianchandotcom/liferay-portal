/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {render} from '@testing-library/react';
import React from 'react';
import ResizeObserver from 'resize-observer-polyfill';

import EngagementChart from '../../../src/main/resources/META-INF/resources/js/main_view/analytics/components/EngagementChart';
import {mockEngagementChartData} from './__mocks__';

global.ResizeObserver = ResizeObserver;

jest.mock('recharts', () => {
	const OriginalModule = jest.requireActual('recharts');

	return {
		...OriginalModule,
		ResponsiveContainer: ({children}: {children: React.ReactNode}) => (
			<OriginalModule.ResponsiveContainer height={400} width={800}>
				{children}
			</OriginalModule.ResponsiveContainer>
		),
	};
});

describe('EngagementTimelineChart component', () => {
	let container: HTMLElement;

	beforeEach(() => {
		const view = render(
			<EngagementChart
				engagementChartItems={mockEngagementChartData}
				isLoading={false}
			/>
		);

		container = view.container;
	});

	afterAll(() => {
		delete (global as any).ResizeObserver;
	});

	it('render the chart matching snapshot', async () => {
		const svgChart = container.querySelector(
			'.recharts-surface'
		) as HTMLElement;

		expect(svgChart).toMatchSnapshot();
	});

	it('render the chart with data', async () => {
		const svgChart = container.querySelector(
			'.recharts-surface'
		) as HTMLElement;

		expect(svgChart).toBeInTheDocument();

		const line = svgChart.querySelector('.recharts-line-curve');

		expect(line).toBeInTheDocument();
		expect(line).toHaveAttribute('stroke', 'url(#lineGradient)');

		const xAxisTick = container.querySelector(
			'.recharts-xAxis .recharts-cartesian-axis-tick-value'
		);

		expect(xAxisTick).toHaveTextContent('Feb 20');
	});
});
