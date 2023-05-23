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

import {NetworkStatus} from '@apollo/client';
import {useEffect, useMemo, useState} from 'react';
import useSearchTerm from '../../../../../../../../common/hooks/useSearchTerm';
import {useGetUserAccountsByAccountExternalReferenceCode} from '../../../../../../../../common/services/liferay/graphql/user-accounts';
import getRaysourceContactRoleName from '../utils/getRaysourceContactRoleName';
import useDeleteUserAccount from './useDeleteUserAccount';
import useSupportSeatsCount from './useSupportSeatsCount';
import useUpdateUserAccount from './useUpdateUserAccount';

const getFilter = (searchTerm) => {
	if (searchTerm) {
		return `(contains(name, '${searchTerm}') or contains(emailAddress, '${searchTerm}') or userGroupRoleNames/any(s:contains(s, '${searchTerm}')))`;
	}

	return '';
};

export default function useUserAccountsByAccountExternalReferenceCode(
	externalReferenceCode,
	koroneikiAccountLoading
) {
	const [searching, setSearching] = useState(false);

	const {
		data: userAccountData,
		networkStatus,
		refetch,
	} = useGetUserAccountsByAccountExternalReferenceCode(
		externalReferenceCode,
		{
			notifyOnNetworkStatusChange: true,
			skip: koroneikiAccountLoading,
		}
	);

	const data = useMemo(() => {
		const items = (
			userAccountData?.accountUserAccountsByExternalReferenceCode
				?.items ?? []
		).filter((account) => {
			const accountBriefByExternalReferenceCode = account.accountBriefs.find(
				(accountBrief) =>
					accountBrief.externalReferenceCode === externalReferenceCode
			);

			if (
				accountBriefByExternalReferenceCode &&
				accountBriefByExternalReferenceCode.roleBriefs.some(
					(roleBrief) => roleBrief.name === 'Provisioning'
				)
			) {
				return false;
			}

			return true;
		});

		return {
			...userAccountData,
			accountUserAccountsByExternalReferenceCode: {
				...userAccountData?.accountUserAccountsByExternalReferenceCode,
				items,
				totalCount: items.length,
			},
		};
	}, [userAccountData, externalReferenceCode]);

	const {
		deleteContactRoles,
		deleteUserAccount,
		loading: removing,
	} = useDeleteUserAccount();

	const {
		loading: updating,
		replaceAccountRole,
		updateContactRoles,
	} = useUpdateUserAccount();

	useEffect(() => {
		if (networkStatus === NetworkStatus.refetch) {
			setSearching(false);
		}
	}, [networkStatus]);

	const supportSeatsCount = useSupportSeatsCount(
		data?.accountUserAccountsByExternalReferenceCode,
		searching
	);

	const search = useSearchTerm((searchTerm) => {
		setSearching(true);

		refetch({
			filter: getFilter(searchTerm),
		});
	});

	const remove = (userAccount) => {
		const contactRoleNames = userAccount.selectedAccountSummary.roleBriefs?.map(
			(roleBrief) => getRaysourceContactRoleName(roleBrief.name)
		);

		deleteContactRoles({
			onCompleted: (_, {variables}) =>
				deleteUserAccount({
					variables: {
						emailAddress: variables.contactEmail,
						externalReferenceCode: variables.externalReferenceCode,
					},
				}),
			variables: {
				contactEmail: userAccount.emailAddress,
				contactRoleNames: contactRoleNames.join('&'),
				externalReferenceCode,
			},
		});
	};

	const update = (userAccount, currentAccountRoles, newAccountRoleItem) => {
		const newContactRoleName = getRaysourceContactRoleName(
			newAccountRoleItem.label
		);

		const currentContactRolesName = currentAccountRoles.map((roleBrief) =>
			getRaysourceContactRoleName(roleBrief.name)
		);

		updateContactRoles({
			onCompleted: () =>
				deleteContactRoles({
					onCompleted: () =>
						replaceAccountRole({
							variables: {
								currentAccountRoleId: currentAccountRoles[0].id,
								emailAddress: userAccount.emailAddress,
								externalReferenceCode,
								newAccountRoleId: newAccountRoleItem.value,
							},
						}),
					variables: {
						contactEmail: userAccount.emailAddress,
						contactRoleNames: currentContactRolesName.join('&'),
						externalReferenceCode,
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
			loading:
				koroneikiAccountLoading ||
				networkStatus === NetworkStatus.loading,
			remove,
			search,
			searching: networkStatus === NetworkStatus.setVariables,
			update,
			updating: updating || removing,
		},
	];
}
