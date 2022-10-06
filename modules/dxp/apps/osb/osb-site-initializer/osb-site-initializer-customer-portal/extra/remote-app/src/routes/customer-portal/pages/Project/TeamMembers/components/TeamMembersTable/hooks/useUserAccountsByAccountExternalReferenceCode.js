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
import {useGetUserAccountsByAccountExternalReferenceCode} from '../../../../../../../../common/services/liferay/graphql/user-accounts/queries/useGetUserAccountsByAccountExternalReferenceCode';
import hasAccountAdministratorRoleByExternalReferenceCode from '../utils/hasAccountAdministratorRoleByExternalReferenceCode';

export default function useUserAccountsByAccountExternalReferenceCode(
	externalReferenceCode,
	koroneikiAccountLoading
) {
	const {data, loading} = useGetUserAccountsByAccountExternalReferenceCode(
		externalReferenceCode,
		{
			skip: koroneikiAccountLoading,
		}
	);

	const accountAdministratorsCount = useMemo(
		() =>
			data?.accountUserAccountsByExternalReferenceCode.items.filter(
				(item) =>
					hasAccountAdministratorRoleByExternalReferenceCode(
						item.accountBriefs,
						externalReferenceCode
					)
			).length,
		[
			data?.accountUserAccountsByExternalReferenceCode.items,
			externalReferenceCode,
		]
	);

	return [
		accountAdministratorsCount,
		{data, loading: koroneikiAccountLoading || loading},
	];
}
