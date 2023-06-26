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

import {gql, useMutation} from '@apollo/client';

const REPLACE_ACCOUNT_ROLE_BY_USER_ACCOUNT_EMAIL_ADDRESS = gql`
	mutation replaceAccountRoleByUserAccountEmailAddress(
		$currentAccountRoleId: Long!
		$emailAddress: String!
		$externalReferenceCode: String!
		$newAccountRoleId: Long!
	) {
		deleteAccountByExternalReferenceCodeAccountRoleUserAccountByEmailAddress(
			accountRoleId: $currentAccountRoleId
			emailAddress: $emailAddress
			externalReferenceCode: $externalReferenceCode
		)
		createAccountByExternalReferenceCodeAccountRoleUserAccountByEmailAddress(
			accountRoleId: $newAccountRoleId
			emailAddress: $emailAddress
			externalReferenceCode: $externalReferenceCode
		)
	}
`;

export function useReplaceAccountRoleByUserAccountEmailAddress() {
	return useMutation(REPLACE_ACCOUNT_ROLE_BY_USER_ACCOUNT_EMAIL_ADDRESS, {
		awaitRefetchQueries: true,
		refetchQueries: ['getUserAccountsByAccountExternalReferenceCode'],
	});
}
