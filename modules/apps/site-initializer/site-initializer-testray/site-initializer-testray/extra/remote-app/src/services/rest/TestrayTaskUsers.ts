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
import {TestrayTaskUser} from './types';

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
			nestedFields: '',
			transformData: (taskUser) => ({
				...taskUser,
				task: taskUser.r_taskToTasksUsers_c_task,
				user: taskUser.r_userToTasksUsers_user,
			}),
			uri: 'tasksuserses',
		});
	}
}

const testrayTaskUsersImpl = new TestrayTaskUsersImpl();

export {testrayTaskUsersImpl};
