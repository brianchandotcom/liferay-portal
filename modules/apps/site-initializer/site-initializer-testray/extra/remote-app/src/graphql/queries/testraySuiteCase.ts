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
import {TestraySuite} from './testraySuite';

export type TestraySuiteCase = {
	case: TestrayCase;
	id: number;
	suite: TestraySuite;
};

export const getSuiteCases = gql`
	query getSuiteCases(
		$filter: String = ""
		$page: Int = 1
		$pageSize: Int = 20
	) {
		suitescaseses(filter: $filter, page: $page, pageSize: $pageSize)
			@rest(
				type: "C_SuiteCase"
				path: "suitescaseses?filter={args.filter}&page={args.page}&pageSize={args.pageSize}&nestedFields=case.component,suite&nestedFieldsDepth=2"
			) {
			items {
				case: r_caseToSuitesCases_c_case {
					caseNumber
					component: r_componentToCases_c_component {
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
				suite: r_suiteToSuitesCases_c_suite {
					caseParameters
					description
					id
					name
				}
			}
			lastPage
			page
			pageSize
			totalCount
		}
	}
`;
