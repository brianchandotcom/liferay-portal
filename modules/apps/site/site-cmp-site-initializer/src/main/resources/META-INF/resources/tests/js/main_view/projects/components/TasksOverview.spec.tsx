/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {render, screen} from '@testing-library/react';
import React from 'react';

import TasksOverview from '../../../../../js/main_view/projects/components/TasksOverview';

describe('TasksOverview', () => {
	it('renders empty state when totalCount is 0', async () => {
		render(
			<TasksOverview
				blockedCount={0}
				doneCount={0}
				inProgressCount={0}
				overdueCount={0}
				redirect="/redirect-url"
				totalCount={0}
			/>
		);

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
