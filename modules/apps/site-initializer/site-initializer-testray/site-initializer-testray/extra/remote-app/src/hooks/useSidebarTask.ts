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

import {useMemo} from 'react';

import {Liferay} from '../services/liferay';
import {
	APIResponse,
	TestraySubTask,
	TestrayTaskUser,
	testraySubTaskImpl,
	testrayTaskUsersImpl,
} from '../services/rest';
import {SearchBuilder} from '../util/search';
import {SubTaskStatuses} from '../util/statuses';
import {useFetch} from './useFetch';

export function useSidebarTask() {
	const taskSearchBuilder = new SearchBuilder();
	const subTaskSearchBuilder = new SearchBuilder();

	const taskFilters = taskSearchBuilder
		.eq('userId', Liferay.ThemeDisplay.getUserId())
		.build();

	const subTasksFilter = subTaskSearchBuilder
		.eq('userId', Liferay.ThemeDisplay.getUserId())
		.and()
		.ne('dueStatus', SubTaskStatuses.MERGED)
		.and()
		.ne('dueStatus', SubTaskStatuses.COMPLETE)
		.build();

	const {data: tasksUserResponse} = useFetch<APIResponse<TestrayTaskUser>>(
		`${testrayTaskUsersImpl.resource}&filter=${taskFilters}`,
		(response) => testrayTaskUsersImpl.transformDataFromList(response)
	);

	const {data: subtasksResponse} = useFetch<APIResponse<TestraySubTask>>(
		`${testraySubTaskImpl.resource}&filter=${subTasksFilter}`,
		(response) => testraySubTaskImpl.transformDataFromList(response)
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
