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

import {useEffect, useState} from 'react';
import {ROLE_TYPES} from '../../../../../common/utils/constants';
import getProjectRoles from '../../../../../common/utils/getProjectRoles';

const useAccountUser = (project) => {
	const [accountRoles, setAccountRoles] = useState([]);
	const [accountRolesOptions, setAccountRolesOptions] = useState();
	const [administratorsAvailable, setAdministratorsAvailable] = useState();

	useEffect(() => {
		const getRoles = async () => {
			const roles = await getProjectRoles(project);
			if (roles) {
				setAccountRoles(roles);
			}
		};
		getRoles();
	}, [project]);

	useEffect(() => {
		if (accountRoles.length) {
			const filteredRoles = accountRoles.map((role) => {
				const isAdministratorOrRequestor =
					role.key === ROLE_TYPES.admin.key ||
					role.key === ROLE_TYPES.requester.key;

				return {
					...role,
					disabled:
						isAdministratorOrRequestor &&
						administratorsAvailable === 0,
				};
			});
			setAccountRolesOptions(filteredRoles);
		}
	}, [accountRoles, administratorsAvailable]);

	return {
		accountRoles: accountRolesOptions,
		administratorsAvailable,
		setAdministratorsAvailable,
	};
};

export default useAccountUser;
