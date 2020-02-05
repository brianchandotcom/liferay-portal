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

import React, {useMemo, useState} from 'react';

import Panel from '../../../shared/components/Panel.es';
import PromisesResolver from '../../../shared/components/promises-resolver/PromisesResolver.es';
import {useFetch} from '../../../shared/hooks/useFetch.es';
import {useFilter} from '../../../shared/hooks/useFilter.es';
import ProcessStepFilter from '../../filter/ProcessStepFilter.es';
import {Body} from './WorkloadByAssigneeCardBody.es';
import Tabs from './WorkloadByAssigneeCardTabs.es';

const Header = ({dispatch, processId}) => (
	<>
		<Panel.HeaderWithOptions
			description={Liferay.Language.get(
				'workload-by-assignee-description'
			)}
			elementClasses="dashboard-panel-header"
			title={Liferay.Language.get('workload-by-assignee')}
			tooltipPosition="bottom"
		/>

		<div className="management-bar management-bar-light ml-3 navbar navbar-expand-md pl-1">
			<ul className="navbar-nav">
				<ProcessStepFilter
					dispatch={dispatch}
					options={{
						hideControl: true,
						multiple: false,
						withAllSteps: true,
						withSelectionTitle: true
					}}
					processId={processId}
				/>
			</ul>
		</div>
	</>
);

const WorkloadByAssigneeCard = ({routeParams}) => {
	const {processId} = routeParams;
	const [currentTab, setCurrentTab] = useState('overdue');

	const filterKeys = ['processStep'];
	const {
		dispatch,
		filterValues: {taskKeys}
	} = useFilter(filterKeys);

	const params = getParams(currentTab, taskKeys);
	const {data, fetchData} = useFetch({
		params,
		url: `/processes/${processId}/assignee-users`
	});

	const promises = useMemo(() => [fetchData()], [fetchData]);

	return (
		<PromisesResolver promises={promises}>
			<Panel elementClasses="workload-by-assignee-card">
				<WorkloadByAssigneeCard.Header
					dispatch={dispatch}
					processId={processId}
				/>

				<WorkloadByAssigneeCard.Tabs
					currentTab={currentTab}
					setCurrentTab={setCurrentTab}
				/>

				<WorkloadByAssigneeCard.Body
					currentTab={currentTab}
					data={data}
					processStepKey={params.taskKeys}
					{...routeParams}
				/>
			</Panel>
		</PromisesResolver>
	);
};

const getParams = (currentTab, taskKeys) => {
	const params = {
		page: 1,
		pageSize: 10
	};

	if (taskKeys && taskKeys.length) {
		const key = taskKeys[0];

		if (key !== 'allSteps') {
			params.taskKeys = key;
		}
	}

	if (currentTab === 'overdue') {
		params.sort = 'overdueTaskCount:desc';
	}
	else if (currentTab === 'onTime') {
		params.sort = 'onTimeTaskCount:desc';
	}
	else {
		params.sort = 'taskCount:desc';
	}

	return params;
};

WorkloadByAssigneeCard.Body = Body;
WorkloadByAssigneeCard.Header = Header;
WorkloadByAssigneeCard.Tabs = Tabs;

export default WorkloadByAssigneeCard;
