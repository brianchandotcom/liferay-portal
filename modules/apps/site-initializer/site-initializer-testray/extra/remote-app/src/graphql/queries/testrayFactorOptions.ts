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

import {testrayFactorOptionsFragment} from '../fragments';

export type TestrayFactorOptions = {
	dateCreated: string;
	dateModified: string;
	name: string;
};

export const getTestrayFactorOptions = gql`
	${testrayFactorOptionsFragment}

	query getTestrayFactorOptions {
		c {
			testrayFactorOptions {
				items {
					...TestrayFactorOptionsFragment
				}
				lastPage
				page
				pageSize
				totalCount
			}
		}
	}
`;

export const getTestrayFactorOption = gql`
	${testrayFactorOptionsFragment}

	query getTestrayFactoroption($testrayFactorOptionId: Long) {
		c {
			testrayFactorOption(testrayFactorOption: $testrayFactorOptionId) {
				...TestrayFactorOptionsFragment
			}
		}
	}
`;
