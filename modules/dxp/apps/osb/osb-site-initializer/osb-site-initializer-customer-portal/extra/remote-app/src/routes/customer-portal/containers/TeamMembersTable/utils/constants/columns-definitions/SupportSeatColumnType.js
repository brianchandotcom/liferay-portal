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

import ClayIcon from '@clayui/icon';
import {memo} from 'react';
import {ROLE_TYPES} from '../../../../../../../common/utils/constants';

const SupportSeatColumnType = memo(({roles}) => {
	const hasAdministratorAccess = !!roles?.find(
		(role) =>
			role === ROLE_TYPES.admin.key || role === ROLE_TYPES.requester.key
	);

	return (
		<>
			{hasAdministratorAccess && (
				<ClayIcon
					className="cp-team-members-support-seat-icon"
					symbol="check-circle-full"
				/>
			)}
		</>
	);
});

export {SupportSeatColumnType};
