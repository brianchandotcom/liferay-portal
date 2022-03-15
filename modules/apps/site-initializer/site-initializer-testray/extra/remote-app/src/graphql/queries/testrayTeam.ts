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

export const getTeam = gql`
	${testrayTeamFragment}

	query getTeam($teamId: Long!) {
		c {
			team(teamId: $teamId) {
				...TeamFragment
			}
		}
	}
`;

export const getTeams = gql`
	${testrayTeamFragment}

	query getTeams($filter: String, $page: Int = 1, $pageSize: Int = 20) {
		c {
			teams(filter: $filter, page: $page, pageSize: $pageSize) {
				items {
					...TeamFragment
				}
				lastPage
				page
				pageSize
				totalCount
			}
		}
	}
`;
