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

import {InferType} from 'yup';

import yupSchema from '../../schema/yup';
import Rest from './Rest';
import {TestrayTaskCaseTypes} from './types';

type TaskToCaseTypes = InferType<typeof yupSchema.taskToCaseTypes>;

class TestrayTaskCaseTypesImpl extends Rest<
	TaskToCaseTypes,
	TestrayTaskCaseTypes
> {
	constructor() {
		super({
			adapter: ({
				caseTypeId: r_caseTypeToTasksCaseTypes_c_caseTypeId,
				name: name,
				taskId: r_taskToTasksCaseTypes_c_taskId,
			}) => ({
				name,
				r_caseTypeToTasksCaseTypes_c_caseTypeId,
				r_taskToTasksCaseTypes_c_taskId,
			}),
			nestedFields: 'caseType,task.build.project,task.build.routine,',
			transformData: (taskCaseType) => ({
				...taskCaseType,
				caseType: taskCaseType.r_caseTypeToTasksCaseTypes_c_caseType,
				task: taskCaseType.r_taskToTasksCaseTypes_c_taskId
					? {
							...taskCaseType.r_taskToTasksCaseTypes_c_taskId,
							build: taskCaseType?.r_taskToTasksCaseTypes_c_taskId
								?.r_buildToTasks_c_build
								? {
										...taskCaseType
											?.r_taskToTasksCaseTypes_c_taskId
											?.r_buildToTasks_c_build,
										project:
											taskCaseType
												?.r_taskToTasksCaseTypes_c_taskId
												?.r_buildToTasks_c_build
												?.r_projectToBuilds_c_project,
										routine:
											taskCaseType
												?.r_taskToTasksCaseTypes_c_taskId
												?.r_buildToTasks_c_build
												?.r_routineToBuilds_c_routine,
								  }
								: undefined,
					  }
					: undefined,
			}),
			uri: 'taskscasetypeses',
		});
	}
}

const testrayTaskCaseTypesImpl = new TestrayTaskCaseTypesImpl();

export {testrayTaskCaseTypesImpl};
