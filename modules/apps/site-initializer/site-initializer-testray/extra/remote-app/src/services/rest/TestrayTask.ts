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

import {APIResponse, TestrayTask} from './types';

const nestedFieldsParam =
	'nestedFields=build.project,build.routine&nestedFieldsDepth=2';

const tasksResource = `/tasks?${nestedFieldsParam}`;

const getTaskQuery = (taskId: number | string | undefined) =>
	`/tasks/${taskId}?${nestedFieldsParam}`;

const getTaskTransformData = (testrayTask: TestrayTask): TestrayTask => ({
	...testrayTask,
	build: testrayTask.r_buildToTasks_c_build
		? {
				...testrayTask.r_buildToTasks_c_build,
				productVersion:
					testrayTask.r_buildToTasks_c_build
						.r_productVersionToBuilds_c_productVersion,
				project:
					testrayTask.r_buildToTasks_c_build
						.r_projectToBuilds_c_project,
				routine:
					testrayTask.r_buildToTasks_c_build
						.r_routineToBuilds_c_routine,
		  }
		: undefined,
});

const getTasksTransformData = (response: APIResponse<TestrayTask>) => ({
	...response,
	items: response?.items?.map(getTaskTransformData),
});

export {
	tasksResource,
	getTaskQuery,
	getTaskTransformData,
	getTasksTransformData,
};
