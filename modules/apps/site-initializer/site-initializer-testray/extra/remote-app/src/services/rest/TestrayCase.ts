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
import fetcher from '../fetcher';
import {APIResponse, TestrayCase} from './types';

type Case = typeof yupSchema.case.__outputType & {projectId: number};

const adapter = ({
	caseTypeId: r_caseTypeToCases_c_caseTypeId,
	componentId: r_componentToCases_c_componentId,
	description,
	descriptionType,
	estimatedDuration,
	name,
	priority,
	projectId: r_projectToCases_c_projectId,
	steps,
	stepsType,
}: Case) => ({
	description,
	descriptionType,
	estimatedDuration,
	name,
	priority,
	r_caseTypeToCases_c_caseTypeId,
	r_componentToCases_c_componentId,
	r_projectToCases_c_projectId,
	steps,
	stepsType,
});

const nestedFieldsParam =
	'nestedFields=build.project,build.routine,caseType,component.team&nestedFieldsDepth=3';

const casesResource = `/cases?${nestedFieldsParam}`;

const getCaseQuery = (caseId: number | string) =>
	`/cases/${caseId}?${nestedFieldsParam}`;

const getCaseTransformData = (testrayCase: TestrayCase): TestrayCase => ({
	...testrayCase,
	caseType: testrayCase?.r_caseTypeToCases_c_caseType,
	component: testrayCase?.r_componentToCases_c_component
		? {
				...testrayCase?.r_componentToCases_c_component,
				team:
					testrayCase?.r_componentToCases_c_component
						?.r_teamToComponents_c_team,
		  }
		: undefined,
});

const getCasesTransformData = (response: APIResponse<TestrayCase>) => ({
	...response,
	items: response?.items?.map(getCaseTransformData),
});

const createCase = (testrayCase: Case) =>
	fetcher.post('/cases', adapter(testrayCase));

const updateCase = (id: number, testrayCase: Case) =>
	fetcher.put(`/cases/${id}`, adapter(testrayCase));

export type {TestrayCase};

export {
	casesResource,
	createCase,
	getCaseQuery,
	getCasesTransformData,
	getCaseTransformData,
	updateCase,
};
