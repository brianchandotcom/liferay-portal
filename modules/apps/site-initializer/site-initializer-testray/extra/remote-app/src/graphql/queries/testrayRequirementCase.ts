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

import {gql} from '@apollo/client';

import {TestrayCase} from './testrayCase';
import {TestrayRequirement} from './testrayRequirement';

export type TestrayRequirementCase = {
	case: TestrayCase;
	id: number;
	requirement: TestrayRequirement;
};

export const getRequirementCases = gql`
	query getRequirementCases(
		$filter: String = ""
		$page: Int = 1
		$pageSize: Int = 20
	) {
		requirementscaseses(filter: $filter, page: $page, pageSize: $pageSize)
			@rest(
				type: "C_RequirementCase"
				path: "requirementscaseses?filter={args.filter}&page={args.page}&pageSize={args.pageSize}&nestedFields=case.component,requirement.component.team&nestedFieldsDepth=3"
			) {
			items {
				case: r_caseToRequirementsCases_c_case {
					caseNumber
					component: r_componentToCases_c_component {
						id
						name
					}
					description
					descriptionType
					estimatedDuration
					id
					name
					priority
					steps
					stepsType
				}
				id
				requirement: r_requiremenToRequirementsCases_c_requirement {
					components
					description
					descriptionType
					id
					key
					linkTitle
					linkURL
					summary
					component: r_componentToRequirements_c_component {
						id
						name
						team: r_teamToComponents_c_team {
							id
							name
						}
					}
				}
			}
			lastPage
			page
			pageSize
			totalCount
		}
	}
`;
