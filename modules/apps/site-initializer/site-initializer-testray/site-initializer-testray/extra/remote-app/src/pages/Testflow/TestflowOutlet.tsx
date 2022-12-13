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
import i18n from '../../i18n';
import {
	APIResponse,
	TestrayTask,
	TestrayTaskCaseTypes,
	TestrayTaskUser,
	testrayTaskImpl,
	testrayTaskUsersImpl,
} from '../../services/rest';
import {testrayTaskCaseTypesImpl} from '../../services/rest/TestrayTaskCaseTypes';
import {searchUtil} from '../../util/search';

const TestflowOutlet = () => {
	const {pathname} = useLocation();
	const {taskId} = useParams();

	const currentPathIsActive = pathname === '/testflow';
	const archivedPathIsActive = pathname === '/testflow/archived';

	const {data: testrayTask, mutate: mutateTask} = useFetch<TestrayTask>(
		taskId ? testrayTaskImpl.getResource(taskId) : null,
		(response) => testrayTaskImpl.transformData(response)
	);

	const {data: taskCaseTypesResponse} = useFetch<
		APIResponse<TestrayTaskCaseTypes>
	>(
		taskId
			? `${testrayTaskCaseTypesImpl.resource}&filter=${searchUtil.eq(
					'taskId',
					taskId as string
			  )}`
			: null,
		(response) => testrayTaskCaseTypesImpl.transformDataFromList(response)
	);

	const taskCaseTypes = (taskCaseTypesResponse?.items || []).map(
		({caseType}) => caseType?.id
	);

	const {data: taskUserResponse, mutate: mutateTaskUsers} = useFetch<
		APIResponse<TestrayTaskUser>
	>(
		taskId
			? `${testrayTaskUsersImpl.resource}&filter=${searchUtil.eq(
					'taskId',
					taskId as string
			  )}`
			: null,
		(response) => testrayTaskUsersImpl.transformDataFromList(response)
	);

	const taskUser = (taskUserResponse?.items || []).map(({user}) => user?.id);

	const {setDropdownIcon, setHeading, setTabs} = useHeader({
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
		if (testrayTask) {
			setHeading([
				{
					category: i18n.translate('tasks'),
					title: testrayTask.name,
				},
			]);
		}
	}, [setHeading, testrayTask]);

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

	return (
		<Outlet
			context={{
				mutateTask,
				mutateTaskUsers,
				setDropdownIcon,
				setHeading,
				setTabs,
				taskCaseTypes,
				taskUser,
				taskUserResponse,
				testrayTask,
			}}
		/>
	);
};

export default TestflowOutlet;
