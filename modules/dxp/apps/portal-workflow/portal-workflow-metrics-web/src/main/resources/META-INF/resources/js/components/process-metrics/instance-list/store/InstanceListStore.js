import React, {createContext, useContext, useEffect, useState} from 'react';
import {AppContext} from '../../../AppContext';
import {ErrorContext} from '../../../../shared/components/request/Error';
import {LoadingContext} from '../../../../shared/components/request/Loading';
import {ProcessStatusContext} from '../../filter/store/ProcessStatusStore';
import {ProcessStepContext} from '../../filter/store/ProcessStepStore';
import {SLAStatusContext} from '../../filter/store/SLAStatusStore';
import {TimeRangeContext} from '../../filter/store/TimeRangeStore';
import {getFiltersParam} from '../../../../shared/components/filter/util/filterUtil';

const filterConstants = {
	processStatus: 'statuses',
	processStep: 'taskKeys',
	slaStatus: 'slaStatuses',
	timeRange: 'timeRange',
	timeRangeDateEnd: 'dateEnd',
	timeRangeDateStart: 'dateStart'
};

const reduceFilters = (filterItems, paramKey) =>
	filterItems.reduce((acc, cur) => `&${paramKey}=${cur.key}${acc}`, '');

const useInstanceListData = (page, pageSize, processId, query) => {
	const [instanceId, setInstanceId] = useState();
	const [items, setItems] = useState([]);
	const [totalCount, setTotalCount] = useState();

	const {client, setTitle} = useContext(AppContext);
	const {getSelectedProcessStatuses, isCompletedStatusSelected} = useContext(
		ProcessStatusContext
	);
	const {getSelectedProcessSteps} = useContext(ProcessStepContext);
	const {getSelectedSLAStatuses} = useContext(SLAStatusContext);
	const {getSelectedTimeRange} = useContext(TimeRangeContext);
	const {setError} = useContext(ErrorContext);
	const {setLoading} = useContext(LoadingContext);

	const filters = getFiltersParam(query);

	const getInstancesRequestURL = () => {
		let baseURL = `/processes/${processId}/instances?page=${page}&pageSize=${pageSize}`;

		const selectedProcessStatuses = getSelectedProcessStatuses(
			filters[filterConstants.processStatus]
		);
		const selectedProcessSteps = getSelectedProcessSteps(
			filters[filterConstants.processStep]
		);
		const selectedSLAStatuses = getSelectedSLAStatuses(
			filters[filterConstants.slaStatus]
		);
		const selectedTimeRange = getSelectedTimeRange(
			filters[filterConstants.timeRange],
			filters[filterConstants.timeRangeDateEnd],
			filters[filterConstants.timeRangeDateStart]
		);

		if (selectedProcessStatuses && selectedProcessStatuses.length) {
			baseURL += reduceFilters(
				selectedProcessStatuses,
				filterConstants.processStatus
			);
		}

		if (selectedProcessSteps && selectedProcessSteps.length) {
			baseURL += reduceFilters(
				selectedProcessSteps,
				filterConstants.processStep
			);
		}

		if (selectedSLAStatuses && selectedSLAStatuses.length) {
			baseURL += reduceFilters(
				selectedSLAStatuses,
				filterConstants.slaStatus
			);
		}

		if (
			isCompletedStatusSelected(filters[filterConstants.processStatus]) &&
			selectedTimeRange
		) {
			baseURL += `&${
				filterConstants.timeRangeDateEnd
			}=${selectedTimeRange.dateEnd.toISOString()}`;
			baseURL += `&${
				filterConstants.timeRangeDateStart
			}=${selectedTimeRange.dateStart.toISOString()}`;
		}

		return baseURL;
	};

	const fetchInstances = () => {
		setError(null);
		setLoading(true);

		client
			.get(getInstancesRequestURL())
			.then(({data}) => {
				setItems(data.items);
				setTotalCount(data.totalCount);
			})
			.catch(error => {
				setError(error);
			})
			.then(() => {
				setLoading(false);
			});
	};

	const fetchProcess = () => {
		setError(null);
		setLoading(true);

		client
			.get(`/processes/${processId}/title`)
			.then(({data}) => {
				setTitle(`${data}: ${Liferay.Language.get('all-items')}`);
			})
			.catch(error => {
				setError(error);
			})
			.then(() => {
				setLoading(false);
			});
	};

	useEffect(fetchProcess, [processId]);

	useEffect(fetchInstances, [page, pageSize, processId, query]);

	return {
		instanceId,
		items,
		setInstanceId,
		totalCount
	};
};

const InstanceListContext = createContext(null);

const InstanceListProvider = ({children, page, pageSize, processId, query}) => {
	return (
		<InstanceListContext.Provider
			value={useInstanceListData(page, pageSize, processId, query)}
		>
			{children}
		</InstanceListContext.Provider>
	);
};

export {
	filterConstants,
	InstanceListContext,
	InstanceListProvider,
	useInstanceListData
};
