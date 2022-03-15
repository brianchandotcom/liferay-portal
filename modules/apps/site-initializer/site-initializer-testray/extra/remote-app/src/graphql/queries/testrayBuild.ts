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

import {TestrayProductVersion} from './testrayProductVersion';
import {TestrayProject} from './testrayProject';
import {TestrayRoutine} from './testrayRoutine';

export type TestrayBuild = {
	dateCreated: string;
	description: string;
	dueStatus: number;
	gitHash: string;
	id: number;
	name: string;
	productVersion?: TestrayProductVersion;
	project?: TestrayProject;
	promoted: boolean;
	routine?: TestrayRoutine;
};

export const getBuilds = gql`
	query getBuilds($filter: String, $page: Int = 1, $pageSize: Int = 20) {
		builds(filter: $filter, page: $page, pageSize: $pageSize)
			@rest(
				type: "C_Build"
				path: "builds?page={args.page}&pageSize={args.pageSize}&nestedFields=ProductVersion"
			) {
			items {
				dateCreated
				description
				dueStatus
				gitHash
				name
				promoted
				id
				productVersion: r_buildProductVersion_c_ProductVersion {
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

export const getBuild = gql`
	query getBuild($buildId: Long!) {
		build(buildId: $buildId)
			@rest(
				type: "C_Build"
				path: "builds/{args.buildId}?nestedFields=ProductVersion"
			) {
			dateCreated
			description
			dueStatus
			gitHash
			id
			name
			productVersion: r_buildProductVersion_c_ProductVersion {
				name
			}
			promoted
		}
	}
`;
