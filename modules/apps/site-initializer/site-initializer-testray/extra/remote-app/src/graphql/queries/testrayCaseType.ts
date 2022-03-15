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

export type TestrayCaseType = {
	dateCreated: string;
	dateModified: string;
	externalReferenceCode: string;
	id: number;
	name: string;
	status: string;
};

const testrayCaseTypeFragment = gql`
	fragment CaseTypeFragment on C_CaseType {
		dateCreated
		dateModified
		externalReferenceCode
		id: caseTypeId
		name
	}
`;

export const getCaseType = gql`
	${testrayCaseTypeFragment}

	query getCaseType($caseTypeId: Long!) {
		c {
			caseType(caseTypeId: $caseTypeId) {
				...CaseTypeFragment
			}
		}
	}
`;

export const getCaseTypes = gql`
	${testrayCaseTypeFragment}

	query getCaseTypes($filter: String, $page: Int = 1, $pageSize: Int = 20) {
		c {
			caseTypes(filter: $filter, page: $page, pageSize: $pageSize) {
				items {
					...CaseTypeFragment
				}
				lastPage
				page
				pageSize
				totalCount
			}
		}
	}
`;
