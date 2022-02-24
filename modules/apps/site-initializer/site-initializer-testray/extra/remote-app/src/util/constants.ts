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

export const DATA_COLORS = {
	'metrics.blocked': '#F8D72E',
	'metrics.failed': '#E73A45',
	'metrics.incomplete': '#E3E9EE',
	'metrics.passed': '#3CD587',
	'metrics.test-fix': '#59BBFC',
};

export const LABEL_GREATER_THAN_99 = '> 99';
export const LABEL_LESS_THAN_1 = '< 1';

export const PAGINATION_DELTA = [20, 50, 75, 100, 200];

export const PAGINATION = {
	delta: [20, 50, 75, 100, 200],
	ellipsisBuffer: 3,
};

export enum TEST_STATUS {
	Blocked = 4,
	'Did Not Run' = 6,
	Failed = 3,
	'In Progress' = 1,
	'Passed' = 2,
	'Test Fix' = 7,
	'Untested' = 0,
}

export const TEST_STATUS_LABEL: any = {
	0: 'Untested',
	1: 'In Progress',
	2: 'Passed',
	3: 'Failed',
	4: 'Blocked',
	6: 'Did Not Run',
	7: 'Test Fix',
};

export const USER_DROPDOWN = [
	{
		items: [
			{
				icon: 'user',
				label: 'Manage Account',
				path: '/manage/user',
			},
			{
				icon: 'logout',
				label: 'Sign Out',
				path: `${window.location.origin}/c/portal/logout`,
			},
		],
		title: '',
	},
];

const getStatusLabel = (status: number): string =>
	(TEST_STATUS_LABEL as any)[status];

export enum SUB_TASK_STATUS {
	'OPEN' = 0,
	'IN_ANALYSIS' = 1,
	'COMPLETE' = 2,
	'MERGED' = 3,
}

export const SUBTASK_STATUS: any = {
	0: {color: 'label-inverse-light', label: 'OPEN'},
	1: {color: 'label-inverse-warning', label: 'IN ANALYSIS'},
	2: {color: 'label-inverse-primary', label: 'COMPLETE'},
	3: {color: 'label-inverse-light', label: 'MERGED'},
};

export {getStatusLabel};
