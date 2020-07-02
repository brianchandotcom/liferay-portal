/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import TooltipContent from '../../../../src/main/resources/META-INF/resources/js/components/chart/TooltipContent.es';

describe('Tooltip', () => {
	afterEach(cleanup);

	it('renders the tooltip with label, number of entries and percentage for SimpleBarChart', () => {
		const {container} = render(
			<TooltipContent
				active={true}
				payload={[
					{
						name: 'count',
						payload: {count: 2, label: 'label1'},
						value: 2,
					},
				]}
				showHeader={false}
				totalEntries={2}
			/>
		);

		const tooltipLabel = container.querySelector('#tooltip-label')
			.innerHTML;

		expect(tooltipLabel).toBe('label1: 2 entries <b>(100%)</b>');
	});

	it('renders tooltip for MultiBar Chart', () => {
		const {asFragment} = render(
			<TooltipContent
				active={true}
				label="Row 1"
				payload={[
					{
						dataKey: 'Col 1',
						fill: '#4b9bff',
						name: 'Col 1',
						value: 1,
					},
					{
						dataKey: 'Col 2',
						fill: '#af78ff',
						name: 'Col 2',
						value: 2,
					},
					{
						dataKey: 'Col 3',
						fill: '#50d2a0',
						name: 'Col 3',
						value: 3,
					},
				]}
				roundBullet={false}
				totalEntries={6}
			/>
		);

		expect(asFragment()).toMatchSnapshot();
	});

	it('renders tooltip for Pie chart', () => {
		const {asFragment} = render(
			<TooltipContent
				active={true}
				payload={[
					{
						dataKey: 'count',
						fill: '#4b9bff',
						name: 'Option 1',
						payload: {label: 'Option 1'},
						value: 1,
					},
				]}
				roundBullet={false}
				showBullet={true}
				showHeader={false}
				totalEntries={1}
			/>
		);

		expect(asFragment()).toMatchSnapshot();
	});
});
