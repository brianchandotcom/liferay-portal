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
import {Outlet, useOutletContext, useParams} from 'react-router-dom';

import {useFetch} from '../../../hooks/useFetch';
import useHeader from '../../../hooks/useHeader';
import i18n from '../../../i18n';
import {
	APIResponse,
	TestraySubTask,
	TestrayTask,
	liferayMessageBoardImpl,
	testraySubTaskImpl,
} from '../../../services/rest';
import {testraySubtaskIssuesImpl} from '../../../services/rest/TestraySubtaskIssues';
import {searchUtil} from '../../../util/search';

type OutletContext = {
	testrayTask: TestrayTask;
};

const SubtaskOutlet = () => {
	const {setHeading} = useHeader();
	const {subtaskId} = useParams();
	const {testrayTask} = useOutletContext<OutletContext>();

	const {data: testraySubtask, mutate: mutateSubtask} = useFetch<
		TestraySubTask
	>(testraySubTaskImpl.getResource(subtaskId as string), (response) =>
		testraySubTaskImpl.transformData(response)
	);

	const {data: testraySubtaskToMerged} = useFetch<
		APIResponse<TestraySubTask>
	>(
		`${testraySubTaskImpl.resource}&filter=${searchUtil.eq(
			'r_mergedToTestraySubtask_c_subtaskId',
			subtaskId as string
		)}&pageSize=100&fields=name`,
		(response) => testraySubTaskImpl.transformDataFromList(response)
	);

	const {data, mutate: mutateSubtaskIssues} = useFetch(
		`${testraySubtaskIssuesImpl.resource}&filter=${searchUtil.eq(
			'subtaskId',
			subtaskId as string
		)}`,
		(response) => testraySubtaskIssuesImpl.transformDataFromList(response)
	);

	const {data: mbMessage} = useFetch(
		testraySubtask?.mbMessageId
			? liferayMessageBoardImpl.getMessagesIdURL(
					testraySubtask.mbMessageId
			  )
			: null
	);

	const subtaskIssues = data?.items || [];

	const mergedSubtaskNames = (testraySubtaskToMerged?.items || [])
		.map(({name}) => name)
		.join(', ');

	useEffect(() => {
		if (testraySubtask) {
			setTimeout(() => {
				setHeading([
					{
						category: i18n.translate('task'),
						path: `/testflow/${testrayTask?.id}`,
						title: testrayTask.name,
					},
					{
						category: i18n.translate('subtask'),
						title: testraySubtask.name,
					},
				]);
			});
		}
	}, [setHeading, testraySubtask, testrayTask]);

	return (
		<Outlet
			context={{
				mbMessage,
				mergedSubtaskNames,
				mutateSubtask,
				mutateSubtaskIssues,
				subtaskIssues,
				testraySubtask,
				testrayTask,
			}}
		/>
	);
};

export default SubtaskOutlet;
