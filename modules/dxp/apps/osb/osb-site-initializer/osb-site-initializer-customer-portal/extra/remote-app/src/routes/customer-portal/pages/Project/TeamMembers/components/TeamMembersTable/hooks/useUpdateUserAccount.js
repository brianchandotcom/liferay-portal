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

import {useReplaceAccountRoleByUserAccountEmailAddress} from '../../../../../../../../common/services/liferay/graphql/account-roles';
import {useUpdateRolesByContactEmailAddress} from '../../../../../../../../common/services/raysource/graphql/roles';

export default function useUpdateUserAccount() {
	const [
		replaceAccountRole,
		{called: replaceAccountRoleCalled, loading: replaceAccountRoleLoading},
	] = useReplaceAccountRoleByUserAccountEmailAddress();

	const [
		updateContactRoles,
		{called: updateContactRolesCalled, loading: updateContactRolesLoading},
	] = useUpdateRolesByContactEmailAddress();

	const updating = replaceAccountRoleLoading || updateContactRolesLoading;
	const updateCalled = replaceAccountRoleCalled && updateContactRolesCalled;

	return {
		replaceAccountRole,
		updateCalled,
		updateContactRoles,
		updating,
	};
}
