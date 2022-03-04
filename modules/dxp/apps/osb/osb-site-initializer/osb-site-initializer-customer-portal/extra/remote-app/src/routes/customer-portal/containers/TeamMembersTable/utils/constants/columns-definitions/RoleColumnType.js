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

import {ROLE_TYPES} from '../../../../../../../common/utils/constants';

const RoleColumnType = ({roles}) => {
	const getCurrentActiveRole = () => {
		const roleValues = Object.values(ROLE_TYPES);

		const filteredRoles = roles
			?.filter((role) =>
				roleValues?.some((roleType) => roleType?.key === role)
			)
			?.sort();

		if (filteredRoles.length) {
			const isAdminUser = roles.find(
				(role) => role === ROLE_TYPES.admin.key
			);
			if (isAdminUser) {
				return ROLE_TYPES.admin.name;
			}
			const isRequesterUser = roles.find(
				(role) => role === ROLE_TYPES.requestor.key
			);
			if (isRequesterUser) {
				return ROLE_TYPES.requestor.name;
			}

			const activeRole = roleValues.find(
				(role) => role.key === filteredRoles[0]
			)?.name;

			return activeRole;
		}

		return ROLE_TYPES.member.name;
	};
	const currentRole = getCurrentActiveRole();

	return <p className="m-0 text-truncate">{currentRole}</p>;
};

export {RoleColumnType};
