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
