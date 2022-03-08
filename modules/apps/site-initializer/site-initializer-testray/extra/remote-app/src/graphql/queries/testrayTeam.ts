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

import {testrayTeamFragment} from '../fragments';

export type TestrayTeam = {
	dateCreated: string;
	dateModified: string;
	externalReferenceCode: string;
	id: number;
	name: string;
};

export const getTestrayTeam = gql`
	${testrayTeamFragment}

	query getTestrayTeam($testrayTeamId: Long!) {
		c {
			testrayTeam(testrayTeamId: $testrayTeamId) {
				...TestrayTeamFragment
			}
		}
	}
`;

export const getTestrayTeams = gql`
	${testrayTeamFragment}

	query getTestrayTeams(
		$filter: String
		$page: Int = 1
		$pageSize: Int = 20
	) {
		c {
			testrayTeams(filter: $filter, page: $page, pageSize: $pageSize) {
				items {
					...TestrayTeamFragment
				}
				lastPage
				page
				pageSize
				totalCount
			}
		}
	}
`;
