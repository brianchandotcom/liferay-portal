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
import SelectRole from '../../../components/SelectRoles';
import {getCurrentActiveRoles} from '../../getCurrentActiveRoles';
import {getIsEditingUser} from '../../getIsEditingUser';

const RoleColumnType = ({
	accountRoles,
	selectedRole,
	setSelectedRole,
	userAccount,
	userAction,
}) => {
	const isEditingUser = getIsEditingUser(userAction, userAccount?.id);

	const currentRole = useMemo(
		() => getCurrentActiveRoles(userAccount?.roles),
		[userAccount?.roles]
	);

	return isEditingUser ? (
		<SelectRole
			accountRoles={accountRoles}
			currentRole={currentRole}
			selectedRole={selectedRole}
			setSelectedRole={setSelectedRole}
		/>
	) : (
		<p className="m-0 text-truncate">{currentRole}</p>
	);
};

export {RoleColumnType};
