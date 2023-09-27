/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useReplaceAccountRoleByUserAccountEmailAddress} from '../../../../../../../../common/services/liferay/graphql/account-roles';
import {useUpdateRolesByContactEmailAddress} from '../../../../../../../../common/services/raysource/graphql/roles';

export default function useUpdateUserAccount() {
	const [
		replaceAccountRole,
		{loading: replaceAccountRoleLoading},
	] = useReplaceAccountRoleByUserAccountEmailAddress();

	const [
		updateContactRoles,
		{loading: updateContactRolesLoading},
	] = useUpdateRolesByContactEmailAddress();

	const loading = replaceAccountRoleLoading || updateContactRolesLoading;

	return {
		loading,
		replaceAccountRole,
		updateContactRoles,
	};
}
