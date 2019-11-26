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

import React, {useContext} from 'react';

import Filter from '../../../shared/components/filter/Filter.es';
import filterConstants from '../../shared/components/filter/util/filterConstants.es';
import {CustomTimeRangeForm} from './CustomTimeRangeForm.es';
import {
	getCustomTimeRangeName,
	TimeRangeContext
} from './store/TimeRangeStore.es';

const TimeRangeFilter = ({
	buttonClassName,
	className,
	filterKey = filterConstants.timeRange.key,
	hideControl = false,
	position = 'left',
	showFilterName = true
}) => {
	const {
		defaultTimeRange,
		getSelectedTimeRange,
		setShowCustomForm,
		showCustomForm,
		timeRanges
	} = useContext(TimeRangeContext);

	const isCustomFilter = currentFilter => currentFilter.key === 'custom';

	const onChangeFilter = selectedFilter => {
		const preventDefault = isCustomFilter(selectedFilter);

		return preventDefault;
	};

	const onClickFilter = clickedFilter => {
		if (isCustomFilter(clickedFilter)) {
			setShowCustomForm(true);

			if (clickedFilter.active) {
				document.dispatchEvent(new Event('mousedown'));
			}
		} else {
			setShowCustomForm(false);
		}

		return true;
	};

	const selectedTimeRange = getSelectedTimeRange();

	return (
		<Filter
			buttonClassName={buttonClassName}
			defaultItem={defaultTimeRange}
			elementClasses={className}
			filterKey={filterKey}
			hideControl={hideControl}
			items={[...timeRanges]}
			multiple={false}
			name={getFilterName(selectedTimeRange, showFilterName)}
			onChangeFilter={onChangeFilter}
			onClickFilter={onClickFilter}
			position={position}
		>
			{showCustomForm && <CustomTimeRangeForm filterKey={filterKey} />}
		</Filter>
	);
};

const getFilterName = (selectedTimeRange, showFilterName) => {
	if (showFilterName) {
		return Liferay.Language.get('completion-period');
	}

	if (!selectedTimeRange) {
		return '';
	}

	if (selectedTimeRange.key === 'custom') {
		return getCustomTimeRangeName(selectedTimeRange);
	}

	return selectedTimeRange.name;
};

export {TimeRangeFilter};
