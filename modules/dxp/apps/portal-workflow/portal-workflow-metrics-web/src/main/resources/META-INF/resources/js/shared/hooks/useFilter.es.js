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
