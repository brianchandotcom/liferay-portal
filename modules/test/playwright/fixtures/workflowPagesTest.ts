/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {ScriptManagementPage} from '../pages/portal-security-script-management-web/ScriptManagementPage';
import {ActionPage} from '../pages/portal-workflow-kaleo-designer-web/ActionPage';
import {ActionReassignmentPage} from '../pages/portal-workflow-kaleo-designer-web/ActionReassignmentPage';
import {ConditionNode} from '../pages/portal-workflow-kaleo-designer-web/ConditionNodePage';
import {DiagramViewPage} from '../pages/portal-workflow-kaleo-designer-web/DiagramViewPage';
import {NodePropertiesSidebarPage} from '../pages/portal-workflow-kaleo-designer-web/NodePropertiesSidebarPage';
import {NotificationPage} from '../pages/portal-workflow-kaleo-designer-web/NotificationPage';
import {NotificationSectionPage} from '../pages/portal-workflow-kaleo-designer-web/NotificationSectionPage';
import {ProcessBuilderPage} from '../pages/portal-workflow-kaleo-designer-web/ProcessBuilderPage';
import {TimerPage} from '../pages/portal-workflow-kaleo-designer-web/TimerPage';
import {WorkflowTasksPage} from '../tests/portal-workflow-task-web/pages/WorkflowTasksPage';
import {WorkflowPage} from '../tests/portal-workflow-web/pages/WorkflowPage';

const workflowPagesTest = test.extend<{
	actionPage: ActionPage;
	actionReassignmentPage: ActionReassignmentPage;
	conditionNode: ConditionNode;
	diagramViewPage: DiagramViewPage;
	nodePropertiesSidebarPage: NodePropertiesSidebarPage;
	notificationPage: NotificationPage;
	notificationSectionPage: NotificationSectionPage;
	processBuilderPage: ProcessBuilderPage;
	scriptManagementPage: ScriptManagementPage;
	timerPage: TimerPage;
	workflowPage: WorkflowPage;
	workflowTasksPage: WorkflowTasksPage;
}>({
	actionPage: async ({page}, use) => {
		await use(new ActionPage(page));
	},
	actionReassignmentPage: async ({page}, use) => {
		await use(new ActionReassignmentPage(page));
	},
	conditionNode: async ({page}, use) => {
		await use(new ConditionNode(page));
	},
	diagramViewPage: async ({page}, use) => {
		await use(new DiagramViewPage(page));
	},
	nodePropertiesSidebarPage: async ({page}, use) => {
		await use(new NodePropertiesSidebarPage(page));
	},
	notificationPage: async ({page}, use) => {
		await use(new NotificationPage(page));
	},
	notificationSectionPage: async ({page}, use) => {
		await use(new NotificationSectionPage(page, 0));
	},
	processBuilderPage: async ({page}, use) => {
		await use(new ProcessBuilderPage(page));
	},
	scriptManagementPage: async ({page}, use) => {
		await use(new ScriptManagementPage(page));
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
