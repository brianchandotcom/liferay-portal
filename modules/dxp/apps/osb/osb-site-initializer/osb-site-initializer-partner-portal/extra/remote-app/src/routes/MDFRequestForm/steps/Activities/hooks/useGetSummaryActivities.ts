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

import MDFRequestActivity from '../../../../../common/interfaces/mdfRequestActivity';
import getMaxDateActivity from '../../../../../common/utils/getMaxDateActivity';
import getMinDateActivity from '../../../../../common/utils/getMinDateActivity';
import getTotalBudget from '../../../../../common/utils/getTotalBudget';
import getTotalMDFRequest from '../../../../../common/utils/getTotalMDFRequest';

interface DateActivities {
	endDates: string[];
	startDates: string[];
}

export default function useGetSummaryActivities(
	mdfRequestActivities: MDFRequestActivity[]
) {
	const datesActivities = mdfRequestActivities.reduce<DateActivities>(
		(previousValue, currentValue) => {
			const endDateAccumulator = previousValue.endDates;
			const startDateAccumulator = previousValue.startDates;

			if (currentValue.endDate) {
				endDateAccumulator.push(currentValue.endDate);
			}

			if (currentValue.startDate) {
				startDateAccumulator.push(currentValue.startDate);
			}

			return {
				endDates: endDateAccumulator,
				startDates: startDateAccumulator,
			};
		},
		{endDates: [], startDates: []}
	);

	return {
		maxDateActivity: getMaxDateActivity(datesActivities.endDates),
		minDateActivity: getMinDateActivity(datesActivities.startDates),
		totalCostOfExpense: getTotalBudget(mdfRequestActivities),
		totalMDFRequestAmount: getTotalMDFRequest(mdfRequestActivities),
	};
}
