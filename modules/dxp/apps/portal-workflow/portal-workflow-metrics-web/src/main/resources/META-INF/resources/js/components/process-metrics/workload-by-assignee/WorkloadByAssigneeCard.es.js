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

import {Item, Table} from './WorkloadByAssigneeTable.es';
import {Body, Empty} from './WorkloadByAssigneeBody.es';
import {getFiltersParam} from '../../../shared/components/filter/util/filterUtil.es';
import Panel from '../../../shared/components/Panel.es';
import ProcessStepFilter from '../filter/ProcessStepFilter.es';
import {ProcessStepProvider} from '../filter/store/ProcessStepStore.es';
import React, {useState} from 'react';
import Request from '../../../shared/components/request/Request.es';
import Tabs from './WorkloadByAssigneeTabs.es';

const Header = () => (
	<>
		<Panel.HeaderWithOptions
			description={Liferay.Language.get(
				'workload-by-assignee-description'
			)}
			elementClasses="dashboard-panel-header"
			title={Liferay.Language.get('workload-by-assignee')}
			tooltipPosition="bottom"
		/>

		<Request.Success>
			<div className="ml-3 management-bar management-bar-light navbar pl-1">
				<ul className="navbar-nav">
					<ProcessStepFilter
						filterKey="assigneeTaskKeys"
						hideControl={true}
						multiple={false}
						showFilterName={false}
					/>
				</ul>
			</div>
		</Request.Success>
	</>
);

const WorkloadByAssigneeCard = ({processId, query}) => {
	const {assigneeTaskKeys = []} = getFiltersParam(query);
	const [currentTab, setCurrentTab] = useState('overdue');

	return (
		<Request>
			<ProcessStepProvider
				processId={processId}
				processStepKeys={assigneeTaskKeys}
				withAllSteps={true}
			>
				<Panel elementClasses="workload-by-assignee-card">
					<WorkloadByAssigneeCard.Header />

					<WorkloadByAssigneeCard.Tabs
						currentTab={currentTab}
						setCurrentTab={setCurrentTab}
					/>

					<Panel.Body>
						<Request.Error />

						<Request.Loading />

						<WorkloadByAssigneeCard.Body
							currentTab={currentTab}
							processId={processId}
							query={query}
						/>
					</Panel.Body>
				</Panel>
			</ProcessStepProvider>
		</Request>
	);
};

WorkloadByAssigneeCard.Body = Body;
WorkloadByAssigneeCard.Empty = Empty;
WorkloadByAssigneeCard.Header = Header;
WorkloadByAssigneeCard.Item = Item;
WorkloadByAssigneeCard.Table = Table;
WorkloadByAssigneeCard.Tabs = Tabs;

export default WorkloadByAssigneeCard;
