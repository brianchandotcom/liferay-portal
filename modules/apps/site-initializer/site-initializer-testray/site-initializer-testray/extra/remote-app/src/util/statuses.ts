/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

export enum BuildStatuses {
	ACTIVE = 'ACTIVE',
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

export enum TaskStatuses {
	ABANDONED = 'ABANDONED',
	COMPLETE = 'COMPLETE',
	IN_ANALYSIS = 'INANALYSIS',
	OPEN = 'OPEN',
}

export enum SubTaskStatuses {
	COMPLETE = 'COMPLETE',
	IN_ANALYSIS = 'INANALYSIS',
	MERGED = 'MERGED',
	OPEN = 'OPEN',
}
