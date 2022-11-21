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
	TestrayTask,
	testraySubTaskImpl,
	testrayTaskUsersImpl,
} from '../services/rest';
import {searchUtil} from '../util/search';
import {useFetch} from './useFetch';

export function useSidebarTask() {
	const [selectedTask, setSelectedTask] = useState<number>();

	const userId = Liferay.ThemeDisplay.getUserId() || 0;

	const {data: tasksResponse} = useFetch<APIResponse<TestrayTask>>(
		`${testrayTaskUsersImpl.resource}&filter=${searchUtil.eq(
			'userId',
			userId as string
		)}&nestedFields=task`
	);

	const {data: subtasksResponse} = useFetch<APIResponse<TestraySubTask>>(
		`${testraySubTaskImpl.resource}&filter=${searchUtil.eq(
			'userId',
			userId as string
		)}`
	);

	const tasks = useMemo(() => tasksResponse?.items || [], [
		tasksResponse?.items,
	]);

	const subTasks = useMemo(() => subtasksResponse?.items || [], [
		subtasksResponse?.items,
	]);

	const showTask = () => {
		selectedTask;

		return tasks.find(({id}: {id: number}) => id === selectedTask);
	};

	const displayTask = showTask();

	return {
		displayTask,
		selectedTask,
		setSelectedTask,
		showTask,
		subTasks,
		tasks,
	};
}
