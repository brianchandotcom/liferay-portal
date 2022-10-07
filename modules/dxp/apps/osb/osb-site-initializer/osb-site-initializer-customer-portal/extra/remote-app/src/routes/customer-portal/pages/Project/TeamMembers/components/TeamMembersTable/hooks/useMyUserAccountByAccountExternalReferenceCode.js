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
import hasAccountAdministratorRole from '../utils/hasAccountAdministratorRole';

export default function useMyUserAccountByAccountExternalReferenceCode(
	externalReferenceCode,
	koroneikiAccountLoading
) {
	const {data, loading} = useGetMyUserAccount({
		skip: koroneikiAccountLoading,
	});

	const hasAccountAdministrator = useMemo(
		() =>
			hasAccountAdministratorRole(
				data?.myUserAccount.accountBriefs,
				externalReferenceCode
			),
		[data?.myUserAccount, externalReferenceCode]
	);

	return [
		hasAccountAdministrator,
		{data, loading: koroneikiAccountLoading || loading},
	];
}
