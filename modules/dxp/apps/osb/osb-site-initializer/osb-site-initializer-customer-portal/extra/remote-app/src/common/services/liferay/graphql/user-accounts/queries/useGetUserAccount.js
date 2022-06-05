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
import {useMemo} from 'react';
import {Liferay} from '../../..';
import useHash from '../../../../../hooks/useHash';

const GET_USER_ACCOUNT = gql`
	query getUserAccount($userAccountId: Long!, $accountKey: String) {
		userAccount(userAccountId: $userAccountId) {
			accountBriefs {
				externalReferenceCode
				id
				name
				roleBriefs {
					id
					name
				}
			}
			externalReferenceCode
			hasProvisioningRole @client
			id
			isLiferayStaff @client
			name
			organizationBriefs {
				id
				name
			}
			roleBriefs {
				id
				name
			}
			selectedAccountBrief(accountKey: $accountKey) @client {
				externalReferenceCode
				hasAccountAdministratorRole
				id
				name
			}
		}
	}
`;

const eventUserAccount = Liferay.publish(
	'customer-portal-select-user-loading',
	{
		async: true,
		fireOnce: true,
	}
);

export function useGetUserAccount(userAccountId, options = {skip: false}) {
	const hashLocation = useHash();

	const accountKey = useMemo(
		() => hashLocation.replace('#/', '').split('/').filter(Boolean)[0],
		[hashLocation]
	);

	return useQuery(GET_USER_ACCOUNT, {
		fetchPolicy: 'cache-and-network',
		nextFetchPolicy: 'cache-first',
		onCompleted: (data) =>
			eventUserAccount.fire({
				detail: data?.userAccount,
			}),
		skip: options.skip,
		variables: {
			accountKey,
			userAccountId,
		},
	});
}
