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

import yupSchema from '../../schema/yup';
import Rest from './Rest';
import {TestrayTask} from './types';

type TaskForm = typeof yupSchema.task.__outputType & {projectId: number};

class TestrayTaskImpl extends Rest<TaskForm, TestrayTask> {
	constructor() {
		super({
			adapter: ({
				buildId: r_buildToTasks_c_buildId,
				caseTypes: taskToTasksCaseTypes,
				dueStatus,
				name,
			}) => ({
				dueStatus,
				name,
				r_buildToTasks_c_buildId,
				taskToTasksCaseTypes,
			}),
			nestedFields: 'build.project,build.routine',
			transformData: (testrayTask) => ({
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
			}),
			uri: 'tasks',
		});
	}
}
export const testrayTaskImpl = new TestrayTaskImpl();
