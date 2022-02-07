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

type Routine = {
	assigned?: any;
	buildName?: any;
	progress?: number;
	projectName?: any;
	routineName?: any;
	score?: {passed: number; total: number};
	startDate?: any;
	status?: any;
	task?: any;
};

const assigned = [
	{
		name: 'John Doe',
		url: 'https://picsum.photos/200',
	},
	{
		name: 'John Doe',
		url: 'https://picsum.photos/200',
	},
	{
		name: 'John Doe',
		url: 'https://picsum.photos/200',
	},
	{
		name: 'John Doe',
		url: 'https://picsum.photos/200',
	},
];

const generateItems = <T>(item: T, total = 20): T[] => {
	return [...new Array(total)].map(() => item);
};

export const routines = generateItems<Routine>(
	{
		assigned,
		buildName:
			'CE Package Tester - 7.4.3.10-ga10 - 3099 - 2022-01-31[08:44:04]',
		progress: 20,
		projectName: 'Liferay Portal 7.4',
		routineName: 'CE Package Tester',
		score: {passed: 985, total: 4589},
		startDate: 'a day ago',
		status: 'IN ANALYZES',
		task: 'CE Package Tester - 7.4.3.10-ga10 - 3099 - 2022-01-31[08:44:04]',
	},
	5
);

// Tomcat 9.0 Chrome 86.0 IBM DB2 11.1 Oracle JDK 8 64 Bit CentOS 7 64 Bit

const name = 'Tomcat';

export const runs = [
	{
		blocked: 0,
		failed: 1074,
		incomplete: 1,
		name: `1 ${name}`,
		passed: 1318,
		test_fix: 0,
	},
	{
		blocked: 0,
		failed: 818,
		incomplete: 2,
		name: `2 ${name}`,
		passed: 9779,
		test_fix: 0,
	},
	{
		blocked: 0,
		failed: 2393,
		incomplete: 0,
		name: `3 ${name}`,
		passed: 0,
		test_fix: 0,
	},
	{
		blocked: 0,
		failed: 93,
		incomplete: 2,
		name: `4 ${name}`,
		passed: 2071,
		test_fix: 0,
	},
	{
		blocked: 0,
		failed: 78,
		incomplete: 2,
		name: `5 ${name}`,
		passed: 2411,
		test_fix: 0,
	},
	{
		blocked: 0,
		failed: 134,
		incomplete: 2,
		name: `6 ${name}`,
		passed: 2258,
		test_fix: 0,
	},
	{
		blocked: 0,
		failed: 0,
		incomplete: 0,
		name: `7 ${name}`,
		passed: 0,
		test_fix: 0,
	},
	{
		blocked: 0,
		failed: 0,
		incomplete: 0,
		name: `8 ${name}`,
		passed: 0,
		test_fix: 0,
	},
	{
		blocked: 0,
		failed: 3332,
		incomplete: 2,
		name: `9 ${name}`,
		passed: 2411,
		test_fix: 0,
	},
	{
		blocked: 0,
		failed: 93,
		incomplete: 2,
		name: `10 ${name}`,
		passed: 2071,
		test_fix: 0,
	},
	{
		blocked: 0,
		failed: 78,
		incomplete: 2,
		name: `11 ${name}`,
		passed: 2411,
		test_fix: 0,
	},
	{
		blocked: 0,
		failed: 134,
		incomplete: 2,
		name: `12 ${name}`,
		passed: 2258,
		test_fix: 0,
	},
	{
		blocked: 0,
		failed: 0,
		incomplete: 0,
		name: `13 ${name}`,
		passed: 0,
		test_fix: 0,
	},
	{
		blocked: 0,
		failed: 93,
		incomplete: 2,
		name: `14 ${name}`,
		passed: 2071,
		test_fix: 0,
	},
	{
		blocked: 0,
		failed: 78,
		incomplete: 2,
		name: `15 ${name}`,
		passed: 2411,
		test_fix: 0,
	},
	{
		blocked: 0,
		failed: 134,
		incomplete: 2,
		name: `16 ${name}`,
		passed: 2258,
		test_fix: 0,
	},
	{
		blocked: 0,
		failed: 0,
		incomplete: 0,
		name: `17 ${name}`,
		passed: 0,
		test_fix: 0,
	},
];
