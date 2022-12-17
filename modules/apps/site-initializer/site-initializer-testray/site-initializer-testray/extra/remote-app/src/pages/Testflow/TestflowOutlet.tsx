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

import {useEffect} from 'react';
import {Outlet, useLocation, useParams} from 'react-router-dom';

import {useFetch} from '../../hooks/useFetch';
import useHeader from '../../hooks/useHeader';
import useSearchBuilder from '../../hooks/useSearchBuilder';
import i18n from '../../i18n';
import {
	APIResponse,
	TestraySubTask,
	TestrayTask,
	TestrayTaskCaseTypes,
	TestrayTaskUser,
	testraySubTaskImpl,
	testrayTaskImpl,
	testrayTaskUsersImpl,
} from '../../services/rest';
import {testrayTaskCaseTypesImpl} from '../../services/rest/TestrayTaskCaseTypes';
import {searchUtil} from '../../util/search';
import {SubTaskStatuses} from '../../util/statuses';

const TestflowNavigationOutlet = () => {
	const {pathname} = useLocation();

	const currentPathIsActive = pathname === '/testflow';
	const archivedPathIsActive = pathname === '/testflow/archived';

	const {setTabs} = useHeader({
		shouldUpdate: currentPathIsActive || archivedPathIsActive,
		useDropdown: [],
		useHeaderActions: {
			actions: [],
		},
		useHeading: [
			{
				category: i18n.translate('task').toUpperCase(),
				title: i18n.translate('testflow'),
			},
		],
	});

	useEffect(() => {
		setTabs([
			{
				active: currentPathIsActive,
				path: '/testflow',
				title: i18n.translate('current'),
			},
			{
				active: archivedPathIsActive,
				path: '/testflow/archived',
				title: i18n.translate('archived'),
			},
		]);
	}, [archivedPathIsActive, currentPathIsActive, setTabs]);

	return <Outlet />;
};

const TestflowOutlet = () => {
	const params = useParams();

	const taskId = params.taskId as string;

	const {data: testrayTask, mutate: mutateTask} = useFetch<TestrayTask>(
		testrayTaskImpl.getResource(taskId),
		(response) => testrayTaskImpl.transformData(response)
	);

	const {data: testrayTaskCaseTypes} = useFetch<
		APIResponse<TestrayTaskCaseTypes>
	>(
		`${testrayTaskCaseTypesImpl.resource}&filter=${searchUtil.eq(
			'taskId',
			taskId
		)}`,
		(response) => testrayTaskCaseTypesImpl.transformDataFromList(response)
	);

	const {data: testrayTaskUser, revalidate: revalidateTaskUser} = useFetch<
		APIResponse<TestrayTaskUser>
	>(
		`${testrayTaskImpl.getNestedObject(
			'taskToTasksUsers',
			Number(taskId)
		)}?nestedFields=task,user`,
		(response) => testrayTaskUsersImpl.transformDataFromList(response)
	);

	const searchBuilder = useSearchBuilder({useURIEncode: false});

	const subTaskFilter = searchBuilder
		.eq('taskId', taskId)
		.and()
		.in('dueStatus', [
			SubTaskStatuses.IN_ANALYSIS,
			SubTaskStatuses.MERGED,
			SubTaskStatuses.OPEN,
		])
		.build();

	const {data: testraySubtasks, revalidate: revalidateSubtask} = useFetch<
		APIResponse<TestraySubTask>
	>(
		`${testraySubTaskImpl.resource}&fields=id&filter=${subTaskFilter}&pageSize=1`
	);

	if (!testrayTask) {
		return null;
	}

	return (
		<Outlet
			context={{
				data: {
					testraySubtasks,
					testrayTask,
					testrayTaskCaseTypes: testrayTaskCaseTypes?.items ?? [],
					testrayTaskUser: testrayTaskUser?.items ?? [],
				},
				mutate: {
					mutateTask,
				},
				revalidate: {
					revalidateSubtask,
					revalidateTaskUser,
				},
			}}
		/>
	);
};

export {TestflowNavigationOutlet};

export default TestflowOutlet;
