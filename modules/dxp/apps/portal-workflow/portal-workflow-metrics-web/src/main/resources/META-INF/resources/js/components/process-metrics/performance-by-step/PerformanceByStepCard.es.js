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

import {Item, Table} from './PerformanceByStepTable.es';
import {Body} from './PerformanceByStepBody.es';
import {getFiltersParam} from '../../../shared/components/filter/util/filterUtil.es';
import Panel from '../../../shared/components/Panel.es';
import React from 'react';
import Request from '../../../shared/components/request/Request.es';
import {TimeRangeFilter} from '../filter/TimeRangeFilter.es';
import {TimeRangeProvider} from '../filter/store/TimeRangeStore.es';

const Header = () => (
	<Panel.HeaderWithOptions
		description={Liferay.Language.get('performance-by-step-description')}
		elementClasses="dashboard-panel-header"
		title={Liferay.Language.get('performance-by-step')}
	>
		<Request.Success>
			<div className="autofit-col m-0 management-bar management-bar-light navbar">
				<ul className="navbar-nav">
					<TimeRangeFilter
						filterKey="performanceTimeRange"
						hideControl={true}
						position="right"
						showFilterName={false}
					/>
				</ul>
			</div>
		</Request.Success>
	</Panel.HeaderWithOptions>
);

const PerformanceByStepCard = ({processId, query}) => {
	const {performanceTimeRange = []} = getFiltersParam(query);

	return (
		<Request>
			<TimeRangeProvider timeRangeKeys={performanceTimeRange}>
				<Panel>
					<PerformanceByStepCard.Header />

					<Request.Error />

					<Request.Loading />

					<PerformanceByStepCard.Body processId={processId} />
				</Panel>
			</TimeRangeProvider>
		</Request>
	);
};

PerformanceByStepCard.Body = Body;
PerformanceByStepCard.Header = Header;
PerformanceByStepCard.Item = Item;
PerformanceByStepCard.Table = Table;

export default PerformanceByStepCard;
