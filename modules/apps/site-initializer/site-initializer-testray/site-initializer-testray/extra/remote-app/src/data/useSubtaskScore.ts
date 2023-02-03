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

import {useFetch} from '../hooks/useFetch';
import {
	APIResponse,
	TestraySubTask,
	TestrayTask,
	testraySubTaskImpl,
} from '../services/rest';
import {TaskStatuses} from '../util/statuses';

const useSubtaskScore = ({
	testrayTask,
	userId,
}: {
	testrayTask: TestrayTask;
	userId: number;
}) => {
	const progressScore = {
		completed: testrayTask.subtaskScoreCompleted,
		incomplete: testrayTask.subtaskScoreSelfIncomplete,
		othersCompleted: 0,
		selfCompleted: 0,
	};

	const {data: testraySubtasks} = useFetch<APIResponse<TestraySubTask>>(
		testraySubTaskImpl.resource,
		{
			params: {
				fields: 'r_userToSubtasks_userId,dueStatus,score',
				pageSize: 999,
			},
		}
	);

	for (const subtask of testraySubtasks?.items ?? []) {
		if (subtask.dueStatus.key !== TaskStatuses.COMPLETE) {
			continue;
		}

		const property =
			subtask.r_userToSubtasks_userId === userId
				? 'selfCompleted'
				: 'othersCompleted';

		progressScore[property] += subtask.score;
	}

	return progressScore;
};

export default useSubtaskScore;
