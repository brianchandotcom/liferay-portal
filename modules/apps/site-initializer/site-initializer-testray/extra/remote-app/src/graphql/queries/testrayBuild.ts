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
	promoted: boolean;
	testrayProductVersion?: TestrayProductVersion;
	testrayProject?: TestrayProject;
	testrayRoutine?: TestrayRoutine;
};

export const getTestrayBuilds = gql`
	query getTestrayBuilds(
		$filter: String
		$page: Int = 1
		$pageSize: Int = 20
	) {
		testrayBuilds(filter: $filter, page: $page, pageSize: $pageSize)
			@rest(
				type: "C_TestrayBuild"
				path: "testraybuilds?page={args.page}&pageSize={args.pageSize}&nestedFields=testrayProductVersion"
			) {
			items {
				dateCreated
				description
				dueStatus
				gitHash
				name
				promoted
				testrayBuildId: id
				testrayProductVersion: r_buildProductVersion_c_testrayProductVersion {
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

export const getTestrayBuild = gql`
	query gettestrayBuild($testrayBuildId: Long!) {
		testrayBuild(testrayBuildId: $testrayBuildId)
			@rest(
				type: "C_TestrayBuild"
				path: "testraybuilds/{args.testrayBuildId}?nestedFields=testrayProductVersion"
			) {
			dateCreated
			description
			dueStatus
			gitHash
			name
			promoted
			id
			testrayProductVersion: r_buildProductVersion_c_testrayProductVersion {
				name
			}
		}
	}
`;
