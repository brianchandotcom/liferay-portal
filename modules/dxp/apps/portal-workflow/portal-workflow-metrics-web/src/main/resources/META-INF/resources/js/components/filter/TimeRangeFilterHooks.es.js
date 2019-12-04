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

import React, {useCallback, useMemo, useState} from 'react';

import Filter from '../../shared/components/filter/Filter.es';
import {useFilterFetch} from '../../shared/components/filter/hooks/useFilterFetch.es';
import {useFilterName} from '../../shared/components/filter/hooks/useFilterName.es';
import filterConstants from '../../shared/components/filter/util/filterConstants.es';
import {useRouterParams} from '../../shared/hooks/useRouterParams.es';
import {
	formatDescriptionDate,
	parseQueryDate
} from '../process-metrics/util/timeRangeUtil.es';
import {CustomTimeRangeForm} from './CustomTimeRangeForm.es';

const getCustomTimeRange = (dateEnd, dateStart) => {
	const customTimeRange = {
		dateEnd: parseQueryDate(dateEnd, true),
		dateStart: parseQueryDate(dateStart),
		dividerAfter: true,
		key: 'custom',
		name: Liferay.Language.get('custom-range')
	};

	customTimeRange.resultName = `${formatDescriptionDate(
		dateStart
	)} - ${formatDescriptionDate(dateEnd)}`;

	return customTimeRange;
};

const isCustomFilter = filter => filter.key === 'custom';

const TimeRangeFilter = ({
	className,
	dateEndKey = 'dateEnd',
	dateStartKey = 'dateStart',
	dispatch,
	filterKey = filterConstants.timeRange.key,
	options = {
		hideControl: false,
		multiple: false,
		position: 'left',
		withSelectionTitle: false
	}
}) => {
	const {filters} = useRouterParams();
	const {formVisible, onClickFilter, setFormVisible} = useCustomFormState();

	const staticItems = useMemo(
		() => [getCustomTimeRange(filters[dateEndKey], filters[dateStartKey])],
		[dateEndKey, dateStartKey, filters]
	);

	const {items, selectedItems} = useFilterFetch(
		dispatch,
		filterKey,
		'/time-ranges',
		staticItems
	);

	const defaultItem = useMemo(
		() => items.find(timeRange => timeRange.defaultTimeRange) || items[0],
		[items]
	);

	const filterName = useFilterName(
		options.multiple,
		selectedItems,
		Liferay.Language.get('completion-period'),
		options.withSelectionTitle
	);

	const onChangeFilter = selectedFilter => isCustomFilter(selectedFilter);

	return (
		<Filter
			defaultItem={defaultItem}
			elementClasses={className}
			filterKey={filterKey}
			items={items}
			name={filterName}
			onChangeFilter={onChangeFilter}
			onClickFilter={onClickFilter}
			{...options}
		>
			{formVisible && (
				<CustomTimeRangeForm
					filterKey={filterKey}
					setFormVisible={setFormVisible}
				/>
			)}
		</Filter>
	);
};

const useCustomFormState = () => {
	const [formVisible, setFormVisible] = useState(false);

	const onClickFilter = useCallback(currentItem => {
		if (isCustomFilter(currentItem)) {
			setFormVisible(true);

			if (currentItem.active) {
				document.dispatchEvent(new Event('mousedown'));
			}
		} else {
			setFormVisible(false);
		}

		return true;
	}, []);

	return {
		formVisible,
		onClickFilter,
		setFormVisible
	};
};

export default TimeRangeFilter;
