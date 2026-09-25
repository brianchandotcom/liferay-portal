/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	AssignToModalContent,
	UpdateDueDateModalContent,
} from '@liferay/site-cms-site-initializer';
import React from 'react';

type WorkflowTaskModalProps = {
	closeModal: () => void;
	dueDate: string;
	loadData: () => Promise<void>;
	workflowTaskId: number;
};

const WORKFLOW_TASK_MODALS: Record<
	string,
	(baseProps: WorkflowTaskModalProps) => JSX.Element
> = {
	assignToMeWorkflowTask: (props) => (
		<AssignToModalContent {...props} assignable={false} />
	),
	assignToWorkflowTask: (props) => (
		<AssignToModalContent {...props} assignable={true} />
	),
	updateDueDateWorkflowTask: (props) => (
		<UpdateDueDateModalContent {...props} />
	),
};

export default WORKFLOW_TASK_MODALS;
