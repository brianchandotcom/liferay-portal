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

export default function getActivityPeriodFilterTerm(
	initialFilter: string,
	filters: typeof INITIAL_FILTER
) {
	initialFilter = initialFilter ? initialFilter : '&filter=';

	const filterDates = [];

	if (filters.activityPeriod.dates.startDate) {
		filterDates.push(
			`minDateActivity ge ${filters.activityPeriod.dates.startDate}`
		);
	}
	if (filters.activityPeriod.dates.endDate) {
		filterDates.push(
			`maxDateActivity le ${filters.activityPeriod.dates.endDate}`
		);
	}

	return initialFilter.concat(
		filterDates.join(
			filters.activityPeriod.dates.startDate >
				filters.activityPeriod.dates.endDate
				? ' or '
				: ' and '
		)
	);
}
