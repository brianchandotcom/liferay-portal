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

import TestrayError from '../../TestrayError';
import i18n from '../../i18n';
import yupSchema from '../../schema/yup';
import {SearchBuilder, searchUtil} from '../../util/search';
import {TaskStatuses} from '../../util/statuses';
import Rest from './Rest';
import {testrayTaskCaseTypesImpl} from './TestrayTaskCaseTypes';
import {testrayTaskUsersImpl} from './TestrayTaskUsers';
import {APIResponse, TestrayTask, TestrayTaskUser} from './types';

type TaskForm = typeof yupSchema.task.__outputType & {projectId: number};

type NestedObjectOptions =
	| 'taskToSubtasks'
	| 'taskToTasksCaseTypes'
	| 'taskToTasksUsers';

class TestrayTaskImpl extends Rest<TaskForm, TestrayTask, NestedObjectOptions> {
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
			nestedFields: 'build.project,build.routine,taskToTasksCaseTypes',
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

	private async assignUsers(taskId: number, userIds: number[]) {
		let response = await testrayTaskUsersImpl.getAll(
			searchUtil.eq('taskId', taskId)
		);

		response = testrayTaskUsersImpl.transformDataFromList(
			response as APIResponse<TestrayTaskUser>
		);

		const currentTaskUserIds = (userIds || []) as number[];

		const taskUsers = response.items;

		const taskUserIds = taskUsers.map(({user}) => user?.id as number);

		const userIdsToAdd = currentTaskUserIds.filter(
			(currentTaskUserId) => !taskUserIds.includes(currentTaskUserId)
		);

		const userIdsToRemove = taskUsers.filter(
			({user}) => !currentTaskUserIds.includes(user?.id as number)
		);

		if (userIdsToRemove.length) {
			await testrayTaskUsersImpl.removeBatch(
				userIdsToRemove.map(({id}) => id)
			);
		}

		if (userIdsToAdd.length) {
			await testrayTaskUsersImpl.createBatch(
				userIdsToAdd.map((userId) => ({
					name: `${taskId}-${userId}`,
					taskId,
					userId,
				}))
			);
		}
	}

	public async assignTo(task: TestrayTask, userIds: number[]) {
		const response = await this.update(task.id, {
			dueStatus: TaskStatuses.IN_ANALYSIS,
			name: task.name as string,
		});

		await this.assignUsers(task.id, userIds);

		return response;
	}

	public abandon(task: TestrayTask) {
		return this.update(task.id, {
			dueStatus: TaskStatuses.ABANDONED,
			name: task.name,
		});
	}

	public complete(task: TestrayTask) {
		return this.update(task.id, {
			dueStatus: TaskStatuses.COMPLETE,
			name: task.name,
		});
	}

	public getTasksByBuildId(buildId: number) {
		return this.fetcher<APIResponse<TestrayTask>>(
			`/tasks?filter=${searchUtil.eq('buildId', buildId)}`
		);
	}

	protected async validate(task: TaskForm, id?: number) {
		const searchBuilder = new SearchBuilder();

		if (id) {
			searchBuilder.ne('id', id).and();
		}

		const filter = searchBuilder.eq('name', task.name).build();

		const response = await this.fetcher<APIResponse<TestrayTask>>(
			`/tasks?filter=${filter}`
		);

		if (response?.totalCount) {
			throw new TestrayError(
				i18n.sub('the-x-name-already-exists', 'tasks')
			);
		}
	}

	public async create(data: TaskForm): Promise<TestrayTask> {
		const task = await super.create(data);

		const userIds = data.userIds || [];

		const caseTypeIds = data.caseTypes || [];

		if (userIds.length) {
			await testrayTaskUsersImpl.createBatch(
				userIds.map((userId) => ({
					name: `${task.id}-${userId}`,
					taskId: task.id,
					userId,
				}))
			);
		}

		if (caseTypeIds.length) {
			await testrayTaskCaseTypesImpl.createBatch(
				caseTypeIds.map((caseTypeId) => ({
					caseTypeId,
					name: `${task.id}-${caseTypeId}`,
					taskId: task.id,
				}))
			);
		}

		return task;
	}

	public async update(
		id: number,
		data: Partial<TaskForm>
	): Promise<TestrayTask> {
		const task = await super.update(id, data);

		if (data.dueStatus === TaskStatuses.IN_ANALYSIS) {
			await this.assignUsers(id, data.userIds as number[]);
		}

		return task;
	}

	protected async beforeCreate(task: TaskForm): Promise<void> {
		await this.validate(task);
	}

	protected async beforeUpdate(id: number, task: TaskForm): Promise<void> {
		await this.validate(task, id);
	}
}

export const testrayTaskImpl = new TestrayTaskImpl();
