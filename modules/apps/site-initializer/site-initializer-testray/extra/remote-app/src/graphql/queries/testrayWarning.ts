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

import {testrayWarningFragment} from '../fragments';

export type TestrayWarning = {
	content: string;
	id: number;
};

export const getWarning = gql`
	${testrayWarningFragment}

	query getWarning($warningId: Long!) {
		c {
			Warning(warningId: $warningId) {
				...WarningFragment
			}
		}
	}
`;

export const getWarnings = gql`
	${testrayWarningFragment}

	query getWarnings($filter: String, $page: Int = 1, $pageSize: Int = 20) {
		c {
			warnings(filter: $filter, page: $page, pageSize: $pageSize) {
				items {
					...WarningFragment
				}
				lastPage
				page
				pageSize
				totalCount
			}
		}
	}
`;
