import React, {createContext, useContext, useEffect, useState} from 'react';
import {AppContext} from '../../../AppContext';
import {ErrorContext} from '../../../../shared/components/request/Error';
import {formatTimeRange} from '../../util/timeRangeUtil';
import {LoadingContext} from '../../../../shared/components/request/Loading';

function useTimeRange(timeRangeKeys) {
	const {client} = useContext(AppContext);
	const {setError} = useContext(ErrorContext);
	const {setLoading} = useContext(LoadingContext);
	const [timeRanges, setTimeRanges] = useState([]);

	const fetchData = () => {
		setError(null);
		setLoading(true);

		return client
			.get('/time-ranges')
			.then(({data}) => {
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
			})
			.catch(error => {
				setError(error);
			})
			.then(() => {
				setLoading(false);
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
