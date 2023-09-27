/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {InferType} from 'yup';

import Rest from '../../core/Rest';
import SearchBuilder from '../../core/SearchBuilder';
import yupSchema from '../../schema/yup';
import {getUniqueList} from '../../util';
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
			nestedFields: 'task.build.project,task.build.routine,user',
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
			filter: SearchBuilder.eq('taskId', taskId),
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
