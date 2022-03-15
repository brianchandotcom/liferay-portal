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

export type TestrayCaseResult = {
	assignedUserId: string;
	attachments: string;
	closedDate: string;
	commentMBMessageId: string;
	dateCreated: string;
	dateModified: string;
	dueStatus: string;
	errors: string;
	startDate: string;
};

export const getCaseResults = gql`
	query getCaseResults($filter: String, $page: Int = 1, $pageSize: Int = 20) {
		caseResults(filter: $filter, page: $page, pageSize: $pageSize)
			@rest(
				type: "C_CaseResult"
				path: "caseresults?page={args.page}&pageSize={args.pageSize}&nestedFields=component,build.productVersion,build.routine,run"
			) {
			items {
				assignedUserId
				attachments
				build: r_buildToCaseResult_c_build {
					gitHash
					routine: r_routineToBuilds_c_routine {
						name
					}
					productVersion: r_productVersionToBuilds_c_productVersion {
						name
					}
				}
				closedDate
				commentMBMessageId
				component: r_componentToCaseResult_c_component
				dateCreated
				dateModified
				dueStatus
				errors
				startDate
				run: r_runToCaseResult_c_run {
					externalReferencePK
				}
			}
			lastPage
			page
			pageSize
			totalCount
		}
	}
`;
