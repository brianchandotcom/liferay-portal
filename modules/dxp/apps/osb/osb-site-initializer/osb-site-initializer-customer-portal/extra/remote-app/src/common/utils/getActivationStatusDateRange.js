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

import {FORMAT_DATE_TYPES} from './constants';
import getDateCustomFormat from './getDateCustomFormat';

export default function getActivationStatusDateRange(orderItems) {
	const dates = orderItems.reduce(
		(dateAccumulator, orderItem) => {
			const options = JSON.parse(orderItem.options);

			return {
				endDates: [...dateAccumulator.endDates, options.endDate],
				startDates: [...dateAccumulator.startDates, options.startDate],
			};
		},
		{endDates: [], startDates: []}
	);
	const earliestStartDate = new Date(
		Math.min(...dates.startDates.map((date) => new Date(date)))
	);
	const farthestEndDate = new Date(
		Math.max(...dates.endDates.map((date) => new Date(date)))
	);
	const activationStatusDateRange = `${getDateCustomFormat(
		earliestStartDate,
		FORMAT_DATE_TYPES.day2DMonthSYearN
	)} - ${getDateCustomFormat(
		farthestEndDate,
		FORMAT_DATE_TYPES.day2DMonthSYearN
	)}`;

	return activationStatusDateRange;
}
