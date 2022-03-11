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
	components: string;
	description: string;
	descriptionType: keyof typeof DescriptionType;
	id: number;
	key: string;
	linkTitle: string;
	linkURL: string;
	summary: string;
	testrayComponent?: TestrayComponent;
};

export const getTestrayRequirements = gql`
	query getTestrayRequirements(
		$filter: String
		$page: Int = 1
		$pageSize: Int = 20
	) {
		testrayRequirements(filter: $filter, page: $page, pageSize: $pageSize)
			@rest(
				type: "C_TestrayRequirement"
				path: "testrayrequirements?page={args.page}&pageSize={args.pageSize}&nestedFields=testrayComponent,testrayTeam"
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
				testrayComponent: r_requirementComponent_c_testrayComponent {
					name
					testrayTeam: r_componentTeam_c_testrayTeam {
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

export const getTestrayRequirement = gql`
	query getTestrayRequirement($testrayRequirementId: Long!) {
		testrayRequirement(testrayRequirementId: $testrayRequirementId)
			@rest(
				type: "C_TestrayRequirement"
				path: "testrayrequirements/{args.testrayRequirementId}?nestedFields=testrayComponent,testrayTeam"
			) {
			components
			description
			descriptionType
			id
			key
			linkTitle
			linkURL
			summary
			testrayComponent: r_requirementComponent_c_testrayComponent {
				name
				testrayTeam: r_componentTeam_c_testrayTeam {
					name
				}
			}
		}
	}
`;
