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

export enum RoleTypes {
	REGULAR = 1,
	SITE = 2,
	ORGANIZATION = 3,
	ASSET_LIBRARY = 5,
}

export enum STORAGE_KEYS {
	EXPORT_CASE_IDS = '@testray/export-case-ids',
}

export const DATA_COLORS = {
	'metrics.blocked': '#F8D72E',
	'metrics.failed': '#E73A45',
	'metrics.incomplete': '#E3E9EE',
	'metrics.passed': '#3CD587',
	'metrics.test-fix': '#59BBFC',
};

export enum Statuses {
	PASSED = 'PASSED',
	FAILED = 'FAILED',
	BLOCKED = 'BLOCKED',
	TEST_FIX = 'TEST FIX',
	INCOMPLETE = 'INCOMPLETE',
	SELF = 'SELF COMPLETED',
	OTHER = 'OTHERS COMPLETED',
}

export enum StatusesProgressScore {
	SELF = 'SELF COMPLETED',
	OTHER = 'OTHERS COMPLETED',
	INCOMPLETE = 'INCOMPLETE',
}

export const chartColors = {
	[Statuses.BLOCKED]: DATA_COLORS['metrics.blocked'],
	[Statuses.FAILED]: DATA_COLORS['metrics.failed'],
	[Statuses.INCOMPLETE]: DATA_COLORS['metrics.incomplete'],
	[Statuses.PASSED]: DATA_COLORS['metrics.passed'],
	[Statuses.TEST_FIX]: DATA_COLORS['metrics.test-fix'],
};

export const chartClassNames = {
	[Statuses.BLOCKED]: 'blocked',
	[Statuses.FAILED]: 'failed',
	[Statuses.INCOMPLETE]: 'test-incomplete',
	[Statuses.PASSED]: 'passed',
	[Statuses.SELF]: 'self-completed',
	[Statuses.TEST_FIX]: 'test-fix',
	[Statuses.OTHER]: 'others-completed',
};

export const LABEL_GREATER_THAN_99 = '> 99';
export const LABEL_LESS_THAN_1 = '< 1';

export const PAGINATION_DELTA = [20, 50, 75, 100, 150];

export const PAGINATION = {
	delta: PAGINATION_DELTA,
	ellipsisBuffer: 3,
};

export const BUILD_STATUS = {
	0: {color: 'label-chart-in-analysis', label: 'IN ANALYSIS'},
	2: {color: 'label-secondary', label: 'OPEN'},
};
