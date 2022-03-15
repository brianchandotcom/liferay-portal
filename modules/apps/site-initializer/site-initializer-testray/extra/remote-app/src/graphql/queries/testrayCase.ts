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

import {TestrayCaseType} from './testrayCaseType';
import {TestrayComponent} from './testrayComponent';

export type TestrayCase = {
	caseNumber: number;
	caseType?: TestrayCaseType;
	component?: TestrayComponent;
	dateCreated: string;
	dateModified: string;
	description: string;
	descriptionType: string;
	estimatedDuration: number;
	id: number;
	name: string;
	originationKey: string;
	priority: number;
	steps: string;
	stepsType: string;
	testrayCaseResult: number;
};

export const getCases = gql`
	query getCases($filter: String, $page: Int = 1, $pageSize: Int = 20) {
		cases(filter: $filter, page: $page, pageSize: $pageSize)
			@rest(
				type: "C_Case"
				path: "cases?page={args.page}&pageSize={args.pageSize}&nestedFields=Component.Team,CaseType"
			) {
			items {
				caseNumber
				caseResult
				caseType: r_caseCaseType_c_CaseType {
					name
				}
				component: r_casesComponents_c_Component {
					name
					team: r_componentTeam_c_Team {
						name
					}
				}
				dateCreated
				dateModified
				description
				descriptionType
				estimatedDuration
				id
				name
				originationKey
				priority
				steps
				stepsType
			}
			lastPage
			page
			pageSize
			totalCount
		}
	}
`;

export const getCase = gql`
	query getCase($caseId: Long!) {
		case(caseId: $caseId)
			@rest(
				type: "C_Case"
				path: "cases/{args.caseId}?nestedFields=Component.Team,CaseType"
			) {
			caseNumber
			caseResult
			caseType: r_caseCaseType_c_CaseType {
				name
			}
			component: r_casesComponents_c_Component {
				name
				team: r_componentTeam_c_Team {
					name
				}
			}
			dateCreated
			dateModified
			description
			descriptionType
			estimatedDuration
			id
			name
			originationKey
			priority
			steps
			stepsType
		}
	}
`;
