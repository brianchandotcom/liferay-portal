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

import {testrayFactorOptionFragment} from '../fragments';
import {TestrayFactorCategory} from './testrayFactorCategory';

export type TestrayFactorOptions = {
	dateCreated: string;
	dateModified: string;
	factorCategory?: TestrayFactorCategory;
	id: number;
	name: string;
};

export const getFactorOptions = gql`
	query getFactorOptions(
		$filter: String
		$page: Int = 1
		$pageSize: Int = 20
	) {
		factorOptions(filter: $filter, page: $page, pageSize: $pageSize)
			@rest(
				type: "C_FactorOption"
				path: "factoroptions?page={args.page}&pageSize={args.pageSize}&nestedFields=factorCategory"
			) {
			items {
				name
				id
				factorCategory: r_factorCategoryToOptions_c_factorCategory {
					id
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

export const getFactorOption = gql`
	${testrayFactorOptionFragment}

	query getFactorOption($factorOptionId: Long) {
		c {
			factorOption(factorOption: $factorOptionId) {
				...FactorOptionFragment
			}
		}
	}
`;
