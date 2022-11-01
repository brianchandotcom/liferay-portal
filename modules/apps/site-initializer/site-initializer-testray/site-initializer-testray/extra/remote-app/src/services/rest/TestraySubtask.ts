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
import {SubTaskStatuses} from '../../util/statuses';
import {Liferay} from '../liferay';
import Rest from './Rest';
import {testrayCaseResultImpl} from './TestrayCaseResult';
import {TestraySubTask} from './types';

type SubtaskForm = typeof yupSchema.subtask.__outputType & {
	projectId: number;
};

class TestraySubtaskImpl extends Rest<SubtaskForm, TestraySubTask> {
	public UNASSIGNED_USER_ID = 0;

	constructor() {
		super({
			adapter: ({
				dueStatus,
				errors,
				name,
				score,
				taskId: r_taskToSubtasks_c_taskId,
				userId: r_userToSubtasks_userId,
			}) => ({
				dueStatus,
				errors,
				name,
				r_taskToSubtasks_c_taskId,
				r_userToSubtasks_userId,
				score,
			}),
			nestedFields: 'tasks,users',
			transformData: (subTask) => ({
				...subTask,
				task: subTask.r_taskToSubtasks_c_task,
				user: subTask.r_userToSubtasks_user,
			}),
			uri: 'subtasks',
		});
	}

	public assignTo(subTask: TestraySubTask, userId: number) {
		return this.update(subTask.id, {
			dueStatus: SubTaskStatuses.IN_ANALYSIS,
			userId,
		});
	}

	public assignToMe(subTask: TestraySubTask) {
		return this.update(subTask.id, {
			dueStatus: SubTaskStatuses.IN_ANALYSIS,
			userId: Number(Liferay.ThemeDisplay.getUserId()),
		});
	}

	public async complete(
		subtaskId: number,
		caseResultIds: number[],
		dueStatus: string
	) {
		await this.update(subtaskId, {
			dueStatus: SubTaskStatuses.COMPLETE,
			userId: Number(Liferay.ThemeDisplay.getUserId()),
		});

		await testrayCaseResultImpl.updateBatch(
			caseResultIds,
			caseResultIds.map(() => ({
				dueStatus,
			}))
		);
	}

	public returnToOpen(subTask: TestraySubTask) {
		return this.update(subTask.id, {
			dueStatus: SubTaskStatuses.OPEN,
			userId: this.UNASSIGNED_USER_ID,
		});
	}
}

export const testraySubTaskImpl = new TestraySubtaskImpl();
