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

import {gql} from '@apollo/client';

import {TestrayBuild} from './testrayBuild';

export type TestrayTask = {
	dateCreated: string;
	dueStatus: number;
	id: number;
	name: string;
	testrayBuild?: TestrayBuild;
};

export const getTestrayTasks = gql`
	query getTestrayTasks(
		$filter: String
		$page: Int = 1
		$pageSize: Int = 20
	) {
		testrayTasks(filter: $filter, page: $page, pageSize: $pageSize)
			@rest(
				type: "C_TestrayTask"
				path: "testraytasks?page={args.page}&pageSize={args.pageSize}&nestedFields=testrayBuild.testrayProject,testrayBuild.testrayRoutine"
			) {
			items {
				dateCreated
				dueStatus
				name
				testrayBuild: r_taskBuild_c_testrayBuild {
					id
					dueDate
					name
					testrayProject: r_buildProject_c_testrayProject {
						id
						name
					}
					testrayRoutine: r_buildRoutine_c_testrayRoutine {
						id
						name
					}
				}
				id
			}
			lastPage
			page
			pageSize
			totalCount
		}
	}
`;

export const getTestrayTask = gql`
	query getTestrayTask($testrayTaskId: Long!) {
		testrayTask(testrayTaskId: $testrayTaskId)
			@rest(
				type: "C_TestrayTask"
				path: "testraytasks/{args.testrayTaskId}?nestedFields=testrayBuild.testrayProject,testrayBuild.testrayRoutine"
			) {
			dateCreated
			dueStatus
			name
			testrayBuild: r_taskBuild_c_testrayBuild {
				id
				dueDate
				name
				testrayProject: r_buildProject_c_testrayProject {
					id
					name
				}
				testrayRoutine: r_buildRoutine_c_testrayRoutine {
					id
					name
				}
			}
			id
		}
	}
`;
