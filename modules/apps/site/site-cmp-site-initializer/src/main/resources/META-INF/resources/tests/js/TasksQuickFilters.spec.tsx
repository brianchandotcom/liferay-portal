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

	it('renders the appropriate counts from multiple API calls', async () => {
		(fetch as jest.Mock)
			.mockResolvedValueOnce({
				json: () => Promise.resolve({totalCount: 1}),
				ok: true,
			})
			.mockResolvedValueOnce({
				json: () => Promise.resolve({totalCount: 2}),
				ok: true,
			})
			.mockResolvedValueOnce({
				json: () => Promise.resolve({totalCount: 3}),
				ok: true,
			})
			.mockResolvedValueOnce({
				json: () => Promise.resolve({totalCount: 4}),
				ok: true,
			});

		await act(async () => {
			render(
				<TasksQuickFilters
					blockedCountURL="/blocked"
					inProgressCountURL="/in-progress"
					overdueCountURL="/overdue"
					totalCountURL="/total"
				/>
			);
		});

		expect(fetch).toHaveBeenCalledWith('/blocked');
		expect(fetch).toHaveBeenCalledWith('/in-progress');
		expect(fetch).toHaveBeenCalledWith('/overdue');
		expect(fetch).toHaveBeenCalledWith('/total');

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
