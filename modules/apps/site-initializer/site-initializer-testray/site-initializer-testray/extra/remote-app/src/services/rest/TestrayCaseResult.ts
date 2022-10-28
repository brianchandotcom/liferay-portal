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
import {CaseResultStatuses} from '../../util/statuses';
import {Liferay} from '../liferay';
import Rest from './Rest';
import {TestrayCaseResult} from './types';

type CaseResultForm = typeof yupSchema.caseResult.__outputType;

class TestrayCaseResultRest extends Rest<CaseResultForm, TestrayCaseResult> {
	public UNASSIGNED_USER_ID = 0;

	constructor() {
		super({
			adapter: ({
				buildId: r_buildToCaseResult_c_buildId,
				caseId: r_caseToCaseResult_c_caseId,
				commentMBMessage,
				dueStatus,
				issues,
				runId: r_runToCaseResult_c_runId,
				startDate,
				userId: r_userToCaseResults_userId = Liferay.ThemeDisplay.getUserId(),
			}) => ({
				commentMBMessage,
				dueStatus,
				issues,
				r_buildToCaseResult_c_buildId,
				r_caseToCaseResult_c_caseId,
				r_runToCaseResult_c_runId,
				r_userToCaseResults_userId,
				startDate,
			}),
			nestedFields:
				'case,component.team,build.productVersion,build.routine,run,user',
			transformData: (caseResult) => ({
				...caseResult,
				build: caseResult?.r_buildToCaseResult_c_build
					? {
							...caseResult?.r_buildToCaseResult_c_build,
							productVersion:
								caseResult.r_buildToCaseResult_c_build
									?.r_productVersionToBuilds_c_productVersion,
							routine:
								caseResult.r_buildToCaseResult_c_build
									?.r_routineToBuilds_c_routine,
					  }
					: undefined,
				case: caseResult?.r_caseToCaseResult_c_case
					? {
							...caseResult?.r_caseToCaseResult_c_case,
							caseType:
								caseResult?.r_caseToCaseResult_c_case
									?.r_caseTypeToCases_c_caseType,
							component:
								caseResult?.r_caseToCaseResult_c_case
									?.r_componentToCases_c_component,
					  }
					: undefined,
				component: caseResult?.r_componentToCaseResult_c_component
					? {
							...caseResult.r_componentToCaseResult_c_component,
							team:
								caseResult.r_componentToCaseResult_c_component
									.r_teamToComponents_c_team,
					  }
					: undefined,
				run: caseResult?.r_runToCaseResult_c_run
					? {
							...caseResult?.r_runToCaseResult_c_run,
							build: caseResult?.r_runToCaseResult_c_run?.build,
					  }
					: undefined,
				user: caseResult?.r_userToCaseResults_user
					? {
							...caseResult?.r_userToCaseResults_user,
							id: caseResult?.r_userToCaseResults_user?.uuid,
					  }
					: undefined,
			}),
			uri: 'caseresults',
		});
	}

	public assignTo(caseResult: TestrayCaseResult, userId: number) {
		return this.update(caseResult.id, {
			dueStatus: caseResult.dueStatus.key,
			startDate: caseResult.startDate,
			userId,
		});
	}

	public assignToMe(caseResult: TestrayCaseResult) {
		return this.update(caseResult.id, {
			dueStatus: CaseResultStatuses.IN_PROGRESS,
			startDate: caseResult.startDate,
			userId: Number(Liferay.ThemeDisplay.getUserId()),
		});
	}

	public removeAssign(caseResult: TestrayCaseResult) {
		return this.update(caseResult.id, {
			startDate: null,
			userId: this.UNASSIGNED_USER_ID,
		});
	}
}

const nestedFieldsParam =
	'nestedFields=case.caseType,component,build.productVersion,build.routine,run,user&nestedFieldsDepth=3';

const caseResultsResource = `/caseresults?${nestedFieldsParam}`;

export const testrayCaseResultImpl = new TestrayCaseResultRest();

export {caseResultsResource};
