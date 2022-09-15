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
import {TEST_STATUS} from '../../util/constants';
import {Liferay} from '../liferay';
import Rest from './Rest';
import {TestrayCaseResult} from './types';

type CaseResultForm = typeof yupSchema.caseResult.__outputType;

class TestrayCaseResultRest extends Rest<CaseResultForm, TestrayCaseResult> {
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
				'/nestedFields=case,component.team,build.productVersion,build.routine,run,user&nestedFieldsDepth=3',
			transformData: (caseResult) =>
				({
					...caseResult,
					assignedUserId: caseResult?.assignedUserId,
					attachments: caseResult?.attachments,
					build: caseResult?.r_buildToCaseResult_c_build
						? {
								...caseResult?.r_buildToCaseResult_c_build,
								gitHash:
									caseResult.r_buildToCaseResult_c_build
										.gitHash,
								id: caseResult.r_buildToCaseResult_c_build.id,
								productVersion:
									caseResult.r_buildToCaseResult_c_build
										?.r_productVersionToBuilds_c_productVersion,
								routine:
									caseResult.r_buildToCaseResult_c_build
										?.r_routineToBuilds_c_routine,
						  }
						: null,
					case: caseResult?.r_caseToCaseResult_c_case
						? {
								...caseResult?.r_caseToCaseResult_c_case,
								caseNumber:
									caseResult?.r_caseToCaseResult_c_case
										?.caseNumber,
								caseType: caseResult?.r_caseToCaseResult_c_case
									?.caseType
									? {
											name:
												caseResult
													.r_caseToCaseResult_c_case
													.caseType?.name,
									  }
									: null,
								component:
									caseResult?.r_caseToCaseResult_c_case
										?.r_componentToCases_c_component,
								id: caseResult?.r_caseToCaseResult_c_case?.id,
								name:
									caseResult?.r_caseToCaseResult_c_case.name,
								priority:
									caseResult.r_caseToCaseResult_c_case
										.priority,
						  }
						: null,
					commentMBMessageId: caseResult?.commentMBMessageId,
					component: caseResult?.r_componentToCaseResult_c_component
						? {
								...caseResult.r_componentToCaseResult_c_component,
								team:
									caseResult
										.r_componentToCaseResult_c_component
										.r_teamToComponents_c_team,
						  }
						: null,
					dateCreated: caseResult?.dateCreated,
					dateModified: caseResult?.dateModified,
					dueStatus: caseResult?.dueStatus,
					errors: caseResult?.errors,
					id: caseResult?.id,
					run: caseResult?.r_runToCaseResult_c_run
						? {
								...caseResult?.r_runToCaseResult_c_run,
								build:
									caseResult?.r_runToCaseResult_c_run?.build,
						  }
						: null,
					startDate: caseResult?.startDate,
					user: caseResult?.r_userToCaseResults_user
						? {
								...caseResult?.r_userToCaseResults_user,
								additionalName:
									caseResult?.r_userToCaseResults_user
										?.additionalName,
								emailAddress:
									caseResult?.r_userToCaseResults_user
										?.emailAddress,
								givenName:
									caseResult?.r_userToCaseResults_user
										?.givenName,
								id: caseResult?.r_userToCaseResults_user?.uuid,
						  }
						: null,
					warnings: caseResult?.warnings,
				} as any),
			uri: 'caseresults',
		});
	}

	public assignTo(caseResult: TestrayCaseResult, userId: number) {
		const data = {
			dueStatus: caseResult.dueStatus,
			r_userToCaseResults_userId: userId,
			startDate: caseResult.startDate,
		};

		return this.fetcher.put(`/caseresults/${caseResult.id}`, data);
	}

	public assignToMe(caseResult: TestrayCaseResult) {
		const data = {
			dueStatus: TEST_STATUS['In Progress'],
			r_userToCaseResults_userId: Liferay.ThemeDisplay.getUserId(),
			startDate: caseResult.startDate,
		};

		return this.fetcher.put(`/caseresults/${caseResult.id}`, data);
	}

	public removeAssign(caseResult: TestrayCaseResult) {
		const data = {
			dueStatus: TEST_STATUS.Untested,
			r_userToCaseResults_userId: 0,
			startDate: null,
		};

		return this.fetcher.put(`/caseresults/${caseResult.id}`, data);
	}
}

const nestedFieldsParam =
	'nestedFields=case.caseType,component,build.productVersion,build.routine,run,user&nestedFieldsDepth=3';

const caseResultsResource = `/caseresults?${nestedFieldsParam}`;

export const testrayCaseResultRest = new TestrayCaseResultRest();

export {caseResultsResource};
