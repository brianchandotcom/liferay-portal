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
import {useAppPropertiesContext} from '../../../../../../../../common/contexts/AppPropertiesContext';
import getProjectRoles from '../../../../../../../../common/utils/getProjectRoles';

const useAccountRoles = (project) => {
	const [accountRoles, setAccountRoles] = useState([]);
	const {client} = useAppPropertiesContext();

	useEffect(() => {
		const getRoles = async () => {
			const roles = await getProjectRoles(client, project);
			if (roles) {
				setAccountRoles(roles);
			}
		};
		getRoles();
	}, [client, project]);

	return {accountRoles};
};

export default useAccountRoles;
