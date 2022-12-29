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
import {getUniqueList} from '../../util';
import {searchUtil} from '../../util/search';
import Rest from './Rest';
import {APIResponse, TestrayTaskUser} from './types';

type TaskToUser = InferType<typeof yupSchema.taskToUser>;

class TestrayTaskUsersImpl extends Rest<TaskToUser, TestrayTaskUser> {
	constructor() {
		super({
			adapter: ({
				name: name,
				taskId: r_taskToTasksUsers_c_taskId,
				userId: r_userToTasksUsers_userId,
			}) => ({
				name,
				r_taskToTasksUsers_c_taskId,
				r_userToTasksUsers_userId,
			}),
			nestedFields: 'task.build.project,task.build.routine,user,',
			transformData: (taskUser) => ({
				...taskUser,
				task: taskUser.r_taskToTasksUsers_c_task
					? {
							...taskUser.r_taskToTasksUsers_c_task,
							build: taskUser?.r_taskToTasksUsers_c_task
								?.r_buildToTasks_c_build
								? {
										...taskUser?.r_taskToTasksUsers_c_task
											?.r_buildToTasks_c_build,
										project:
											taskUser?.r_taskToTasksUsers_c_task
												?.r_buildToTasks_c_build
												?.r_projectToBuilds_c_project,
										routine:
											taskUser?.r_taskToTasksUsers_c_task
												?.r_buildToTasks_c_build
												?.r_routineToBuilds_c_routine,
								  }
								: undefined,
					  }
					: undefined,
				user: taskUser.r_userToTasksUsers_user,
			}),
			uri: 'tasksuserses',
		});
	}

	public async assign(taskId: number, userIds: number[] | number) {
		let response = await this.getAll({
			filter: searchUtil.eq('taskId', taskId),
			pageSize: 100,
		});

		response = this.transformDataFromList(
			response as APIResponse<TestrayTaskUser>
		);

		const taskUsers = response.items;

		const taskUserIds = taskUsers.map(({user}) => user?.id as number);

		const currentTaskUserIds = Array.isArray(userIds)
			? userIds
			: getUniqueList([...taskUserIds, userIds]);

		const userIdsToAdd = currentTaskUserIds.filter(
			(currentTaskUserId) => !taskUserIds.includes(currentTaskUserId)
		);

		const userIdsToRemove = taskUsers.filter(
			({user}) => !currentTaskUserIds.includes(user?.id as number)
		);

		if (userIdsToRemove.length) {
			await this.removeBatch(userIdsToRemove.map(({id}) => id));
		}

		if (userIdsToAdd.length) {
			await this.createBatch(
				userIdsToAdd.map((userId) => ({
					name: `${taskId}-${userId}`,
					taskId,
					userId,
				}))
			);
		}
	}
}

const testrayTaskUsersImpl = new TestrayTaskUsersImpl();

export {testrayTaskUsersImpl};
