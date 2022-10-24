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
import {TestraySubTask} from './types';

type subtaskForm = typeof yupSchema.subtask.__outputType & {projectId: number};

class TestraySubtaskImpl extends Rest<subtaskForm, TestraySubTask> {
	constructor() {
		super({
			adapter: ({
				dueStatus,
				name,
				score,
				taskId: r_taskToSubtasks_c_taskId,
				users: r_userToSubtasks_userId,
			}) => ({
				dueStatus,
				name,
				r_taskToSubtasks_c_taskId,
				r_userToSubtasks_userId,
				score,
			}),
			nestedFields: 'tasks,users',
			transformData: (subtask) => ({
				...subtask,
				taskId: subtask.r_taskToSubtasks_c_task,
				userId: subtask.r_userToSubtasks_user,
			}),
			uri: 'subtasks',
		});
	}
}
export const testraySubtaskImpl = new TestraySubtaskImpl();
