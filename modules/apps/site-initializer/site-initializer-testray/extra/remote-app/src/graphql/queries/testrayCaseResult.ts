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

export const getTestrayCaseResults = gql`
	query getTestrayCaseResults(
		$filter: String
		$page: Int = 1
		$pageSize: Int = 20
	) {
		testrayCaseResults(filter: $filter, page: $page, pageSize: $pageSize)
			@rest(
				type: "C_TestrayCaseResult"
				path: "testraycaseresults?page={args.page}&pageSize={args.pageSize}&nestedFields=testrayComponent.testrayTeam,testrayCaseResultType"
			) {
			items {
				assignedUserId
				attachments
				closedDate
				commentMBMessageId
				dateCreated
				dateModified
				dueStatus
				errors
				startDate
			}
			lastPage
			page
			pageSize
			totalCount
		}
	}
`;
