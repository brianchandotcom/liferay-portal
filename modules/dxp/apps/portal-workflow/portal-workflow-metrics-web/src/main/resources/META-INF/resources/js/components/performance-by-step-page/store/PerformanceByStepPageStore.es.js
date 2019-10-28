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

import React, {createContext, useContext, useState} from 'react';

import {AppContext} from '../../AppContext.es';
import {TimeRangeContext} from '../../process-metrics/filter/store/TimeRangeStore.es';

const usePerformanceData = (queryDateEnd, queryDateStart, timeRangeKeys) => {
	const {client} = useContext(AppContext);
	const {getSelectedTimeRange} = useContext(TimeRangeContext);
	const [items, setItems] = useState([]);
	const [totalCount, setTotalCount] = useState(0);

	const fetchData = (page, pageSize, processId, search, sort) => {
		const params = {
			page,
			pageSize,
			sort: decodeURIComponent(sort)
		};

		if (typeof search === 'string' && search) {
			params.key = decodeURIComponent(search);
		}

		const isValidDate = date => date && !isNaN(date);

		const timeRange = getSelectedTimeRange(
			timeRangeKeys,
			queryDateEnd,
			queryDateStart
		);

		if (
			timeRange &&
			isValidDate(timeRange.dateEnd) &&
			isValidDate(timeRange.dateStart)
		) {
			const {dateEnd, dateStart} = timeRange;
			params.dateEnd = dateEnd.toISOString();
			params.dateStart = dateStart.toISOString();
		}

		return client
			.get(`/processes/${processId}/tasks?completed=true`, {params})
			.then(({data}) => {
				setTotalCount(() => data.totalCount);
				setItems(() => data.items);
				return data;
			});
	};

	return {
		fetchData,
		items,
		totalCount
	};
};

const PerformanceDataContext = createContext();

const PerformanceDataProvider = ({
	children,
	queryDateEnd,
	queryDateStart,
	timeRangeKeys
}) => {
	return (
		<PerformanceDataContext.Provider
			value={usePerformanceData(
				queryDateEnd,
				queryDateStart,
				timeRangeKeys
			)}
		>
			{children}
		</PerformanceDataContext.Provider>
	);
};

export {PerformanceDataProvider, PerformanceDataContext, usePerformanceData};
