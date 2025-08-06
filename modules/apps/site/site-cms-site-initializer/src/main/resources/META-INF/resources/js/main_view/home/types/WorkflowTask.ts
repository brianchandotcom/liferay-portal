/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export interface WorkflowTask {
	assetTitle: string;
	assetType: string;
	assignedDate: string;
	auditUser: string;
	auditUserImageURL: string;
	dueDate: string;
	isComplete: boolean;
	name: string;
	workflowTaskId: string;
}
