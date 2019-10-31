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

import {
	getFiltersParam,
	reduceFilters
} from '../../../shared/components/filter/util/filterUtil.es';
import {AppContext} from '../../AppContext.es';
import {AssigneeContext} from '../../process-metrics/filter/store/AssigneeStore.es';
import {ProcessStatusContext} from '../../process-metrics/filter/store/ProcessStatusStore.es';
import {ProcessStepContext} from '../../process-metrics/filter/store/ProcessStepStore.es';
import {SLAStatusContext} from '../../process-metrics/filter/store/SLAStatusStore.es';
import {TimeRangeContext} from '../../process-metrics/filter/store/TimeRangeStore.es';

const filterConstants = {
	assignees: 'assigneeUserIds',
	processStatus: 'statuses',
	processStep: 'taskKeys',
	slaStatus: 'slaStatuses',
	timeRange: 'timeRange',
	timeRangeDateEnd: 'dateEnd',
	timeRangeDateStart: 'dateStart'
};

const useInstanceListData = (page, pageSize, processId, query) => {
	const [instanceId, setInstanceId] = useState();
	const [items, setItems] = useState([]);
	const [loading] = useState([]);
	const [searching, setSearching] = useState();
	const [totalCount, setTotalCount] = useState();

	const {client} = useContext(AppContext);
	const {getSelectedAssignees} = useContext(AssigneeContext);
	const {getSelectedProcessStatuses, isCompletedStatusSelected} = useContext(
		ProcessStatusContext
	);
	const {getSelectedProcessSteps} = useContext(ProcessStepContext);
	const {getSelectedSLAStatuses} = useContext(SLAStatusContext);
	const {getSelectedTimeRange} = useContext(TimeRangeContext);

	const filters = getFiltersParam(query);

	const getInstancesRequestURL = () => {
		setSearching(false);

		let baseURL = `/processes/${processId}/instances?page=${page}&pageSize=${pageSize}`;

		const selectedAssignees = getSelectedAssignees(
			filters[filterConstants.assignees]
		);
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

		if (selectedAssignees && selectedAssignees.length) {
			setSearching(true);

			baseURL += reduceFilters(
				selectedAssignees,
				filterConstants.assignees
			);
		}

		if (selectedProcessStatuses && selectedProcessStatuses.length) {
			setSearching(true);

			baseURL += reduceFilters(
				selectedProcessStatuses,
				filterConstants.processStatus
			);
		}

		if (selectedProcessSteps && selectedProcessSteps.length) {
			setSearching(true);

			baseURL += reduceFilters(
				selectedProcessSteps,
				filterConstants.processStep
			);
		}

		if (selectedSLAStatuses && selectedSLAStatuses.length) {
			setSearching(true);

			baseURL += reduceFilters(
				selectedSLAStatuses,
				filterConstants.slaStatus
			);
		}

		if (
			isCompletedStatusSelected(filters[filterConstants.processStatus]) &&
			selectedTimeRange
		) {
			setSearching(true);

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
		return client.get(getInstancesRequestURL()).then(({data}) => {
			setItems(data.items);
			setTotalCount(data.totalCount);
			return data;
		});
	};

	return {
		fetchInstances,
		instanceId,
		items,
		loading,
		searching,
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
