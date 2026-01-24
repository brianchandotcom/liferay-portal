/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {act, cleanup, render, screen} from '@testing-library/react';
import {fetch} from 'frontend-js-web';
import React from 'react';

import TasksOverview from '../../../../../js/main_view/projects/components/TasksOverview';

describe('TasksOverview', () => {
	afterEach(cleanup);

	it('renders the appropriate counts', async () => {
		(fetch as jest.Mock).mockResolvedValue({
			json: () =>
				Promise.resolve({
					blockedCount: 1,
					completionRate: 50,
					inProgressCount: 2,
					overdueCount: 3,
					totalCount: 4,
				}),
			ok: true,
		});

		await act(async () => {
			render(
				<TasksOverview cmpProjectId="123" redirect="/redirect-url" />
			);
		});

		expect(screen.getByText('1')).toBeInTheDocument();
		expect(screen.getByText('2')).toBeInTheDocument();
		expect(screen.getByText('3')).toBeInTheDocument();
		expect(screen.getByText('4')).toBeInTheDocument();
		expect(screen.getByText('50%')).toBeInTheDocument();
	});

	it('renders empty state when totalCount is 0', async () => {
		(fetch as jest.Mock).mockResolvedValue({
			json: () => ({
				blockedCount: 0,
				completionRate: 0,
				inProgressCount: 0,
				overdueCount: 0,
				totalCount: 0,
			}),
		});

		await act(async () => {
			render(
				<TasksOverview cmpProjectId="123" redirect="/redirect-url" />
			);
		});

		expect(screen.getByText('no-tasks')).toBeInTheDocument();

		expect(screen.getByText('no-tasks')).toBeInTheDocument();
		expect(
			screen.getByText('add-a-tasks-to-start-tracking-work')
		).toBeInTheDocument();
		expect(screen.getByText('new-task')).toBeInTheDocument();

		expect(screen.queryByText('tasks-overview')).not.toBeInTheDocument();
		expect(screen.queryByText('total-tasks')).not.toBeInTheDocument();
	});
});
