import {CustomTimeRangeForm} from './CustomTimeRangeForm';
import Filter from '../../../../shared/components/filter/Filter';
import moment from '../../../../shared/util/moment';
import React from 'react';
import {TimeRangeContext} from '../store/TimeRangeStore';
import {useContext} from 'react';

const TimeRangeFilter = ({filterKey = 'timeRange', position = 'right'}) => {
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
			defaultItem={defaultTimeRange}
			filterKey={filterKey}
			hideControl={true}
			items={[...timeRanges]}
			multiple={false}
			name={getFilterName(selectedTimeRange)}
			onChangeFilter={onChangeFilter}
			onClickFilter={onClickFilter}
			position={position}
		>
			{showCustomForm && <CustomTimeRangeForm filterKey={filterKey} />}
		</Filter>
	);
};

const getFilterName = selectedTimeRange => {
	if (!selectedTimeRange) {
		return '';
	}

	const {dateEnd, dateStart} = selectedTimeRange;
	const formatDate = date => moment.utc(date).format('ll');

	if (selectedTimeRange.key === 'custom') {
		return `${formatDate(dateStart)} - ${formatDate(dateEnd)}`;
	}

	return selectedTimeRange.name;
};

export {TimeRangeFilter};
