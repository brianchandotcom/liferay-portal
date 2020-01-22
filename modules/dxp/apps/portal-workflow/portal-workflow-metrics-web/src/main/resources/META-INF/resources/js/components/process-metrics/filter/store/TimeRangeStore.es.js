/* eslint-disable react-hooks/exhaustive-deps */
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

import {usePrevious} from 'frontend-js-react-web';
import React, {createContext, useContext, useEffect, useState} from 'react';

import {getFiltersParam} from '../../../../shared/components/filter/util/filterUtil.es';
import {ErrorContext} from '../../../../shared/components/request/Error.es';
import {LoadingContext} from '../../../../shared/components/request/Loading.es';
import {useRouter} from '../../../../shared/hooks/useRouter.es';
import {compareArrays} from '../../../../shared/util/array.es';
import {AppContext} from '../../../AppContext.es';
import {
	formatDescriptionDate,
	formatTimeRange,
	parseQueryDate
} from '../../util/timeRangeUtil.es';

const useTimeRange = timeRangeKeys => {
	const {client, isAmPm} = useContext(AppContext);
	const {
		location: {search}
	} = useRouter();
	const {setError} = useContext(ErrorContext);
	const {setLoading} = useContext(LoadingContext);
	const [showCustomForm, setShowCustomForm] = useState(false);
	const [timeRanges, setTimeRanges] = useState([]);

	const filters = getFiltersParam(search);

	const fetchData = () => {
		setError(null);
		setLoading(true);

		client
			.get('/time-ranges')
			.then(({data}) => {
				const timeRanges = [
					getCustomTimeRange(filters, timeRangeKeys),
					...data.items.map(item => {
						const itemKey = String(item.id);

						return {
							...item,
							active: timeRangeKeys.includes(itemKey),
							dateEnd: new Date(item.dateEnd),
							dateStart: new Date(item.dateStart),
							description: formatTimeRange(item, isAmPm),
							key: itemKey
						};
					})
				];

				setTimeRanges(timeRanges);
			})
			.catch(error => {
				setError(error);
			})
			.then(() => {
				setLoading(false);
			});
	};

	const defaultTimeRange = getDefaultTimeRange(timeRanges);

	const getSelectedTimeRange = () => {
		const selectedTimeRanges = timeRanges.filter(item => item.active);

		return selectedTimeRanges.length ? selectedTimeRanges[0] : null;
	};

	const updateData = () => {
		setTimeRanges(
			timeRanges.map(timeRange => ({
				...timeRange,
				active: timeRangeKeys.includes(timeRange.key)
			}))
		);
	};

	useEffect(fetchData, []);

	const previousKeys = usePrevious(timeRangeKeys);

	useEffect(() => {
		const filterChanged = !compareArrays(previousKeys, timeRangeKeys);

		if (filterChanged && timeRanges.length) {
			updateData();
		}
	}, [timeRangeKeys]);

	return {
		defaultTimeRange,
		fetchData,
		getSelectedTimeRange,
		setShowCustomForm,
		setTimeRanges,
		showCustomForm,
		timeRanges
	};
};

const getCustomTimeRange = (filters, timeRangeKeys) => {
	const itemKey = 'custom';

	const customTimeRange = {
		active: timeRangeKeys.includes(itemKey),
		dateEnd: parseQueryDate(filters.dateEnd, true),
		dateStart: parseQueryDate(filters.dateStart),
		dividerAfter: true,
		key: itemKey,
		name: Liferay.Language.get('custom-range')
	};

	customTimeRange.resultName = getCustomTimeRangeName(filters);
	customTimeRange.getName = timeRange => getCustomTimeRangeName(timeRange);

	return customTimeRange;
};

const getCustomTimeRangeName = selectedTimeRange => {
	const {dateEnd, dateStart} = selectedTimeRange;
	const formatDate = date => formatDescriptionDate(date);

	return `${formatDate(dateStart)} - ${formatDate(dateEnd)}`;
};

const getDefaultTimeRange = timeRanges => {
	const defaultTimeRanges = timeRanges.filter(
		timeRange => timeRange.defaultTimeRange
	);

	return defaultTimeRanges.length ? defaultTimeRanges[0] : null;
};

const TimeRangeContext = createContext(null);

const TimeRangeProvider = ({children, timeRangeKeys}) => {
	return (
		<TimeRangeContext.Provider value={useTimeRange(timeRangeKeys)}>
			{children}
		</TimeRangeContext.Provider>
	);
};

export {
	getCustomTimeRangeName,
	TimeRangeContext,
	TimeRangeProvider,
	useTimeRange
};
