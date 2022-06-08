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

import {DescriptionType} from '../../types';
import {TestrayComponent} from './testrayComponent';

export type TestrayRequirement = {
	component?: TestrayComponent;
	components: string;
	description: string;
	descriptionType: keyof typeof DescriptionType;
	id: number;
	key: string;
	linkTitle: string;
	linkURL: string;
	summary: string;
};

export const getRequirements = gql`
	query getRequirements(
		$filter: String = ""
		$page: Int = 1
		$pageSize: Int = 20
	) {
		requirements(filter: $filter, page: $page, pageSize: $pageSize)
			@rest(
				type: "C_Requirement"
				path: "requirements?page={args.page}&pageSize={args.pageSize}&filter={args.filter}&nestedFields=component,team&nestedFieldsDepth=2"
			) {
			items {
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
			lastPage
			page
			pageSize
			totalCount
		}
	}
`;

export const getRequirement = gql`
	query getRequirement($requirementId: Long!) {
		requirement(requirementId: $requirementId)
			@rest(
				type: "C_Requirement"
				path: "requirements/{args.requirementId}?nestedFields=component,team&nestedFieldsDepth=2"
			) {
			components
			description
			descriptionType
			id
			key
			linkTitle
			linkURL
			summary
			component: r_componentToRequirements_c_component {
				name
				team: r_teamToComponents_c_team {
					name
				}
			}
		}
	}
`;
