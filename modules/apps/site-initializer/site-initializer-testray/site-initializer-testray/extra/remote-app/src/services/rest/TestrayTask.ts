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
import {DISPATCH_TRIGGER_TYPE} from '../../util/enum';
import {SearchBuilder, searchUtil} from '../../util/search';
import {DispatchTriggerStatuses, TaskStatuses} from '../../util/statuses';
import {liferayDispatchTriggerImpl} from './LiferayDispatchTrigger';
import Rest from './Rest';
import {testrayDispatchTriggerImpl} from './TestrayDispatchTrigger';
import {testrayTaskCaseTypesImpl} from './TestrayTaskCaseTypes';
import {testrayTaskUsersImpl} from './TestrayTaskUsers';
import {APIResponse, TestrayTask} from './types';

type TaskForm = typeof yupSchema.task.__outputType & {
	dispatchTriggerId: number;
	projectId: number;
};

type NestedObjectOptions =
	| 'taskToSubtasks'
	| 'taskToTasksCaseTypes'
	| 'taskToTasksUsers';

class TestrayTaskImpl extends Rest<TaskForm, TestrayTask, NestedObjectOptions> {
	constructor() {
		super({
			adapter: ({
				dispatchTriggerId,
				buildId: r_buildToTasks_c_buildId,
				caseTypes: taskToTasksCaseTypes,
				dueStatus = TaskStatuses.OPEN,
				name,
			}) => ({
				dispatchTriggerId,
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

	public abandon(task: TestrayTask) {
		return this.update(task.id, {
			dueStatus: TaskStatuses.ABANDONED,
			name: task.name,
		});
	}

	public async assignTo(task: TestrayTask, userIds: number[]) {
		return testrayTaskUsersImpl.assign(task.id, userIds);
	}

	protected async beforeCreate(task: TaskForm): Promise<void> {
		await this.validate(task);
	}

	protected async beforeUpdate(id: number, task: TaskForm): Promise<void> {
		await this.validate(task, id);
	}

	public complete(task: TestrayTask) {
		return this.update(task.id, {
			dueStatus: TaskStatuses.COMPLETE,
			name: task.name,
		});
	}

	public async create(data: TaskForm): Promise<TestrayTask> {
		const task = await super.create(data);

		const caseTypeIds = data.caseTypes || [];

		if (caseTypeIds.length) {
			await testrayTaskCaseTypesImpl.createBatch(
				caseTypeIds.map((caseTypeId) => ({
					caseTypeId,
					name: `${task.id}-${caseTypeId}`,
					taskId: task.id,
				}))
			);
		}

		const dispatchTrigger = await liferayDispatchTriggerImpl.create({
			active: true,
			dispatchTaskExecutorType: DISPATCH_TRIGGER_TYPE.CREATE_TASK_SUBTASK,
			dispatchTaskSettings: {
				testrayBuildId: data.buildId,
				testrayCaseTypeIds: data.caseTypes,
				testrayTaskId: task.id,
			},
			externalReferenceCode: `T-${task.id}`,
			name: `T-${task.id} / ${data.name}`,
			overlapAllowed: false,
		});

		const dispatchTriggerId = dispatchTrigger.liferayDispatchTrigger.id;

		await Promise.allSettled([
			super.update(task.id, {
				...data,
				dispatchTriggerId,
			}),
			liferayDispatchTriggerImpl.run(dispatchTriggerId),
		]);

		const body = {
			dueStatus: DispatchTriggerStatuses.INPROGRESS,
			output: '',
		};

		try {
			await liferayDispatchTriggerImpl.run(
				dispatchTrigger.liferayDispatchTrigger.id
			);
		} catch (error) {
			body.dueStatus = DispatchTriggerStatuses.FAILED;
			body.output = (error as TestrayError)?.message;
		}

		await testrayDispatchTriggerImpl.update(
			dispatchTrigger.testrayDispatchTrigger.id,
			body
		);

		return {...task, dispatchTriggerId};
	}

	public getTasksByBuildId(buildId: number) {
		return this.fetcher<APIResponse<TestrayTask>>(
			`/tasks?filter=${searchUtil.eq('buildId', buildId)}`
		);
	}

	public async reanalyze(task: TestrayTask) {
		return this.update(task.id, {
			dueStatus: TaskStatuses.IN_ANALYSIS,
			name: task.name as string,
		});
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
}

export const testrayTaskImpl = new TestrayTaskImpl();
