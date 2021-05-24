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

import ClayPanel from '@clayui/panel';
import React, {useMemo, useState} from 'react';

import PanelHeaderWithOptions from '../../../shared/components/panel-header-with-options/PanelHeaderWithOptions.es';
import PromisesResolver from '../../../shared/components/promises-resolver/PromisesResolver.es';
import Tabs from '../../../shared/components/tabs/Tabs.es';
import {useFilter} from '../../../shared/hooks/useFilter.es';
import {usePost} from '../../../shared/hooks/usePost.es';
import ProcessStepFilter from '../../filter/ProcessStepFilter.es';
import Body from './WorkloadByAssigneeCardBody.es';

function Header({processId}) {
	return (
		<>
			<PanelHeaderWithOptions
				className="tabs-panel-header"
				description={Liferay.Language.get(
					'workload-by-assignee-description'
				)}
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
}

function WorkloadByAssigneeCard({routeParams}) {
	const {processId} = routeParams;
	const [currentTab, setCurrentTab] = useState('overdue');
	const filterKeys = ['processStep'];

	const {
		filterValues: {taskNames: [taskName] = ['allSteps']},
	} = useFilter({filterKeys});

	const sortItems = {
		onTime: 'onTimeTaskCount:desc',
		overdue: 'overdueTaskCount:desc',
		total: 'taskCount:desc',
	};

	const sort = sortItems[currentTab];

	const taskNames = taskName !== 'allSteps' ? [taskName] : undefined;

	const {data, postData} = usePost({
		body: {taskNames},
		params: {
			page: 1,
			pageSize: 10,
			sort,
		},
		url: `/processes/${processId}/assignees/metrics`,
	});

	const promises = useMemo(() => [postData()], [postData]);

	const tabs = [
		{name: Liferay.Language.get('overdue'), tabKey: 'overdue'},
		{name: Liferay.Language.get('on-time'), tabKey: 'onTime'},
		{name: Liferay.Language.get('total'), tabKey: 'total'},
	];

	return (
		<PromisesResolver promises={promises}>
			<ClayPanel className="mt-4 workload-by-assignee-card">
				<WorkloadByAssigneeCard.Header processId={processId} />

				<div className="border-bottom container-nav">
					<Tabs
						currentTab={currentTab}
						setCurrentTab={setCurrentTab}
						tabs={tabs}
					/>
				</div>

				<WorkloadByAssigneeCard.Body
					currentTab={currentTab}
					{...data}
					processStepKey={taskNames && taskName}
					{...routeParams}
				/>
			</ClayPanel>
		</PromisesResolver>
	);
}

WorkloadByAssigneeCard.Body = Body;
WorkloadByAssigneeCard.Header = Header;

export default WorkloadByAssigneeCard;
