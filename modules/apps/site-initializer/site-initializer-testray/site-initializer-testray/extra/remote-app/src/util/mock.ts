/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export type Tasks = {
	blocked?: number;
	failed?: number;
	incomplete?: number;
	passed?: number;
	test_fix?: number;
};

export type Subtask = {
	assignee: any;
	error: string;
	name: string;
	score: number;
	status: string;
	tests: number;
};

export type Progress = {
	incomplete: number;
	other: number;
	self: number;
};

export type Tests = {
	case: string;
	component: string;
	issues: string;
	priority: number;
	run: number;
	status: string;
	team: string;
};

const getRandom = (max = 50) => Math.ceil(Math.random() * max);

export function getRandomMaximumValue(count: number, max: number) {
	return [...new Array(count)].map(() => getRandom(max));
}

export {getRandom};
