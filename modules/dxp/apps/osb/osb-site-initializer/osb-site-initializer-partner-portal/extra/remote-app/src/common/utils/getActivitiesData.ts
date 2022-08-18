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

import useTotalBudget from '../hooks/useTotalBudget';
import MDFRequest from '../interfaces/mdfRequest';
import MDFRequestActivity from '../interfaces/mdfRequestActivity';

interface DateActivities {
	endDates: Date[];
	startDates: Date[];
}

export default function getActivitiesData(mdfRequest: MDFRequest) {
	if (mdfRequest.activities.length) {
		const datesActivities = mdfRequest.activities.reduce(
			(dateAccumulator: DateActivities, activity: MDFRequestActivity) => {
				return {
					endDates: [...dateAccumulator.endDates, activity.endDate],
					startDates: [
						...dateAccumulator.startDates,
						activity.startDate,
					],
				};
			},
			{endDates: [], startDates: []}
		);

		const maxDateActivity = new Date(
			Math.min(
				Number(
					...datesActivities.startDates.map((date) => new Date(date))
				)
			)
		);
		const minDateActivity = new Date(
			Math.max(
				Number(
					...datesActivities.endDates.map((date) => new Date(date))
				)
			)
		);

		const totalCostOfExpense = useTotalBudget(mdfRequest);

		return {
			maxDateActivity,
			minDateActivity,
			totalCostOfExpense,
		};
	}
}
