/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import MultiBarChart from '../../../../../../../src/main/resources/META-INF/resources/js/custom/form-report/components/chart/bar/MultiBarChart';

const props = {
	data: {row1: {col1: 1}, row2: {col2: 2}, row3: {col3: 3}},
	field: {
		columns: {
			col1: {index: 1, value: 'Column 1'},
			col2: {index: 2, value: 'Column 2'},
			col3: {index: 3, value: 'Column 3'},
		},
		icon: 'table2',
		label: 'Grid',
		name: 'Grid',
		rows: {
			row1: {index: 1, value: 'Row 1'},
			row2: {index: 2, value: 'Row 2'},
			row3: {index: 3, value: 'Row 3'},
		},
		title: 'Grid',
		type: 'grid',
	},
	height: 300,
	structure: {
		columns: ['col1', 'col2', 'col3'],
		rows: ['row1', 'row2', 'row3'],
	},
	totalEntries: 2,
	width: 700,
};

describe('MultiBarChart', () => {
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
		const {queryByText} = render(<MultiBarChart {...props} />);

		expect(queryByText('Row 1')).toBeTruthy();
		expect(queryByText('Row 2')).toBeTruthy();
		expect(queryByText('Row 3')).toBeTruthy();
		expect(queryByText('Column 1')).toBeTruthy();
		expect(queryByText('Column 2')).toBeTruthy();
		expect(queryByText('Column 3')).toBeTruthy();
	});
});
