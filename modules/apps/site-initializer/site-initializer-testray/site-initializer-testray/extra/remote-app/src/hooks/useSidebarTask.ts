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

import {useMemo, useState} from 'react';

import {Liferay} from '../services/liferay';
import {
	APIResponse,
	TestraySubTask,
	TestrayTaskUser,
	testraySubTaskImpl,
	testrayTaskUsersImpl,
} from '../services/rest';
import {searchUtil} from '../util/search';
import {useFetch} from './useFetch';

export function useSidebarTask() {
	const [selectedTask, setSelectedTask] = useState<number>(0);

	const userId = Number(Liferay.ThemeDisplay.getUserId());

	const taskId = 203220;

	const {data: tasksUserResponse} = useFetch<APIResponse<TestrayTaskUser>>(
		`${testrayTaskUsersImpl.resource}&filter=${searchUtil.eq(
			'userId',
			userId
		)}`,
		(response) => testrayTaskUsersImpl.transformDataFromList(response)
	);

	const {data: subtasksResponse} = useFetch<APIResponse<TestraySubTask>>(
		`${testraySubTaskImpl.resource}&filter=${searchUtil.eq(
			'taskId',
			taskId
		)}`
	);

	const tasks = useMemo(() => tasksUserResponse?.items || [], [
		tasksUserResponse?.items,
	]);

	const subTasks = useMemo(() => subtasksResponse?.items || [], [
		subtasksResponse?.items,
	]);

	const displayTask = tasks.find(({id}) => id === selectedTask);

	return {
		displayTask,
		selectedTask,
		setSelectedTask,
		subTasks,
		tasks,
	};
}
