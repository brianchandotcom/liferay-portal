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
import hasAccountSupportSeatRole from '../utils/hasAccountSupportSeatRole';

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

	const supportSeatsCount = useMemo(
		() =>
			data?.accountUserAccountsByExternalReferenceCode.items.filter(
				(item) =>
					hasAccountSupportSeatRole(
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
		supportSeatsCount,
		{data, loading: koroneikiAccountLoading || loading},
	];
}
