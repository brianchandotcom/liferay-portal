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

const DELETE_USER_ACCOUNT_BY_EMAIL_ADDRESS = gql`
	mutation deleteUserAccountByEmailAddress(
		$emailAddress: String!
		$externalReferenceCode: String!
	) {
		deleteAccountUserAccountByExternalReferenceCodeByEmailAddress(
			emailAddress: $emailAddress
			externalReferenceCode: $externalReferenceCode
		)
	}
`;

export function useDeleteUserAccountByEmailAddress() {
	return useMutation(DELETE_USER_ACCOUNT_BY_EMAIL_ADDRESS, {
		awaitRefetchQueries: true,
		refetchQueries: ['getUserAccountsByAccountExternalReferenceCode'],
	});
}
