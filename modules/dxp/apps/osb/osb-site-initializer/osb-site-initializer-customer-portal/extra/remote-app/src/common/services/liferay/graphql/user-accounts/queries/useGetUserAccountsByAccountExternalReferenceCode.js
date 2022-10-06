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
import {CORE_USER_ACCOUNT_FIELDS} from '../fragments';

const GET_USER_ACCOUNTS_BY_ACCOUNT_EXTERNAL_REFERENCE_CODE = gql`
	${CORE_USER_ACCOUNT_FIELDS}
	query getUserAccountsByAccountExternalReferenceCode(
		$externalReferenceCode: String!
	) {
		accountUserAccountsByExternalReferenceCode(
			externalReferenceCode: $externalReferenceCode
		)
			@rest(
				type: "UserAccountPage"
				path: "/headless-admin-user/v1.0/accounts/by-external-reference-code/{args.externalReferenceCode}/user-accounts"
			) {
			items @type(name: "UserAccount") {
				...CoreUserAccountFields
			}
			page
			pageSize
			totalCount
			lastPage
		}
	}
`;

export function useGetUserAccountsByAccountExternalReferenceCode(
	externalReferenceCode,
	options = {
		filter: '',
		notifyOnNetworkStatusChange: false,
		page: 1,
		pageSize: 20,
		skip: false,
	}
) {
	return useQuery(GET_USER_ACCOUNTS_BY_ACCOUNT_EXTERNAL_REFERENCE_CODE, {
		context: {
			type: 'liferay-rest',
		},
		fetchPolicy: 'cache-and-network',
		nextFetchPolicy: 'cache-first',
		notifyOnNetworkStatusChange: options.notifyOnNetworkStatusChange,
		skip: options.skip,
		variables: {
			externalReferenceCode,
			filter: options.filter || '',
			page: options.page || 1,
			pageSize: options.pageSize || 20,
		},
	});
}
