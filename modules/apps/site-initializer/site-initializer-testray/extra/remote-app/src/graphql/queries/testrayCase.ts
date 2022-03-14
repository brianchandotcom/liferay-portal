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
	testrayCaseType?: TestrayCaseType;
	testrayComponent?: TestrayComponent;
};

export const getTestrayCases = gql`
	query getTestrayCases(
		$filter: String
		$page: Int = 1
		$pageSize: Int = 20
	) {
		testrayCases(filter: $filter, page: $page, pageSize: $pageSize)
			@rest(
				type: "C_TestrayCase"
				path: "testraycases?page={args.page}&pageSize={args.pageSize}&nestedFields=testrayComponent.testrayTeam,testrayCaseType"
			) {
			items {
				caseNumber
				dateCreated
				dateModified
				description
				descriptionType
				estimatedDuration
				name
				originationKey
				priority
				steps
				stepsType
				id
				testrayCaseResult
				testrayCaseType: r_caseCaseType_c_testrayCaseType {
					name
				}
				testrayComponent: r_casesComponents_c_testrayComponent {
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

export const getTestrayCase = gql`
	query gettestrayCase($testrayCaseId: Long!) {
		testrayCase(testrayCaseId: $testrayCaseId)
			@rest(
				type: "C_TestrayCase"
				path: "testraycases/{args.testrayCaseId}?nestedFields=testrayComponent.testrayTeam,testrayCaseType"
			) {
			caseNumber
			dateCreated
			dateModified
			description
			descriptionType
			estimatedDuration
			name
			originationKey
			priority
			steps
			stepsType
			id
			testrayCaseResult
			testrayCaseType: r_caseCaseType_c_testrayCaseType {
				name
			}
			testrayComponent: r_casesComponents_c_testrayComponent {
				name
				testrayTeam: r_componentTeam_c_testrayTeam {
					name
				}
			}
		}
	}
`;
