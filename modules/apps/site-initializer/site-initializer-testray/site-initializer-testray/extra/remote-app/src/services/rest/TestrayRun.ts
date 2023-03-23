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
import Rest from '../../core/Rest';
import SearchBuilder from '../../core/SearchBuilder';
import yupSchema from '../../schema/yup';
import {DISPATCH_TRIGGER_TYPE} from '../../util/enum';
import {DispatchTriggerStatuses} from '../../util/statuses';
import {liferayDispatchTriggerImpl} from './LiferayDispatchTrigger';
import {testrayDispatchTriggerImpl} from './TestrayDispatchTrigger';
import {APIResponse, TestrayRun} from './types';

type RunForm = Omit<typeof yupSchema.run.__outputType, 'id'>;

class TestrayRunImpl extends Rest<RunForm, TestrayRun> {
	constructor() {
		super({
			adapter: ({
				buildId: r_buildToRuns_c_buildId,
				description,
				environmentHash,
				name,
				number,
			}) => ({
				description,
				environmentHash,
				name,
				number,
				r_buildToRuns_c_buildId,
			}),
			nestedFields: 'build.routine,build.projectToBuilds',
			transformData: (run) => {
				const environmentValues = run.name.split('|');

				const [
					applicationServer,
					browser,
					database,
					javaJDK,
					operatingSystem,
				] = environmentValues;

				return {
					...run,
					applicationServer,
					browser,
					build: run?.r_buildToRuns_c_build
						? {
								...run.r_buildToRuns_c_build,
								project:
									run.r_buildToRuns_c_build
										.r_projectToBuilds_c_project,
								routine:
									run.r_buildToRuns_c_build
										.r_routineToBuilds_c_routine,
						  }
						: undefined,
					database,
					javaJDK,
					operatingSystem,
				};
			},
			uri: 'runs',
		});
	}

	public async autofill(
		objectEntryId1: number,
		objectEntryId2: number,
		autofillType: 'Build' | 'Run'
	) {
		const name = `AUTOFILL-${objectEntryId1}/${objectEntryId2}-${autofillType}-${new Date().getTime()}`;

		if (autofillType === 'Build') {
			const response = await this.getAll({
				filter: SearchBuilder.in('id', [
					objectEntryId1,
					objectEntryId2,
				]),
			});

			const [runA, runB] =
				this.transformDataFromList(response as APIResponse<TestrayRun>)
					?.items ?? [];

			objectEntryId1 = runA.build?.id as number;
			objectEntryId2 = runB.build?.id as number;
		}

		const response = await liferayDispatchTriggerImpl.create({
			active: true,
			dispatchTaskExecutorType: DISPATCH_TRIGGER_TYPE.AUTO_FILL,
			dispatchTaskSettings: {
				autofillType,
				objectEntryId1,
				objectEntryId2,
			},
			externalReferenceCode: name,
			name,
			overlapAllowed: false,
		});

		const body = {
			dueStatus: DispatchTriggerStatuses.INPROGRESS,
			output: '',
		};

		try {
			await liferayDispatchTriggerImpl.run(
				response.liferayDispatchTrigger.id
			);
		}
		catch (error) {
			body.dueStatus = DispatchTriggerStatuses.FAILED;
			body.output = (error as TestrayError)?.message;
		}

		await testrayDispatchTriggerImpl.update(
			response.testrayDispatchTrigger.id,
			body
		);
	}
}

export const testrayRunImpl = new TestrayRunImpl();
