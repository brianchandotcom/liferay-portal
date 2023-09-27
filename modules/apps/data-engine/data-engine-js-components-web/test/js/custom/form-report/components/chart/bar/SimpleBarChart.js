/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import SimpleBarChart from '../../../../../../../src/main/resources/META-INF/resources/js/custom/form-report/components/chart/bar/SimpleBarChart';

const props = {
	data: [
		{count: 2, label: 'Option 1'},
		{count: 2, label: 'Option 2'},
		{count: 1, label: 'Option 3'},
	],
	height: 300,
	totalEntries: 5,
	width: 700,
};

describe('SimpleBarChart', () => {
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
		const {queryAllByText, queryByText} = render(
			<SimpleBarChart {...props} />
		);

		expect(queryAllByText('Option 1')).toBeTruthy();
		expect(queryByText('Option 2')).toBeTruthy();
		expect(queryByText('Option 3')).toBeTruthy();
	});
});
