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

export type TestrayFactorOptions = {
	c_testrayFactorOptionId: any;
	dateCreated: string;
	dateModified: string;
	externalReferenceCode: string;
	name: string;
	status: string;
	testrayFactorCategoryId: number;
	testrayFactorOptionId: number;
};

export type TestrayFactoryOptionsQuery = {
	c: {
		testrayFactorOptions: TestrayFactorOptions;
	};
};

export type TestrayFactoryoOtionQuery = {
	c: {
		getTestrayFactoroption: TestrayFactorOptions;
	};
};

export const getTestrayFactorOptions = gql`
	query getTestrayFactorOptions {
		c {
			testrayFactorOptions {
				lastPage
				page
				pageSize
				totalCount
				items {
					c_testrayFactorOptionId
					dateCreated
					dateModified
					externalReferenceCode
					name
					status
					testrayFactorCategoryId
					testrayFactorOptionId
				}
			}
		}
	}
`;

export const getTestrayFactorOption = gql`
	query getTestrayFactoroption($testrayFactorOptionId: Long) {
		c {
			testrayFactorOption(testrayFactorOption: $testrayFactorOptionId) {
				c_testrayFactorOptionId
				dateCreated
				dateModified
				externalReferenceCode
				name
				status
				testrayFactorCategoryId
				testrayFactorOptionId
			}
		}
	}
`;
