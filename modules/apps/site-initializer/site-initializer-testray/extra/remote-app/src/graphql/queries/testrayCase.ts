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

export type TestrayCase = {
	caseNumber: number;
	description: string;
	descriptionType: string;
	estimatedDuration: number;
	name: string;
	originationKey: string;
	priority: number;
	steps: string;
	stepsType: string;
	testrayCaseId: number;
	testrayCaseResult: number;
};

const testrayCaseFragment = gql`
	fragment TestrayCaseFragment on C_TestrayCase {
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
	}
`;

export const getTestrayCases = gql`
	${testrayCaseFragment}

	query getTestrayCases(
		$filter: String
		$page: Int = 1
		$pageSize: Int = 20
	) {
		c {
			testrayCases(filter: $filter, page: $page, pageSize: $pageSize) {
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
