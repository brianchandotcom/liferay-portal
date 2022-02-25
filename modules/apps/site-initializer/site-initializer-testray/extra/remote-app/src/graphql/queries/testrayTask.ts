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
	dueStatus: number;
	name: string;
	testrayBuild?: TestrayBuild;
};

const testrayTaskFragment = gql`
	fragment TestrayTaskFragment on C_TestrayTask {
		dueStatus
		name
		testrayBuild
	}
`;

export const getTestrayTask = gql`
	${testrayTaskFragment}

	query getTestrayTask($testrayTaskId: Long!) {
		c {
			testrayTask(testrayTaskId: $testrayTaskId) {
				...TestrayTaskFragment
			}
		}
	}
`;

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
				dueStatus
				name
				testrayBuild {
					dueDate
					name
					testrayProject {
						name
					}
					testrayRoutine {
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

export const getTestrayTaskRest = gql`
	query getTestrayTask($testrayTaskId: Long!) {
		testrayTask(testrayTaskId: $testrayTaskId)
			@rest(
				type: "C_TestrayTask"
				path: "testraytasks/{args.testrayTaskId}?nestedFields=testrayBuild.testrayProject,testrayBuild.testrayRoutine"
			) {
			dueStatus
			name
			testrayBuild {
				dueDate
				name
				testrayProject {
					name
				}
				testrayRoutine {
					name
				}
			}
			id
		}
	}
`;

export const getTestrayTasksXXX = gql`
	query getTestrayTasks(
		$filter: String
		$page: Int = 1
		$pageSize: Int = 20
	) {
		c {
			testrayTasks(filter: $filter, page: $page, pageSize: $pageSize) {
				items {
					...TestrayTaskFragment
				}
				lastPage
				page
				pageSize
				totalCount
			}
		}
	}
`;
