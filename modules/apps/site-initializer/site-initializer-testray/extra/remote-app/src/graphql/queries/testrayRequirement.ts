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

export const getTestrayRequirements = gql`
	query getTestrayRequirements(
		$filter: String
		$page: Int = 1
		$pageSize: Int = 20
	) {
		c {
			testrayRequirements(
				filter: $filter
				page: $page
				pageSize: $pageSize
			) {
				items {
					components
					description
					descriptionType
					key
					linkTitle
					linkURL
					summary
					testrayRequirementId
				}
				lastPage
				page
				pageSize
				totalCount
			}
		}
	}
`;

export const getTestrayRequirement = gql`
	query getTestrayRequirement($testrayRequirementId: Long!) {
		c {
			testrayRequirement(testrayRequirementId: $testrayRequirementId) {
				description
				components
				summary
				key
				linkTitle
				linkURL
				descriptionType
			}
		}
	}
`;
