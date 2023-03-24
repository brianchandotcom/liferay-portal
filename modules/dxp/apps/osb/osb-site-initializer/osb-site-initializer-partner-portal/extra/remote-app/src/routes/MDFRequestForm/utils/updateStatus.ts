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

import LiferayPicklist from '../../../common/interfaces/liferayPicklist';
import MDFRequest from '../../../common/interfaces/mdfRequest';
import Role from '../../../common/interfaces/role';
import {Status} from '../../../common/utils/constants/status';
import {isLiferayManager} from '../../../common/utils/isLiferayManager';

const updateStatus = (
	values: MDFRequest,
	currentRequestStatus?: LiferayPicklist,
	roles?: Role[]
) => {
	if (!values.id) {
		values.mdfRequestStatus = currentRequestStatus;
	}
	else {
		if (
			roles &&
			!isLiferayManager(roles) &&
			currentRequestStatus !== Status.DRAFT
		) {
			values.mdfRequestStatus = Status.PENDING;
		}

		if (
			roles &&
			!isLiferayManager(roles) &&
			values.totalMDFRequestAmount >= 15000 &&
			values.mdfRequestStatus !== Status.DRAFT
		) {
			values.mdfRequestStatus = Status.MARKETING_DIRECTOR_REVIEW;
		}
	}

	return values;
};
export default updateStatus;
