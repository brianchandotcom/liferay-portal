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

import LiferayPicklist from '../interfaces/liferayPicklist';
import {Status} from './constants/status';

const updateStatus = (
	status: LiferayPicklist,
	currentRequestStatus?: LiferayPicklist,
	changeStatus?: boolean,
	id?: number,
	totalMDFRequestAmount?: number
) => {
	if (!id && currentRequestStatus) {
		status = currentRequestStatus;
	}

	if (!currentRequestStatus && id) {
		status = Status.PENDING;
	}
	else {
		if (changeStatus && currentRequestStatus !== Status.DRAFT) {
			status = Status.PENDING;
		}

		if (
			changeStatus &&
			totalMDFRequestAmount &&
			totalMDFRequestAmount >= 15000 &&
			status !== Status.DRAFT
		) {
			status = Status.MARKETING_DIRECTOR_REVIEW;
		}
	}

	return status;
};
export default updateStatus;
