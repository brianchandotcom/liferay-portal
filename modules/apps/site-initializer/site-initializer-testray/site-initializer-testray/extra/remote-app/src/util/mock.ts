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
