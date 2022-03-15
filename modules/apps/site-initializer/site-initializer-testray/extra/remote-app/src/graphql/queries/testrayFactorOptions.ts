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
	id: number;
	name: string;
};

export const getFactorOptions = gql`
	${testrayFactorOptionsFragment}

	query getFactorOptions {
		c {
			factorOptions {
				items {
					...FactorOptionsFragment
				}
				lastPage
				page
				pageSize
				totalCount
			}
		}
	}
`;

export const getFactorOption = gql`
	${testrayFactorOptionsFragment}

	query getFactoroption($factorOptionId: Long) {
		c {
			factorOption(factorOption: $factorOptionId) {
				...FactorOptionsFragment
			}
		}
	}
`;
