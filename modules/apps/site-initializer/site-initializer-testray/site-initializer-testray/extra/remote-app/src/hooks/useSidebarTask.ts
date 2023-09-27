/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMemo} from 'react';

import SearchBuilder from '../core/SearchBuilder';
import {Liferay} from '../services/liferay';
import {
	APIResponse,
	TestraySubTask,
	TestrayTaskUser,
	testraySubTaskImpl,
	testrayTaskUsersImpl,
} from '../services/rest';
import {SubTaskStatuses} from '../util/statuses';
import {useFetch} from './useFetch';

export function useSidebarTask() {
	const subTasksFilter = new SearchBuilder()
		.eq('userId', Liferay.ThemeDisplay.getUserId())
		.and()
		.ne('dueStatus', SubTaskStatuses.MERGED)
		.and()
		.ne('dueStatus', SubTaskStatuses.COMPLETE)
		.build();

	const taskFilters = new SearchBuilder()
		.eq('userId', Liferay.ThemeDisplay.getUserId())
		.build();

	const {data: tasksUserResponse} = useFetch<APIResponse<TestrayTaskUser>>(
		testrayTaskUsersImpl.resource,
		{
			params: {
				filter: taskFilters,
			},
			transformData: (response) =>
				testrayTaskUsersImpl.transformDataFromList(response),
		}
	);

	const {data: subtasksResponse} = useFetch<APIResponse<TestraySubTask>>(
		testraySubTaskImpl.resource,
		{
			params: {
				filter: subTasksFilter,
			},
			transformData: (response) =>
				testraySubTaskImpl.transformDataFromList(response),
		}
	);

	const subTasks = useMemo(() => subtasksResponse?.items || [], [
		subtasksResponse?.items,
	]);

	const tasks = useMemo(
		() =>
			(tasksUserResponse?.items || []).map(({task}) => ({
				...task,
				subTasks: subtasksResponse?.items.filter((subtask) => {
					return subtask?.task?.id === task?.id ? subtask : undefined;
				}),
			})),
		[subtasksResponse?.items, tasksUserResponse?.items]
	);

	return {
		subTasks,
		tasks,
		tasksUserResponse,
	};
}
