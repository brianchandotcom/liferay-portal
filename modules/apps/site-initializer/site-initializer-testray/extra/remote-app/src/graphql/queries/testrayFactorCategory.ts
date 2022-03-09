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

export type TestrayFactorCategory = {
	dateCreated: string;
	dateModified: string;
	externalReferenceCode: string;
	name: string;
	status: string;
	testrayFactorCategoryId: number;
};

export type TestrayFactorCategoryQuery = {
	c: {
		testrayFactorCategory: TestrayFactorCategory;
	};
};

export const getTestrayFactorCategory = gql`
	query getTestrayFactorCategory($testrayFactorCategoryId: Long) {
		c {
			testrayFactorCategory(
				testrayFactorCategoryId: $testrayFactorCategoryId
			) {
				dateCreated
				dateModified
				externalReferenceCode
				name
				status
				testrayFactorCategoryId
			}
		}
	}
`;

export const getTestrayFactorCategories = gql`
	query getTestrayFactorCategories {
		c {
			testrayFactorCategories {
				lastPage
				page
				pageSize
				totalCount
				items {
					c_testrayFactorCategoryId
					dateCreated
					dateModified
					externalReferenceCode
					name
					status
					testrayFactorCategoryId
				}
			}
		}
	}
`;
