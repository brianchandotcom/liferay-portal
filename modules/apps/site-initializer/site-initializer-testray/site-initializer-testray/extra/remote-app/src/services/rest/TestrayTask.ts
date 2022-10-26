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

import i18n from '../../i18n';
import yupSchema from '../../schema/yup';
import {SearchBuilder, searchUtil} from '../../util/search';
import {TaskStatuses} from '../../util/statuses';
import Rest from './Rest';
import {APIResponse, TestrayTask} from './types';

type TaskForm = typeof yupSchema.task.__outputType & {projectId: number};

class TestrayTaskImpl extends Rest<TaskForm, TestrayTask> {
	constructor() {
		super({
			adapter: ({
				buildId: r_buildToTasks_c_buildId,
				caseTypes: taskToTasksCaseTypes,
				dueStatus = TaskStatuses.IN_ANALYSIS,
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
			throw new Error(i18n.sub('the-x-name-already-exists', 'tasks'));
		}
	}

	protected async beforeCreate(task: TaskForm): Promise<void> {
		await this.validate(task);
	}

	protected async beforeUpdate(id: number, task: TaskForm): Promise<void> {
		await this.validate(task, id);
	}
}
export const testrayTaskImpl = new TestrayTaskImpl();
