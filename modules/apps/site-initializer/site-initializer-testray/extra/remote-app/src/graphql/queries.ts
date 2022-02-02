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

export const getTestrayProjects = gql`
	query getTestrayProjects(
		$filter: String
		$page: Int = 1
		$pageSize: Int = 20
		$scopeKey: String
	) {
		c {
			testrayProjects(
				filter: $filter
				page: $page
				pageSize: $pageSize
				scopeKey: $scopeKey
			) {
				items {
					description
					name
					testrayProjectId
				}
				lastPage
				page
				pageSize
				totalCount
			}
		}
	}
`;

export const getTestrayProject = gql`
	query getTestrayProjects($testrayProjectId: Long!) {
		c {
			testrayProject(testrayProjectId: $testrayProjectId) {
				description
				name
			}
		}
	}
`;

export const getTestrayBuilds = gql`
	query getTestrayBuilds(
		$filter: String
		$page: Int = 1
		$pageSize: Int = 20
		$scopeKey: String
	) {
		c {
			testrayBuilds(
				filter: $filter
				page: $page
				pageSize: $pageSize
				scopeKey: $scopeKey
			) {
				items {
					description
					name
					gitHash
					dueStatus
					promoted
				}
				lastPage
				page
				pageSize
				totalCount
			}
		}
	}
`;

export const getTestrayRoutines = gql`
	query getTestrayRoutines(
		$filter: String
		$page: Int = 1
		$pageSize: Int = 20
		$scopeKey: String
	) {
		c {
			testrayRoutines(
				filter: $filter
				page: $page
				pageSize: $pageSize
				scopeKey: $scopeKey
			) {
				items {
					name
					testrayRoutineId
				}
				lastPage
				page
				pageSize
				totalCount
			}
		}
	}
`;

export const getTestrayCases = gql`
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
					testrayCaseId
					testrayCaseTypeId
					testrayComponentId
					testrayProjectId
				}
				lastPage
				page
				pageSize
				totalCount
			}
		}
	}
`;

export const getTestraySuites = gql`
	query getTestraySuites(
		$filter: String
		$page: Int = 1
		$pageSize: Int = 20
		$scopeKey: String
	) {
		c {
			testraySuites(
				filter: $filter
				page: $page
				pageSize: $pageSize
				scopeKey: $scopeKey
			) {
				items {
					name
					description
					type
				}
				lastPage
				page
				pageSize
				totalCount
			}
		}
	}
`;

export const getTestrayRequirements = gql`
	query getTestrayRequirements(
		$filter: String
		$page: Int = 1
		$pageSize: Int = 20
		$scopeKey: String
	) {
		c {
			testrayRequirements(
				filter: $filter
				page: $page
				pageSize: $pageSize
				scopeKey: $scopeKey
			) {
				items {
					description
					components
					summary
					key
					linkTitle
					linkURL
					descriptionType
				}
				lastPage
				page
				pageSize
				totalCount
			}
		}
	}
`;
