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

import CaseResult from '../../pages/Project/Routines/Builds/Inner/CaseResult';
import yupSchema from '../../schema/yup';
import fetcher from '../fetcher';
import {Liferay} from '../liferay';
import {APIResponse, TestrayCaseResult} from './types';

type CaseResult = typeof yupSchema.caseResult.__outputType;

const getCaseResults =
	'/caseresults?nestedFields=case,component.team,build.productVersion,build.routine,run,user&nestedFieldsDepth=3';

const caseResultResource =
	'/caseresults?nestedFields=case,component.team,build.productVersion,build.routine,run,user&nestedFieldsDepth=3';

const nestedFieldsParam =
	'nestedFields=case.caseType,component,build.productVersion,build.routine,run,user&nestedFieldsDepth=3';

const caseResultsResource = `/caseresults?${nestedFieldsParam}`;

const getCaseResultsQuery = (caseResultId: number | string | undefined) => {
	const url = `/caseresults/${caseResultId}`;

	return url;
};

const transformDataCaseResults = (caseResult: TestrayCaseResult) => {
	return {
		...caseResult,
		assignedUserId: caseResult?.assignedUserId,
		attachments: caseResult?.attachments,
		build: caseResult?.r_buildToCaseResult_c_build
			? {
					...caseResult?.r_buildToCaseResult_c_build,
					gitHash: caseResult.r_buildToCaseResult_c_build.gitHash,
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
						caseResult?.r_caseToCaseResult_c_case?.caseNumber,
					caseType: caseResult?.r_caseToCaseResult_c_case?.caseType
						? {
								name:
									caseResult.r_caseToCaseResult_c_case
										.caseType?.name,
						  }
						: null,
					component:
						caseResult?.r_caseToCaseResult_c_case
							?.r_componentToCases_c_component,
					id: caseResult?.r_caseToCaseResult_c_case?.id,
					name: caseResult?.r_caseToCaseResult_c_case.name,
					priority: caseResult.r_caseToCaseResult_c_case.priority,
			  }
			: null,
		commentMBMessageId: caseResult?.commentMBMessageId,
		component: caseResult?.r_componentToCaseResult_c_component
			? {
					...caseResult.r_componentToCaseResult_c_component,
					team:
						caseResult.r_componentToCaseResult_c_component
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
					build: caseResult?.r_runToCaseResult_c_run?.build,
			  }
			: null,
		startDate: caseResult?.startDate,
		user: caseResult?.r_userToCaseResults_user
			? {
					...caseResult?.r_userToCaseResults_user,
					additionalName:
						caseResult?.r_userToCaseResults_user?.additionalName,
					emailAddress:
						caseResult?.r_userToCaseResults_user?.emailAddress,
					givenName: caseResult?.r_userToCaseResults_user?.givenName,
					id: caseResult?.r_userToCaseResults_user?.uuid,
			  }
			: null,
		warnings: caseResult?.warnings,
	};
};

const adapter = ({...form}: CaseResult) => ({
	...form,
	closedDate: new Date(),
	r_userToCaseResults_userId: Liferay.ThemeDisplay.getUserId(),
});

const createCaseResult = (caseResult: CaseResult) =>
	fetcher.post('/caseresults', adapter(caseResult));

const updateCaseResult = (id: number, caseResult: CaseResult) =>
	fetcher.put(`/caseresults/${id}`, adapter(caseResult));

const getCaseResultTransformData = (
	response: APIResponse<TestrayCaseResult>
) => ({
	...response,
	items: response?.items?.map(transformDataCaseResults),
});

export {
	caseResultResource,
	caseResultsResource,
	createCaseResult,
	getCaseResults,
	getCaseResultsQuery,
	getCaseResultTransformData,
	updateCaseResult,
	transformDataCaseResults,
};
