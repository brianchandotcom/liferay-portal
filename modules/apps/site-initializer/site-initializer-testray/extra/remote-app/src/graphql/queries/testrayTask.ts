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
	build?: TestrayBuild;
	dateCreated: string;
	dueStatus: number;
	id: number;
	name: string;
};

export const getTasks = gql`
	query getTasks($filter: String, $page: Int = 1, $pageSize: Int = 20) {
		tasks(filter: $filter, page: $page, pageSize: $pageSize)
			@rest(
				type: "C_Task"
				path: "tasks?page={args.page}&pageSize={args.pageSize}&nestedFields=build.project,build.routine"
			) {
			items {
				dateCreated
				dueStatus
				name
				build: r_taskBuild_c_Build {
					dueDate
					id
					name
					project: r_buildProject_c_Project {
						id
						name
					}
					routine: r_buildRoutine_c_Routine {
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

export const getTask = gql`
	query getTask($taskId: Long!) {
		task(taskId: $taskId)
			@rest(
				type: "C_Task"
				path: "tasks/{args.taskId}?nestedFields=build.project,build.routine"
			) {
			dateCreated
			dueStatus
			name
			build: r_taskBuild_c_Build {
				id
				dueDate
				name
				project: r_buildProject_c_Project {
					id
					name
				}
				routine: r_buildRoutine_c_Routine {
					id
					name
				}
			}
			id
		}
	}
`;
