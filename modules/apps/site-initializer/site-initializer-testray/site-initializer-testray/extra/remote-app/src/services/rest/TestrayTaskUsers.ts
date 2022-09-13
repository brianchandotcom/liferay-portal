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

type TaskToUser = typeof yupSchema.taskToUser.__outputType & {
	projectId: number;
};

class TestrayTaskUsersImpl extends Rest {
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
			transformData: (TaskToUser) => ({
				...TaskToUser,
				taskId: TaskToUser.r_taskToTasksUsers_c_taskId,
				userId: TaskToUser.r_userToTasksUsers_userId,
			}),
			uri: 'tasksuserses',
		});
	}

	public async create(data: TaskToUser): Promise<any> {
		const taskTouser = await super.create(data);

		return taskTouser;
	}
}

const testrayTaskUsersImpl = new TestrayTaskUsersImpl();

export {testrayTaskUsersImpl};
