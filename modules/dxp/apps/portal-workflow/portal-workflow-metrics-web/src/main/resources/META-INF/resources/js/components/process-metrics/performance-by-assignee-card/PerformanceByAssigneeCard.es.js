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

import React from 'react';

import Panel from '../../../shared/components/Panel.es';
import {getFiltersParam} from '../../../shared/components/filter/util/filterUtil.es';
import PromisesResolver from '../../../shared/components/request/PromisesResolver.es';
import Request from '../../../shared/components/request/Request.es';
import {ProcessStepProvider} from '../filter/store/ProcessStepStore.es';
import {TimeRangeProvider} from '../filter/store/TimeRangeStore.es';
import {Body, Footer} from './PerformanceByAssigneeCardBody.es';
import {Container} from './PerformanceByAssigneeCardContainer.es';
import {Filter} from './PerformanceByAssigneeCardFilter.es';
import {Item, Table} from './PerformanceByAssigneeCardTable.es';

const Header = ({processId, query}) => {
	return (
		<Panel.HeaderWithOptions
			description={Liferay.Language.get(
				'performance-by-assignee-description'
			)}
			elementClasses="dashboard-panel-header"
			title={Liferay.Language.get('performance-by-assignee')}
		>
			<PromisesResolver.Resolved>
				<PerformanceByAssigneeCard.Filter
					processId={processId}
					query={query}
				></PerformanceByAssigneeCard.Filter>
			</PromisesResolver.Resolved>
		</Panel.HeaderWithOptions>
	);
};

const PerformanceByAssigneeCard = ({processId, query}) => {
	const {assigneeProcessStep = [], assigneeTimeRange = []} = getFiltersParam(
		query
	);

	return (
		<Panel>
			<Request>
				<ProcessStepProvider
					processId={processId}
					processStepKeys={assigneeProcessStep}
					withAllSteps={true}
				>
					<TimeRangeProvider timeRangeKeys={assigneeTimeRange}>
						<PerformanceByAssigneeCard.Container
							processId={processId}
							query={query}
						/>
					</TimeRangeProvider>
				</ProcessStepProvider>
			</Request>
		</Panel>
	);
};

PerformanceByAssigneeCard.Body = Body;
PerformanceByAssigneeCard.Container = Container;
PerformanceByAssigneeCard.Filter = Filter;
PerformanceByAssigneeCard.Footer = Footer;
PerformanceByAssigneeCard.Header = Header;
PerformanceByAssigneeCard.Item = Item;
PerformanceByAssigneeCard.Table = Table;

export default PerformanceByAssigneeCard;
