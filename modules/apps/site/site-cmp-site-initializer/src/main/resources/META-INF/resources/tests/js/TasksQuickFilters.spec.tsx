/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {act, cleanup, render, screen} from '@testing-library/react';
import {fetch} from 'frontend-js-web';
import React from 'react';

import TasksQuickFilters from '../../js/components/task/TasksQuickFilters';

describe('TasksQuickFilters', () => {
	afterEach(() => {
		cleanup();
		(fetch as jest.Mock).mockClear();
	});

	it('renders the appropriate counts when a projectId is provided', async () => {
		(fetch as jest.Mock).mockResolvedValue({
			json: () =>
				Promise.resolve({
					blockedCount: 1,
					inProgressCount: 2,
					overdueCount: 3,
					totalCount: 4,
				}),
			ok: true,
		});

		await act(async () => {
			render(<TasksQuickFilters projectId="123" />);
		});

		expect(fetch).toHaveBeenCalledWith(
			'/o/headless-cmp/v1.0/projects/123/task-statistics/',
			{
				method: 'GET',
			}
		);

		expect(screen.getByText('blocked').previousSibling).toHaveTextContent(
			'1'
		);
		expect(
			screen.getByText('in-progress').previousSibling
		).toHaveTextContent('2');
		expect(screen.getByText('overdue').previousSibling).toHaveTextContent(
			'3'
		);
		expect(
			screen.getByText('total-tasks').previousSibling
		).toHaveTextContent('4');
	});

	it('renders the appropriate counts from multiple API calls', async () => {
		(fetch as jest.Mock).mockResolvedValueOnce({
			json: () =>
				Promise.resolve({
					blockedCount: 1,
					inProgressCount: 2,
					overdueCount: 3,
					totalCount: 4,
				}),
			ok: true,
		});

		await act(async () => {
			render(<TasksQuickFilters />);
		});

		expect(fetch).toHaveBeenCalledWith(
			'/o/headless-cmp/v1.0/task-statistics/',
			{method: 'GET'}
		);

		expect(screen.getByText('blocked').previousSibling).toHaveTextContent(
			'1'
		);
		expect(
			screen.getByText('in-progress').previousSibling
		).toHaveTextContent('2');
		expect(screen.getByText('overdue').previousSibling).toHaveTextContent(
			'3'
		);
		expect(
			screen.getByText('total-tasks').previousSibling
		).toHaveTextContent('4');
	});
});
