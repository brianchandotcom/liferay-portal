/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useGetAccountRolesByAccountExternalReferenceCode} from '../../../../../../../../common/services/liferay/graphql/account-roles';

export default function useAccountRolesByAccountExternalReferenceCode(
	koroneikiAccount,
	loading,
	skip
) {
	const getFilter = () => {
		const filters = ["name ne 'Provisioning'"];

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
			skip: loading || skip,
		}
	);
}
