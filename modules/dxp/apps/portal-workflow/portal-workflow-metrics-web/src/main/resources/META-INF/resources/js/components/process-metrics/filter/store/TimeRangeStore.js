import React, {createContext, useContext, useEffect, useState} from 'react';
import {formatTimeRange, parseQueryDate} from '../../util/timeRangeUtil';
import {AppContext} from '../../../AppContext';
import {ErrorContext} from '../../../../shared/components/request/Error';
import {getFiltersParam} from '../../../../shared/components/filter/util/filterUtil';
import {LoadingContext} from '../../../../shared/components/request/Loading';
import {useRouter} from '../../../../shared/components/router/useRouter';

const useTimeRange = timeRangeKeys => {
	const {client} = useContext(AppContext);
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

		return client
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
							description: formatTimeRange(item),
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
		if (!timeRanges || !timeRanges.length) {
			return null;
		}

		const selectedTimeRanges = timeRanges.filter(item => item.active);

		return selectedTimeRanges.length ? selectedTimeRanges[0] : null;
	};

	useEffect(() => {
		fetchData();
	}, []);

	return {
		defaultTimeRange,
		getSelectedTimeRange,
		showCustomForm,
		setShowCustomForm,
		setTimeRanges,
		timeRanges
	};
};

const getCustomTimeRange = (filters, timeRangeKeys) => {
	const itemKey = 'custom';

	return {
		active: timeRangeKeys.includes(itemKey),
		dateEnd: parseQueryDate(filters.dateEnd, true),
		dateStart: parseQueryDate(filters.dateStart),
		dividerAfter: true,
		key: itemKey,
		name: Liferay.Language.get('custom-range')
	};
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

export {TimeRangeContext, TimeRangeProvider, useTimeRange};
