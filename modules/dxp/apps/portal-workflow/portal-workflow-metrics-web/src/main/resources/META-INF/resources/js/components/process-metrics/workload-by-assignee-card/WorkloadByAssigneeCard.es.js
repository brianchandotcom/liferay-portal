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
import Tabs from '../../../shared/components/tabs/Tabs.es';
import {useFetch} from '../../../shared/hooks/useFetch.es';
import {useFilter} from '../../../shared/hooks/useFilter.es';
import ProcessStepFilter from '../../filter/ProcessStepFilter.es';
import {Body} from './WorkloadByAssigneeCardBody.es';

const Header = ({processId}) => (
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
					options={{
						hideControl: true,
						multiple: false,
						withAllSteps: true,
						withSelectionTitle: true,
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
		filterValues: {taskKeys: [taskKey] = []},
	} = useFilter({filterKeys});

	const params = getParams(currentTab, taskKey);

	const {data, fetchData} = useFetch({
		params,
		url: `/processes/${processId}/assignee-users`,
	});

	const promises = useMemo(() => [fetchData()], [fetchData]);

	const tabs = [
		{name: Liferay.Language.get('overdue'), tabKey: 'overdue'},
		{name: Liferay.Language.get('on-time'), tabKey: 'onTime'},
		{name: Liferay.Language.get('total'), tabKey: 'total'},
	];

	return (
		<PromisesResolver promises={promises}>
			<Panel elementClasses="workload-by-assignee-card">
				<WorkloadByAssigneeCard.Header processId={processId} />

				<div className="border-bottom">
					<Tabs
						currentTab={currentTab}
						setCurrentTab={setCurrentTab}
						tabs={tabs}
					/>
				</div>

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

const getParams = (currentTab, taskKey) => {
	const params = {
		page: 1,
		pageSize: 10,
		taskKeys: taskKey !== 'allSteps' ? taskKey : undefined,
	};

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

export default WorkloadByAssigneeCard;
