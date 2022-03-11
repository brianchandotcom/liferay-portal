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
import {useCallback} from 'react';
import {ROLE_TYPES} from '../../../../../../../common/utils/constants';
import SelectRole from '../../../components/SelectRoles';
import {getIsEditingUser} from '../../getIsEditingUser';

const RoleColumnType = ({
	accountRoles,
	selectedRole,
	setSelectedRole,
	userAccount,
	userAction,
}) => {
	const isEditingUser = getIsEditingUser(userAction, userAccount?.id);

	const getCurrentActiveRole = useCallback(() => {
		const roleValues = Object.values(ROLE_TYPES);

		const filteredRoles = userAccount?.roles?.filter((role) =>
			roleValues?.some((roleType) => roleType?.key === role)
		);

		if (filteredRoles.length) {
			const activeRole = roleValues.find(
				(role) => role.key === filteredRoles[0]
			);

			return activeRole?.name;
		}

		return ROLE_TYPES.member.name;
	}, [userAccount?.roles]);

	const currentRole = getCurrentActiveRole();

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
