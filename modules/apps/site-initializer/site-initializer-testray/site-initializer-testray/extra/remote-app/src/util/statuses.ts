/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export enum BuildStatuses {
	ACTIVATED = 'ACTIVATED',
	ARCHIVED = 'ARCHIVED',
	DEACTIVATED = 'DEACTIVATED',
}

export enum CaseResultStatuses {
	BLOCKED = 'BLOCKED',
	DID_NOT_RUN = 'DIDNOTRUN',
	FAILED = 'FAILED',
	IN_PROGRESS = 'INPROGRESS',
	INCOMPLETE = 'INCOMPLETE',
	PASSED = 'PASSED',
	TEST_FIX = 'TESTFIX',
	UNTESTED = 'UNTESTED',
}

export enum DispatchTriggerStatuses {
	COMPLETED = 'COMPLETED',
	FAILED = 'FAILED',
	INPROGRESS = 'INPROGRESS',
	SCHEDULED = 'SCHEDULED',
}

export enum TaskStatuses {
	ABANDONED = 'ABANDONED',
	COMPLETE = 'COMPLETE',
	IN_ANALYSIS = 'INANALYSIS',
	OPEN = 'OPEN',
	PROCESSING = 'PROCESSING',
}

export enum SubTaskStatuses {
	COMPLETE = 'COMPLETE',
	IN_ANALYSIS = 'INANALYSIS',
	MERGED = 'MERGED',
	OPEN = 'OPEN',
}
