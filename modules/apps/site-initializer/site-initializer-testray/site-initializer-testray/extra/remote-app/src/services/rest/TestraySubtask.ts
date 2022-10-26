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
import {SUB_TASK_STATUS} from '../../util/constants';
import {Liferay} from '../liferay';
import Rest from './Rest';
import {testrayCaseResultRest} from './TestrayCaseResult';
import {TestraySubTask} from './types';

type SubtaskForm = typeof yupSchema.subtask.__outputType & {
	projectId: number;
};

class TestraySubtaskImpl extends Rest<SubtaskForm, TestraySubTask> {
	constructor() {
		super({
			adapter: ({
				dueStatus,
				name,
				score,
				taskId: r_taskToSubtasks_c_taskId,
				userId: r_userToSubtasks_userId,
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
				task: subtask.r_taskToSubtasks_c_task,
				user: subtask.r_userToSubtasks_user,
			}),
			uri: 'subtasks',
		});
	}

	public assignTo(subtask: TestraySubTask, userId: number) {
		const data = {
			dueStatus: SUB_TASK_STATUS['IN_ANALYSIS'],
			r_userToSubtasks_userId: userId,
		};

		return this.fetcher.put(`/subtasks/${subtask.id}`, data);
	}

	public assignToMe(subtask: TestraySubTask) {
		const data = {
			dueStatus: SUB_TASK_STATUS['IN_ANALYSIS'],
			r_userToSubtasks_userId: Liferay.ThemeDisplay.getUserId(),
		};

		return this.fetcher.put(`/subtasks/${subtask.id}`, data);
	}

	public async complete(
		subtaskId: number,
		caseResultIds: number[],
		dueStatus: string
	) {
		await this.update(subtaskId, {
			dueStatus: SUB_TASK_STATUS['COMPLETE'],
			userId: Number(Liferay.ThemeDisplay.getUserId()),
		});

		await testrayCaseResultRest.updateBatch(
			caseResultIds,
			caseResultIds.map(() => ({
				dueStatus: (dueStatus as unknown) as string,
			}))
		);
	}

	public returnToOpen(subtask: TestraySubTask) {
		const data = {
			dueStatus: SUB_TASK_STATUS['OPEN'],
			r_userToSubtasks_userId: 0,
		};

		return this.fetcher.put(`/subtasks/${subtask.id}`, data);
	}
}

export const testraySubtaskImpl = new TestraySubtaskImpl();
