/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {WorkflowTasksPage} from '../tests/portal-workflow-task-web/pages/WorkflowTasksPage';
import {WorkflowPage} from '../tests/portal-workflow-web/pages/WorkflowPage';

const workflowPagesTest = test.extend<{
	workflowPage: WorkflowPage;
	workflowTasksPage: WorkflowTasksPage;
}>({
	workflowPage: async ({page}, use) => {
		await use(new WorkflowPage(page));
	},
	workflowTasksPage: async ({page}, use) => {
		await use(new WorkflowTasksPage(page));
	},
});

export {workflowPagesTest};
