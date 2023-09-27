/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	const {ResizeObserver} = window;

	beforeAll(() => {
		delete window.ResizeObserver;
		window.ResizeObserver = jest.fn().mockImplementation(() => ({
			disconnect: jest.fn(),
			observe: jest.fn(),
			unobserve: jest.fn(),
		}));
	});

	afterAll(() => {
		cleanup();
		window.ResizeObserver = ResizeObserver;
		jest.restoreAllMocks();
	});

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
