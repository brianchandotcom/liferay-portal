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

import {TestrayCase} from '../../graphql/queries';
import yupSchema from '../../schema/yup';
import fetcher from '../fetcher';

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

const getCaseQuery = (caseId: number | string) =>
	`/cases/${caseId}?nestedFields=component.team,caseType&nestedFieldsDepth=2`;

const getCaseTransformData = (testrayCase: TestrayCase): TestrayCase => ({
	...testrayCase,
	caseType: testrayCase.r_caseTypeToCases_c_caseType,
	component: testrayCase.r_componentToCases_c_component,
});

const createCase = (Case: Case) => fetcher.post('/cases', adapter(Case));

const updateCase = (id: number, Case: Case) =>
	fetcher.put(`/cases/${id}`, adapter(Case));

export {createCase, getCaseQuery, getCaseTransformData, updateCase};
