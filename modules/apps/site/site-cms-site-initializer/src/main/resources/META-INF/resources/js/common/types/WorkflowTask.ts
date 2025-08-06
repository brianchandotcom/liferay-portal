/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {WorkflowLog} from './WorkflowLog';

export interface WorkflowTask {
	assigneePerson: {
		id: Number;
		name: string;
	};
	completed: boolean;
	dateDue: string;
	id: string;
	name: string;
	objectReviewed: {
		assetTitle: string;
		assetType: string;
		id: Number;
	};
	workflowLogs: WorkflowLog[];
}
