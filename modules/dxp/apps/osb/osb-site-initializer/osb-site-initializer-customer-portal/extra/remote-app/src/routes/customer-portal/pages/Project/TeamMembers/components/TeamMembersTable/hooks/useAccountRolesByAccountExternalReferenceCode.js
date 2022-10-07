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

import {useGetAccountRolesByAccountExternalReferenceCode} from '../../../../../../../../common/services/liferay/graphql/account-roles';

export default function useAccountRolesByAccountExternalReferenceCode(
	koroneikiAccount,
	loading
) {
	const getFilter = () => {
		const filters = [];

		if (!koroneikiAccount?.hasSLAGoldPlatinum) {
			filters.push(`name ne 'Requester'`);
		}

		if (!koroneikiAccount?.partner) {
			filters.push(`not (contains(name , 'Partner'))`);
		}

		return filters.join(' and ');
	};

	return useGetAccountRolesByAccountExternalReferenceCode(
		koroneikiAccount?.accountKey,
		{
			filter: getFilter(),
			skip: loading,
		}
	);
}
