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

import client from '../../../../../../apolloClient';
import {deleteAccountUserRoles} from '../../../../../../common/services/liferay/graphql/queries';

export async function deleteAllPreviousUserRoles(
	accountKey,
	userAccount,
	accountRoles
) {
	return Promise.all(
		userAccount.roles.map(async (roleName) => {
			const roleType = accountRoles?.find(
				(role) => role?.key === roleName
			);

			if (roleType) {
				await client.mutate({
					mutation: deleteAccountUserRoles,
					variables: {
						accountKey,
						accountRoleId: roleType?.id,
						emailAddress: userAccount?.emailAddress,
					},
				});
			}
		})
	);
}
