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

import React, {useCallback, useContext, useMemo, useState} from 'react';

import Filter from '../../shared/components/filter/Filter.es';
import {useFilterName} from '../../shared/components/filter/hooks/useFilterName.es';
import {useFilterStatic} from '../../shared/components/filter/hooks/useFilterStatic.es';
import filterConstants from '../../shared/components/filter/util/filterConstants.es';
import {
	getCapitalizedFilterKey,
	mergeItemsArray,
	replaceHistory,
} from '../../shared/components/filter/util/filterUtil.es';
import {parse, stringify} from '../../shared/components/router/queryString.es';
import {useFilter} from '../../shared/hooks/useFilter.es';
import {useRouter} from '../../shared/hooks/useRouter.es';
import {useRouterParams} from '../../shared/hooks/useRouterParams.es';
import {useSessionStorage} from '../../shared/hooks/useStorage.es';
import {AppContext} from '../AppContext.es';
import CustomTimeRangeForm from './CustomTimeRangeForm.es';
import {getCustomTimeRange, parseDateItems} from './util/timeRangeUtil.es';

export default function TimeRangeFilter({
	className,
	disabled,
	filterKey = filterConstants.timeRange.key,
	options = {},
	prefixKey = '',
}) {
	options = {
		hideControl: true,
		multiple: false,
		withSelectionTitle: true,
		withoutRouteParams: false,
		...options,
	};

	const {isAmPm} = useContext(AppContext);
	const {filters} = useRouterParams();
	const [formVisible, setFormVisible] = useState(false);

	const [storedTimeRanges = {}] = useSessionStorage('timeRanges');

	const {dispatch} = useFilter(options);

	const dateEndKey = getCapitalizedFilterKey(prefixKey, 'dateEnd');
	const dateStartKey = getCapitalizedFilterKey(prefixKey, 'dateStart');
	const isCustomFilter = (filter) => filter.key === 'custom';

	const onClickFilter = useCallback(
		(handleClick) => (currentItem) => {
			if (isCustomFilter(currentItem)) {
				setFormVisible(true);
			}
			else {
				handleClick(currentItem);
			}

			return true;
		},
		[]
	);

	const prefixedFilterKey = getCapitalizedFilterKey(prefixKey, filterKey);
	const routerProps = useRouter();

	const dateEnd = filters[dateEndKey];
	const dateStart = filters[dateStartKey];

	const customRange = useMemo(() => getCustomTimeRange(dateEnd, dateStart), [
		dateEnd,
		dateStart,
	]);

	const staticItems = useMemo(
		() =>
			parseDateItems(isAmPm)(
				mergeItemsArray([customRange], storedTimeRanges.items)
			),
		[customRange, storedTimeRanges.items, isAmPm]
	);

	const {items, selectedItems} = useFilterStatic({
		filterKey,
		prefixKey,
		propertyKey: 'id',
		staticItems,
		withoutRouteParams: options.withoutRouteParams,
	});

	const defaultItem = useMemo(
		() => items.find((timeRange) => timeRange.defaultTimeRange),
		[items]
	);

	if (defaultItem && options.withSelectionTitle && !selectedItems.length) {
		selectedItems[0] = defaultItem;
	}

	const filterName = useFilterName(
		options.multiple,
		selectedItems,
		Liferay.Language.get('completion-period'),
		options.withSelectionTitle
	);

	const handleSelectFilter = (filter) => {
		const filterValue = {[prefixedFilterKey]: [filter.key]};
		const query = parse(routerProps.location.search);

		if (!options.withoutRouteParams) {
			query.filters = {
				...query.filters,
				[dateEndKey]: filter.dateEnd,
				[dateStartKey]: filter.dateStart,
				...filterValue,
			};

			replaceHistory(stringify(query), routerProps);
		}
		else {
			dispatch(filterValue);
		}
	};

	return (
		<Filter
			childrenVisibility={formVisible}
			defaultItem={defaultItem}
			disabled={disabled}
			elementClasses={className}
			filterKey={filterKey}
			items={items}
			name={filterName}
			onClickFilter={onClickFilter(handleSelectFilter)}
			prefixKey={prefixKey}
			{...options}
		>
			<CustomTimeRangeForm
				handleSelectFilter={handleSelectFilter}
				items={items}
				prefixKey={prefixKey}
				setFormVisible={setFormVisible}
				withoutRouteParams={options.withoutRouteParams}
			/>
		</Filter>
	);
}
