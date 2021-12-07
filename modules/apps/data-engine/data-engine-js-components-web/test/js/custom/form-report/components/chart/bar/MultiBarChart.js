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
	afterEach(cleanup);

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
