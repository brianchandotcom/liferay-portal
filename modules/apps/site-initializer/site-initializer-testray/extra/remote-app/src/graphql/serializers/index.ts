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

import {RestLink} from 'apollo-link-rest';

const serialize: RestLink.Serializer = (body, headers) => {
	headers.set('Content-Type', 'application/json');

	return {
		body: JSON.stringify(body),
		headers,
	};
};

export const bodySerializers: RestLink.Serializers = {
	case: (
		{
			caseTypeId: r_caseTypeToCases_c_caseTypeId,
			componentId: r_componentToCases_c_componentId,
			projectId: r_projectToCases_c_projectId,
			...data
		},
		headers
	) =>
		serialize(
			{
				...data,
				r_caseTypeToCases_c_caseTypeId,
				r_componentToCases_c_componentId,
				r_projectToCases_c_projectId,
			},
			headers
		),
	factorOption: (
		{
			factorCategoryId: r_factorCategoryToOptions_c_factorCategoryId,
			...data
		},
		headers
	) =>
		serialize(
			{
				...data,
				r_factorCategoryToOptions_c_factorCategoryId,
			},
			headers
		),
	requirement: (
		{
			componentId: r_componentToRequirements_c_componentId,
			projectId: r_projectToRequirements_c_projectId,
			...data
		},
		headers
	) =>
		serialize(
			{
				...data,
				r_componentToRequirements_c_componentId,
				r_projectToRequirements_c_projectId,
			},
			headers
		),
	routine: ({projectId: r_routineToProjects_c_projectId, ...data}, headers) =>
		serialize(
			{
				...data,
				r_routineToProjects_c_projectId,
			},
			headers
		),
	suite: ({projectId: r_projectToSuites_c_projectId, ...data}, headers) =>
		serialize(
			{
				...data,
				r_projectToSuites_c_projectId,
			},
			headers
		),
};
