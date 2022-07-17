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

import {APIResponse, TestrayRequirement} from '../../graphql/queries';
import yupSchema from '../../schema/yup';
import fetcher from '../fetcher';

type Requirement = typeof yupSchema.requirement.__outputType & {
	projectId: number;
};

const adapter = ({
	componentId: r_componentToRequirements_c_componentId,
	description,
	descriptionType,
	key,
	linkTitle,
	linkURL,
	projectId: r_projectToRequirements_c_projectId,
	summary,
}: Requirement) => ({
	description,
	descriptionType,
	key,
	linkTitle,
	linkURL,
	r_componentToRequirements_c_componentId,
	r_projectToRequirements_c_projectId,
	summary,
});

const nestedFieldsParam = 'nestedFields=component,team&nestedFieldsDepth=2';

const requirementsResource = `/requirements?${nestedFieldsParam}`;

const getRequirementQuery = (requirementId: number | string) =>
	`/requirements/${requirementId}?${nestedFieldsParam}`;

const getRequirementTransformData = (
	testrayRequirement: TestrayRequirement
): TestrayRequirement => ({
	...testrayRequirement,
	component: testrayRequirement.r_componentToRequirements_c_component
		? {
				...testrayRequirement.r_componentToRequirements_c_component,
				team:
					testrayRequirement.r_componentToRequirements_c_component
						.r_teamToComponents_c_team,
		  }
		: undefined,
});

const getRequirementsTransformData = (
	response: APIResponse<TestrayRequirement>
) => ({
	...response,
	items: response?.items?.map(getRequirementTransformData),
});

const createRequirement = (Requirement: Requirement) =>
	fetcher.post('/requirements', adapter(Requirement));

const updateRequirement = (id: number, requirement: Partial<Requirement>) =>
	fetcher.patch(`/requirements/${id}`, adapter(requirement as Requirement));

export {
	requirementsResource,
	createRequirement,
	getRequirementQuery,
	getRequirementTransformData,
	getRequirementsTransformData,
	updateRequirement,
};
