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

import {CategoryOptions} from '../../pages/Project/Routines/Builds/BuildForm/BuildFormRun';
import yupSchema from '../../schema/yup';
import {TEST_STATUS} from '../../util/constants';
import Rest from './Rest';
import {testrayCaseResultRest} from './TestrayCaseResult';
import {testrayFactorRest} from './TestrayFactor';
import {testrayRunRest} from './TestrayRun';

import type {TestrayBuild} from './types';

type Build = typeof yupSchema.build.__outputType & {projectId: number};

class TestrayBuildRest extends Rest<Build, TestrayBuild> {
	constructor() {
		super({
			adapter: ({
				description,
				gitHash,
				name,
				productVersionId: r_productVersionToBuilds_c_productVersionId,
				projectId: r_projectToBuilds_c_projectId,
				promoted,
				routineId: r_routineToBuilds_c_routineId,
				template,
			}) => ({
				description,
				gitHash,
				name,
				promoted,
				r_productVersionToBuilds_c_productVersionId,
				r_projectToBuilds_c_projectId,
				r_routineToBuilds_c_routineId,
				template,
			}),
			nestedFields: 'productVersion&nestedFieldsDepth=2',
			transformData: (testrayBuild) => ({
				...testrayBuild,
				creator: testrayBuild?.creator || {},
				productVersion:
					testrayBuild?.r_productVersionToBuilds_c_productVersion,
			}),
			uri: 'builds',
		});
	}

	public async create(data: Build): Promise<TestrayBuild> {
		const build = await super.create(data);

		const caseIds = data.caseIds || [];
		const runs = data.categories || [];

		let runIndex = 1;

		for (const run of runs) {
			const factorOptions = Object.values(run) as CategoryOptions[];

			const factorOptionsList = factorOptions.map(
				({factorOption}) => factorOption
			);

			const testrayRun = await testrayRunRest.create({
				buildId: build.id,
				description: undefined,
				environmentHash: undefined,
				name: factorOptionsList.join(' | '),
				number: runIndex,
			});

			for (const factorOption of factorOptions) {
				await testrayFactorRest.create({
					factorCategoryId: (factorOption.factorCategoryId as unknown) as string,
					factorOptionId: (factorOption.factorOptionId as unknown) as string,
					name: '',
					routineId: undefined,
					runId: testrayRun.id,
				});
			}

			await testrayCaseResultRest.createBatch(
				caseIds.map((caseId) => ({
					buildId: build.id,
					caseId,
					commentMBMessage: undefined,
					dueStatus: TEST_STATUS.Untested.toString(),
					issues: undefined,
					runId: testrayRun.id,
					startDate: undefined,
					userId: 0,
				}))
			);

			runIndex++;
		}

		return build as TestrayBuild;
	}
}

export const testrayBuildRest = new TestrayBuildRest();
