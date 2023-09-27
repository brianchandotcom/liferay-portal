/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useDeleteUserAccountByEmailAddress} from '../../../../../../../../common/services/liferay/graphql/user-accounts';
import {useDeleteRolesByContactEmailAddress} from '../../../../../../../../common/services/raysource/graphql/roles';

export default function useDeleteUserAccount() {
	const [
		deleteUserAccount,
		{loading: deleteUserAccountLoading},
	] = useDeleteUserAccountByEmailAddress();

	const [
		deleteContactRoles,
		{loading: deleteContactRolesLoading},
	] = useDeleteRolesByContactEmailAddress();

	const loading = deleteUserAccountLoading || deleteContactRolesLoading;

	return {
		deleteContactRoles,
		deleteUserAccount,
		loading,
	};
}
