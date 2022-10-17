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
import {useEffect, useState} from 'react';
import useSearchTerm from '../../../../../../../../common/hooks/useSearchTerm';
import {useGetUserAccountsByAccountExternalReferenceCode} from '../../../../../../../../common/services/liferay/graphql/user-accounts';
import getRaysourceContactRoleName from '../utils/getRaysourceContactRoleName';
import useDeleteUserAccount from './useDeleteUserAccount';
import useSupportSeatsCount from './useSupportSeatsCount';
import useUpdateUserAccount from './useUpdateUserAccount';

const DEFAULT_FILTER = "not (userGroupRoleNames/any(s:s eq 'Provisioning'))";

const getFilter = (searchTerm) => {
	if (searchTerm) {
		return `${DEFAULT_FILTER} and (contains(name, '${searchTerm}') or userGroupRoleNames/any(s:contains(s, '${searchTerm}')))`;
	}

	return DEFAULT_FILTER;
};

export default function useUserAccountsByAccountExternalReferenceCode(
	externalReferenceCode,
	koroneikiAccountLoading
) {
	const [searching, setSearching] = useState(false);

	const {
		data,
		networkStatus,
		refetch,
	} = useGetUserAccountsByAccountExternalReferenceCode(
		externalReferenceCode,
		{
			filter: DEFAULT_FILTER,
			notifyOnNetworkStatusChange: true,
			skip: koroneikiAccountLoading,
		}
	);

	const {deleteContactRoles, loading: removing} = useDeleteUserAccount();

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
			loading:
				koroneikiAccountLoading ||
				networkStatus === NetworkStatus.loading,
			remove,
			removing,
			search,
			searching: networkStatus === NetworkStatus.setVariables,
			update,
			updating,
		},
	];
}
