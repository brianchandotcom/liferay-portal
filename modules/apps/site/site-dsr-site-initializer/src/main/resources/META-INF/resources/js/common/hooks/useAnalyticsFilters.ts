/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useEffect, useState} from 'react';

import {TAnalyticsFilter} from '../../main_view/analytics/types';
import {toFilters, toStoredFilters} from '../../main_view/analytics/utils';
import AnalyticsService from '../services/AnalyticsService';

export default function useAnalyticsFilters(
	filtersJSONString: string | null,
	persisted: boolean = false
) {
	const [filters, setFilters] = useState<TAnalyticsFilter>(
		toFilters(filtersJSONString)
	);

	const setFilter = useCallback(
		(filter: TAnalyticsFilter) => {
			setFilters((filters: TAnalyticsFilter) => {
				const filterName = Object.keys(
					filter
				)[0] as keyof TAnalyticsFilter;

				const updatedFilterValue = JSON.stringify(filter[filterName]);
				const filterValue = JSON.stringify(filters[filterName]);

				if (filterValue !== updatedFilterValue) {
					return {
						...filters,
						[filterName]: filter[filterName],
					};
				}

				return filters;
			});
		},
		[setFilters]
	);

	useEffect(() => {
		const shouldStore = persisted
			? () => AnalyticsService.storeFilters(toStoredFilters(filters))
			: () => Promise.resolve();

		shouldStore().then(() => {
			Liferay.fire('dsr-filters-updated', {filters});
		});
	}, [filters, persisted]);

	return [filters, setFilter] as const;
}
