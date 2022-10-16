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
import {useGetMyUserAccount} from '../../../../../../../../common/services/liferay/graphql/user-accounts';
import isAccountAdministrator from '../../../../../../../../common/utils/isAccountAdministrator';
import isSupportSeatRole from '../../../../../../../../common/utils/isSupportSeatRole';

export default function useMyUserAccountByAccountExternalReferenceCode(
	koroneikiAccountLoading,
	externalReferenceCode
) {
	const {data, loading} = useGetMyUserAccount({
		skip: koroneikiAccountLoading,
	});

	const selectedAccountSummary = useMemo(
		() =>
			data?.myUserAccount.accountBriefs.find(
				(accountBrief) =>
					accountBrief.externalReferenceCode === externalReferenceCode
			),
		[data?.myUserAccount.accountBriefs, externalReferenceCode]
	);

	const hasAdministratorRole = useMemo(
		() =>
			selectedAccountSummary?.roleBriefs.some(({name}) =>
				isAccountAdministrator(name)
			),
		[selectedAccountSummary?.roleBriefs]
	);

	const hasSupportSeatRole = useMemo(
		() =>
			selectedAccountSummary?.roleBriefs.some(({name}) =>
				isSupportSeatRole(name)
			),
		[selectedAccountSummary?.roleBriefs]
	);

	return {
		data: {
			myUserAccount: {
				...data?.myUserAccount,
				selectedAccountSummary: {
					hasAdministratorRole,
					hasSupportSeatRole,
					roleBriefs: selectedAccountSummary?.roleBriefs,
				},
			},
		},
		loading: koroneikiAccountLoading || loading,
	};
}
