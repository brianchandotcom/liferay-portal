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

import {useEffect, useMemo, useState} from 'react';

import {Liferay} from '../services/liferay';
import {
	APIResponse,
	TestraySubTask,
	TestrayTask,
	TestrayTaskUser,
	testraySubTaskImpl,
	testrayTaskUsersImpl,
} from '../services/rest';
import {searchUtil} from '../util/search';
import {useFetch} from './useFetch';

export function useSidebarTask() {
	const [selectedTask, setSelectedTask] = useState<TestrayTask>();

	const {data: tasksUserResponse} = useFetch<APIResponse<TestrayTaskUser>>(
		`${testrayTaskUsersImpl.resource}&filter=${searchUtil.eq(
			'userId',
			Liferay.ThemeDisplay.getUserId()
		)}`,
		(response) => testrayTaskUsersImpl.transformDataFromList(response)
	);

	const {data: subtasksResponse} = useFetch<APIResponse<TestraySubTask>>(
		selectedTask
			? `${testraySubTaskImpl.resource}&filter=${searchUtil.eq(
					'taskId',
					selectedTask?.id
			  )}`
			: null
	);

	const tasks = useMemo(
		() =>
			(tasksUserResponse?.items || []).map(
				({task}) => task as TestrayTask
			),
		[tasksUserResponse?.items]
	);

	useEffect(() => setSelectedTask(tasks[0]), [tasks]);

	const subTasks = useMemo(() => subtasksResponse?.items || [], [
		subtasksResponse?.items,
	]);

	const filteredTasks = tasks.filter((task) => task !== selectedTask);

	return {
		filteredTasks,
		selectedTask,
		setSelectedTask,
		subTasks,
		tasks,
		tasksUserResponse,
	};
}
