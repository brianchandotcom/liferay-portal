/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {ActionReassignmentPage} from '../pages/portal-workflow-kaleo-designer-web/ActionReassignmentPage';
import {DiagramViewPage} from '../pages/portal-workflow-kaleo-designer-web/DiagramViewPage';
import {NodePropertiesSidebarPage} from '../pages/portal-workflow-kaleo-designer-web/NodePropertiesSidebarPage';
import {NotificationSectionPage} from '../pages/portal-workflow-kaleo-designer-web/NotificationSectionPage';
import {ProcessBuilderPage} from '../pages/portal-workflow-kaleo-designer-web/ProcessBuilderPage';
import {SourceViewPage} from '../pages/portal-workflow-kaleo-designer-web/SourceViewPage';
import {TimerPage} from '../pages/portal-workflow-kaleo-designer-web/TimerPage';
import {WorkflowTasksPage} from '../tests/portal-workflow-task-web/pages/WorkflowTasksPage';
import {WorkflowPage} from '../tests/portal-workflow-web/pages/WorkflowPage';

const workflowPagesTest = test.extend<{
	actionReassignmentPage: ActionReassignmentPage;
	diagramViewPage: DiagramViewPage;
	nodePropertiesSidebarPage: NodePropertiesSidebarPage;
	notificationSectionPage: NotificationSectionPage;
	processBuilderPage: ProcessBuilderPage;
	sourceViewPage: SourceViewPage;
	timerPage: TimerPage;
	workflowPage: WorkflowPage;
	workflowTasksPage: WorkflowTasksPage;
}>({
	actionReassignmentPage: async ({page}, use) => {
		await use(new ActionReassignmentPage(page));
	},
	diagramViewPage: async ({page}, use) => {
		await use(new DiagramViewPage(page));
	},
	nodePropertiesSidebarPage: async ({page}, use) => {
		await use(new NodePropertiesSidebarPage(page));
	},
	notificationSectionPage: async ({page}, use) => {
		await use(new NotificationSectionPage(page, 0));
	},
	processBuilderPage: async ({page}, use) => {
		await use(new ProcessBuilderPage(page));
	},
	timerPage: async ({page}, use) => {
		await use(new TimerPage(page));
	},
	workflowPage: async ({page}, use) => {
		await use(new WorkflowPage(page));
	},
	workflowTasksPage: async ({page}, use) => {
		await use(new WorkflowTasksPage(page));
	},
});

export {workflowPagesTest};
