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

import {cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';

import PieChart from '../../../../../../../src/main/resources/META-INF/resources/js/custom/form-report/components/chart/pie/PieChart';

const props = {
	data: [
		{count: 2, label: 'label1'},
		{count: 2, label: 'label2'},
	],
	height: 300,
	totalEntries: 4,
	width: 700,
};

describe('PieChart', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {asFragment} = render(<PieChart {...props} />);

		expect(asFragment()).toMatchSnapshot();
	});

	it('expands a sector with the percentage label when mouse is over', () => {
		const {container} = render(<PieChart {...props} />);

		const sector = container.querySelector('.recharts-pie-sector');

		fireEvent.mouseOver(sector);

		expect(
			container.querySelector('.recharts-pie-sector > g')
		).toBeTruthy();

		expect(
			container.querySelector('.recharts-layer > text').innerHTML
		).toBe('50%');
	});

	it('resets a sector when mouse is out', () => {
		const {container} = render(<PieChart {...props} />);

		const sector = container.querySelector('.recharts-pie-sector');

		fireEvent.mouseOver(sector);

		const expandedSector = container.querySelector(
			'.recharts-pie-sector g path'
		);

		fireEvent.mouseOut(expandedSector);

		expect(
			container.querySelector('.recharts-pie-sector > path')
		).toBeTruthy();
	});

	it('highlight a sector when mouse is over it', () => {
		const {container} = render(<PieChart {...props} />);

		const sector = container.querySelector('.recharts-pie-sector');

		fireEvent.mouseOver(sector);

		expect(
			container
				.querySelector('.recharts-layer > path')
				.getAttribute('fill-opacity')
		).toBe('0.5');
	});
});
