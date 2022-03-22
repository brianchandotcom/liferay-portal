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

import {ROLE_TYPES} from '../../../../../common/utils/constants';
const SUPPORT_SEAT = 'Support Seat';
const NON_SUPPORT_SEAT = 'Non-Support Seat';

export function getSupportSeat(roles) {
	const hasSupportSeat = !!roles?.find(
		(role) =>
			role === ROLE_TYPES.admin.key || role === ROLE_TYPES.requester.key
	);

	return hasSupportSeat ? SUPPORT_SEAT : NON_SUPPORT_SEAT;
}
