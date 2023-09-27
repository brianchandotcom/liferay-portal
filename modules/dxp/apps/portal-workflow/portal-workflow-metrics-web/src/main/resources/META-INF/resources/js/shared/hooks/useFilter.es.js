/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useContext, useMemo} from 'react';

import {FilterContext} from '../components/filter/FilterContext.es';
import {useFiltersConstants} from '../components/filter/hooks/useFiltersConstants.es';
import {
	getCapitalizedFilterKey,
	getFilterResults,
	getSelectedItems,
} from '../components/filter/util/filterUtil.es';
import {useRouterParams} from './useRouterParams.es';

const useFilter = ({
	filterKeys = [],
	prefixKeys = [''],
	withoutRouteParams,
}) => {
	const {dispatch, dispatchFilter, filterState, filterValues} = useContext(
		FilterContext
	);

	const {filters} = useRouterParams();
	const {keys, pinnedValues, titles} = useFiltersConstants(filterKeys);

	const filtersError = filterKeys
		.map((filterKey) => filterState.errors?.includes(filterKey))
		.some((hasError) => hasError);

	const prefixedKeys = keys.reduce(
		(keys, key) => [
			...keys,
			...prefixKeys.map((prefix) => getCapitalizedFilterKey(prefix, key)),
		],
		[]
	);

	const selectedFilters = useMemo(
		() =>
			getSelectedItems(
				getFilterResults(
					prefixedKeys,
					pinnedValues,
					titles,
					filterState
				)
			),
		// eslint-disable-next-line react-hooks/exhaustive-deps
		[filterState]
	);

	return {
		dispatch,
		dispatchFilter,
		filterState,
		filterValues: withoutRouteParams ? filterValues : filters,
		filtersError,
		prefixedKeys,
		selectedFilters,
	};
};

export {useFilter};
