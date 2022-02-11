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

const testrayCaseFragment = gql`
	fragment TestrayCaseFragment on C_TestrayCase {
		c_testrayCaseId
		caseNumber
		description
		descriptionType
		estimatedDuration
		name
		originationKey
		priority
		steps
		stepsType
		testrayCaseResult
		testrayCaseId
		testrayCaseTypeId
		testrayComponentId
		testrayProjectId
	}
`;

export const getTestrayCases = gql`
	${testrayCaseFragment}

	query getTestrayCases(
		$filter: String
		$page: Int = 1
		$pageSize: Int = 20
		$scopeKey: String
	) {
		c {
			testrayCases(
				filter: $filter
				page: $page
				pageSize: $pageSize
				scopeKey: $scopeKey
			) {
				items {
					...TestrayCaseFragment
				}
				lastPage
				page
				pageSize
				totalCount
			}
		}
	}
`;

export const getTestrayCase = gql`
	${testrayCaseFragment}

	query getTestrayCase($testrayCaseId: Long!) {
		c {
			testrayCase(testrayCaseId: $testrayCaseId) {
				...TestrayCaseFragment
			}
		}
	}
`;
