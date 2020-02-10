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

import React, {useContext, useMemo} from 'react';

import Filter from '../../shared/components/filter/Filter.es';
import {useFilterName} from '../../shared/components/filter/hooks/useFilterName.es';
import {useFilterStatic} from '../../shared/components/filter/hooks/useFilterStatic.es';
import filterConstants from '../../shared/components/filter/util/filterConstants.es';
import {mergeItemsArray} from '../../shared/components/filter/util/filterUtil.es';
import {useRouterParams} from '../../shared/hooks/useRouterParams.es';
import {useSessionStorage} from '../../shared/hooks/useStorage.es';
import {AppContext} from '../AppContext.es';
import {CustomTimeRangeForm} from './CustomTimeRangeForm.es';
import {useCustomFormState} from './hooks/useCustomFormState.es';
import {getCustomTimeRange, parseDateItems} from './util/timeRangeUtil.es';

const TimeRangeFilter = ({
	buttonClassName,
	className,
	disabled,
	dispatch,
	filterKey = filterConstants.timeRange.key,
	options = {},
	prefixKey = '',
	style
}) => {
	const defaultOptions = {
		hideControl: true,
		multiple: false,
		position: 'left',
		withSelectionTitle: true
	};
	// eslint-disable-next-line react-hooks/exhaustive-deps
	options = useMemo(() => ({...defaultOptions, ...options}), [options]);

	const {isAmPm} = useContext(AppContext);
	const {filters} = useRouterParams();
	const {
		formVisible,
		onChangeFilter,
		onClickFilter,
		setFormVisible
	} = useCustomFormState();
	const [storedTimeRanges = {}] = useSessionStorage('timeRanges');

	const dateEnd = filters[`${prefixKey}dateEnd`];
	const dateStart = filters[`${prefixKey}dateStart`];

	const {items: timeRanges} = useMemo(() => storedTimeRanges, [
		storedTimeRanges
	]);

	const customRange = useMemo(() => getCustomTimeRange(dateEnd, dateStart), [
		dateEnd,
		dateStart
	]);

	const staticItems = useMemo(
		() =>
			parseDateItems(isAmPm)(mergeItemsArray([customRange], timeRanges)),
		[customRange, timeRanges, isAmPm]
	);

	const {items, selectedItems} = useFilterStatic(
		dispatch,
		filterKey,
		prefixKey,
		staticItems
	);

	const defaultItem = useMemo(
		() => items.find(timeRange => timeRange.defaultTimeRange),
		[items]
	);

	if (defaultItem && !selectedItems.length) {
		selectedItems[0] = defaultItem;
	}

	const filterName = useFilterName(
		options.multiple,
		selectedItems,
		Liferay.Language.get('completion-period'),
		options.withSelectionTitle
	);

	return (
		<Filter
			buttonClassName={buttonClassName}
			dataTestId="timeRangeFilter"
			defaultItem={defaultItem}
			disabled={disabled}
			elementClasses={className}
			filterKey={filterKey}
			items={items}
			name={filterName}
			onChangeFilter={onChangeFilter}
			onClickFilter={onClickFilter}
			prefixKey={prefixKey}
			{...options}
			style={style}
		>
			{formVisible && (
				<CustomTimeRangeForm
					filterKey={filterKey}
					items={items}
					prefixKey={prefixKey}
					setFormVisible={setFormVisible}
				/>
			)}
		</Filter>
	);
};

export default TimeRangeFilter;
