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

import {INITIAL_FILTER} from './constants/initialFilter';

export default function getDateCreatedFilterTerm(
	initialFilter: string,
	activityPeriod: typeof INITIAL_FILTER.dateCreated
) {
	const filterDates = [];

	if (activityPeriod.dates.startDate) {
		filterDates.push(
			`dateCreated ge ${new Date(
				activityPeriod.dates.startDate
			).toISOString()}`
		);
	}
	if (activityPeriod.dates.endDate) {
		filterDates.push(
			`dateCreated le ${new Date(
				activityPeriod.dates.endDate
			).toISOString()}`
		);
	}

	return initialFilter.concat(
		filterDates.join(
			activityPeriod.dates.startDate > activityPeriod.dates.endDate
				? ' or '
				: ' and '
		)
	);
}
