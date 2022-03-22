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

import {testrayProjectFragment} from '../fragments';

export type TestrayProject = {
	creator: {
		name: string;
	};
	dateCreated: string;
	description: string;
	id: number;
	name: string;
};

export const getProjects = gql`
	${testrayProjectFragment}

	query getProjects($filter: String, $page: Int = 1, $pageSize: Int = 20) {
		c {
			projects(filter: $filter, page: $page, pageSize: $pageSize) {
				items {
					...ProjectFragment
				}
				lastPage
				page
				pageSize
				totalCount
			}
		}
	}
`;

export const getProject = gql`
	${testrayProjectFragment}

	query getProjects($projectId: Long!) {
		c {
			project(projectId: $projectId) {
				...ProjectFragment
			}
		}
	}
`;
