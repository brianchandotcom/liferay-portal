/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	AnalyticsFilters,
	IAnalyticsUserFilter,
	TAnalyticsFilter,
	TDateRangeAnalyticsFilterValue,
	TRoomAnalyticsFilterValue,
} from '../../main_view/analytics/types';

const hasVariable = (query: string, variableName: string): boolean => {
	const regex = new RegExp(`\\$${variableName}\\b`);

	return regex.test(query);
};

export default function formatVariables(
	filters: TAnalyticsFilter,
	query: string,
	variables: any
) {
	if (hasVariable(query, 'channelId')) {
		const roomAnalyticsFilterValue = filters[AnalyticsFilters.ROOM]
			.value as TRoomAnalyticsFilterValue;

		variables = {
			...variables,
			channelId: roomAnalyticsFilterValue.channelId,
		};
	}

	if (hasVariable(query, 'emailAddresses')) {
		const analyticsUserFilter = filters[
			AnalyticsFilters.USER
		] as IAnalyticsUserFilter;

		variables = {...variables, emailAddresses: analyticsUserFilter.value};
	}

	if (hasVariable(query, 'rangeEnd')) {
		const dateRangeAnalyticsFilterValue = filters[
			AnalyticsFilters.DATE_RANGE
		].value as TDateRangeAnalyticsFilterValue;

		variables = {...variables, rangeEnd: dateRangeAnalyticsFilterValue.to};
	}

	if (hasVariable(query, 'rangeStart')) {
		const dateRangeAnalyticsFilterValue = filters[
			AnalyticsFilters.DATE_RANGE
		].value as TDateRangeAnalyticsFilterValue;

		variables = {
			...variables,
			rangeStart: dateRangeAnalyticsFilterValue.from,
		};
	}

	return variables;
}
