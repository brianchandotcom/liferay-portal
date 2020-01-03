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

import React, {createContext, useCallback, useContext, useState} from 'react';

import {filterKeys} from '../../../shared/components/filter/util/filterConstants.es';
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

const useInstanceListData = (page, pageSize, processId, query) => {
	const [instanceId, setInstanceId] = useState();
	const [items, setItems] = useState([]);
	const [loading] = useState([]);
	const [searching, setSearching] = useState();
	const [totalCount, setTotalCount] = useState();
	const [selectAll, setSelectAll] = useState(false);
	const [selectedItems, setSelectedItems] = useState([]);

	const {client} = useContext(AppContext);
	const {getSelectedAssignees} = useContext(AssigneeContext);
	const {getSelectedProcessStatuses, isCompletedStatusSelected} = useContext(
		ProcessStatusContext
	);
	const {getSelectedProcessSteps} = useContext(ProcessStepContext);
	const {getSelectedSLAStatuses} = useContext(SLAStatusContext);
	const {getSelectedTimeRange} = useContext(TimeRangeContext);

	const filters = getFiltersParam(query);

	let baseURL = `/processes/${processId}/instances?page=${page}&pageSize=${pageSize}`;

	const selectedAssignees = getSelectedAssignees(
		filters[filterKeys.assignee]
	);
	const selectedProcessStatuses = getSelectedProcessStatuses(
		filters[filterKeys.processStatus]
	);
	const selectedProcessSteps = getSelectedProcessSteps(
		filters[filterKeys.processStep]
	);
	const selectedSLAStatuses = getSelectedSLAStatuses(
		filters[filterKeys.slaStatus]
	);
	const selectedTimeRange = getSelectedTimeRange();

	if (selectedAssignees && selectedAssignees.length) {
		baseURL += reduceFilters(selectedAssignees, filterKeys.assignee);
	}

	if (selectedProcessStatuses && selectedProcessStatuses.length) {
		baseURL += reduceFilters(
			selectedProcessStatuses,
			filterKeys.processStatus
		);
	}

	if (selectedProcessSteps && selectedProcessSteps.length) {
		baseURL += reduceFilters(selectedProcessSteps, filterKeys.processStep);
	}

	if (selectedSLAStatuses && selectedSLAStatuses.length) {
		baseURL += reduceFilters(selectedSLAStatuses, filterKeys.slaStatus);
	}

	if (
		isCompletedStatusSelected(filters[filterKeys.processStatus]) &&
		selectedTimeRange
	) {
		baseURL += `&${
			filterKeys.timeRangeDateEnd
		}=${selectedTimeRange.dateEnd.toISOString()}`;
		baseURL += `&${
			filterKeys.timeRangeDateStart
		}=${selectedTimeRange.dateStart.toISOString()}`;
	}

	const fetchInstances = useCallback(() => {
		setSearching(true);
		return client.get(baseURL).then(({data}) => {
			setSearching(false);
			setItems(data.items);
			setTotalCount(data.totalCount);
			return data;
		});
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [baseURL]);

	return {
		fetchInstances,
		instanceId,
		items,
		loading,
		searching,
		selectAll,
		selectedItems,
		setInstanceId,
		setSelectAll,
		setSelectedItems,
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

export {InstanceListContext, InstanceListProvider, useInstanceListData};
