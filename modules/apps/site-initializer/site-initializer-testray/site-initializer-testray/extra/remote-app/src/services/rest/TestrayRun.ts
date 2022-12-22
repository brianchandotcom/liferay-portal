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
import yupSchema from '../../schema/yup';
import {DISPATCH_TRIGGER_TYPE} from '../../util/enum';
import {DispatchTriggerStatuses} from '../../util/statuses';
import {liferayDispatchTriggerImpl} from './LiferayDispatchTrigger';
import Rest from './Rest';
import {testrayDispatchTriggerImpl} from './TestrayDispatchTrigger';
import {TestrayRun} from './types';

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
			nestedFields: 'build.routine',
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
		autoFillType: 'Build' | 'Run'
	) {
		const name = `AUTOFILL-${objectEntryId1}/${objectEntryId2}-${autoFillType}-${new Date().getTime()}`;

		const response = await liferayDispatchTriggerImpl.create({
			active: true,
			dispatchTaskExecutorType: DISPATCH_TRIGGER_TYPE.AUTO_FILL,
			dispatchTaskSettings: {
				autoFillType,
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
