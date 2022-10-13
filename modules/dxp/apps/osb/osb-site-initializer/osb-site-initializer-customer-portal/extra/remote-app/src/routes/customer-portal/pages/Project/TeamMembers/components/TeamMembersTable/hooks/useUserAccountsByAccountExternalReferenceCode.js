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

import {useMemo} from 'react';
import {useGetUserAccountsByAccountExternalReferenceCode} from '../../../../../../../../common/services/liferay/graphql/user-accounts';
import getRaysourceContactRoleName from '../utils/getRaysourceContactRoleName';
import useDeleteUserAccount from './useDeleteUserAccount';
import useUpdateUserAccount from './useUpdateUserAccount';

export default function useUserAccountsByAccountExternalReferenceCode(
	externalReferenceCode,
	koroneikiAccountLoading
) {
	const {data, loading} = useGetUserAccountsByAccountExternalReferenceCode(
		externalReferenceCode,
		{
			filter: "not (userGroupRoleNames/any(s:s eq 'Provisioning'))",
			skip: koroneikiAccountLoading,
		}
	);

	const {deleteContactRoles, loading: removing} = useDeleteUserAccount();

	const {
		loading: updating,
		replaceAccountRole,
		updateContactRoles,
	} = useUpdateUserAccount();

	const supportSeatsCount = useMemo(
		() =>
			data?.accountUserAccountsByExternalReferenceCode.items.filter(
				(item) => item.selectedAccountSummary.hasSupportSeatRole
			).length,
		[data?.accountUserAccountsByExternalReferenceCode.items]
	);

	const remove = (userAccount) => {
		const contactRoleNames = userAccount.selectedAccountSummary.roleBriefs?.map(
			(roleBrief) => getRaysourceContactRoleName(roleBrief.name)
		);

		deleteContactRoles({
			variables: {
				contactEmail: userAccount.emailAddress,
				contactRoleNames: contactRoleNames.join('&'),
				externalReferenceCode,
			},
		});
	};

	const update = (userAccount, currentAccountRole, newAccountRoleItem) => {
		const newContactRoleName = getRaysourceContactRoleName(
			newAccountRoleItem.label
		);

		updateContactRoles({
			onCompleted: () =>
				replaceAccountRole({
					variables: {
						currentAccountRoleId: currentAccountRole.id,
						emailAddress: userAccount.emailAddress,
						externalReferenceCode,
						newAccountRoleId: newAccountRoleItem.value,
					},
				}),
			variables: {
				contactEmail: userAccount.emailAddress,
				contactRoleName: newContactRoleName,
				externalReferenceCode,
			},
		});
	};

	return [
		supportSeatsCount,
		{
			data,
			loading: koroneikiAccountLoading || loading,
			remove,
			removing,
			update,
			updating,
		},
	];
}
