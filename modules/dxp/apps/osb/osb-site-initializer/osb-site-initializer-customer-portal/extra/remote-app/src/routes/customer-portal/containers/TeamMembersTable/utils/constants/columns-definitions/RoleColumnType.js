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
	const filteredRoles = Object.values(ROLE_TYPES).find(
		(roleType) => roleType.key === roles[0]
	);

	return <p className="m-0 text-truncate">{filteredRoles?.name}</p>;
};

export {RoleColumnType};
