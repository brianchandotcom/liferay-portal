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

import {TypeDiff} from '../../../../enums/typeDiff';

export default function monthDiff(
	startDate: Date,
	endDate: Date,
	typeDiff: TypeDiff
) {
	if (typeDiff === TypeDiff.MONTH) {
		return (
			endDate.getMonth() -
			startDate.getMonth() +
			12 * (endDate.getFullYear() - startDate.getFullYear())
		);
	}

	if (typeDiff === TypeDiff.DAY) {
		return endDate.getDate() - startDate.getDate();
	}

	return false;
}
