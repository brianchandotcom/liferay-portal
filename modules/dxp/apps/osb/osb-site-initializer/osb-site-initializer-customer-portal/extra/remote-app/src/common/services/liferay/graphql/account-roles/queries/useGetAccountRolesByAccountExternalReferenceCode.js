/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {gql, useQuery} from '@apollo/client';

const GET_ACCOUNT_ROLES_BY_ACCOUNT_EXTERNAL_REFERENCE_CODE = gql`
	query getAccountRolesByAccountExternalReferenceCode(
		$externalReferenceCode: String!
		$filter: String
		$pageSize: Int
	) {
		accountAccountRolesByExternalReferenceCode(
			externalReferenceCode: $externalReferenceCode
			filter: $filter
			pageSize: $pageSize
		) {
			items {
				id
				name
			}
		}
	}
`;

export function useGetAccountRolesByAccountExternalReferenceCode(
	externalReferenceCode,
	options = {
		filter: '',
		notifyOnNetworkStatusChange: false,
		skip: false,
	}
) {
	return useQuery(GET_ACCOUNT_ROLES_BY_ACCOUNT_EXTERNAL_REFERENCE_CODE, {
		fetchPolicy: 'cache-and-network',
		nextFetchPolicy: 'cache-first',
		notifyOnNetworkStatusChange: options.notifyOnNetworkStatusChange,
		skip: options.skip,
		variables: {
			externalReferenceCode,
			filter: options.filter || '',
			pageSize: 9999,
		},
	});
}
