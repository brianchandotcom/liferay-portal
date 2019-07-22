import React, {useContext, useEffect, useState, createContext} from 'react';
import {AppContext} from '../../../AppContext';
import {formatTimeRange} from '../../util/timeRangeUtil';

function useTimeRange(timeRangeKeys) {
	const {client} = useContext(AppContext);
	const [timeRanges, setTimeRanges] = useState([]);

	const fetchData = () => {
		return client.get('/time-ranges').then(({data}) => {
			const timeRanges = data.items.map(item => {
				const itemKey = String(item.id);

				return {
					...item,
					active: timeRangeKeys.includes(itemKey),
					description: formatTimeRange(item),
					key: itemKey
				};
			});

			setTimeRanges(timeRanges);
		});
	};

	useEffect(() => {
		fetchData();
	}, []);

	const defaultTimeRange = getDefaultTimeRange(timeRanges);

	const getSelectedTimeRange = () => {
		if (!timeRanges || !timeRanges.length) {
			return null;
		}

		const selectedTimeRanges = timeRanges.filter(item => item.active);

		return selectedTimeRanges.length ? selectedTimeRanges[0] : null;
	};

	return {
		defaultTimeRange,
		getSelectedTimeRange,
		timeRanges
	};
}

function getDefaultTimeRange(timeRanges) {
	const defaultTimeRanges = timeRanges.filter(
		timeRange => timeRange.defaultTimeRange
	);

	return defaultTimeRanges.length ? defaultTimeRanges[0] : null;
}

const TimeRangeContext = createContext(null);

function TimeRangeProvider({children, timeRangeKeys}) {
	return (
		<TimeRangeContext.Provider value={useTimeRange(timeRangeKeys)}>
			{children}
		</TimeRangeContext.Provider>
	);
}

export {TimeRangeContext, TimeRangeProvider, useTimeRange};
