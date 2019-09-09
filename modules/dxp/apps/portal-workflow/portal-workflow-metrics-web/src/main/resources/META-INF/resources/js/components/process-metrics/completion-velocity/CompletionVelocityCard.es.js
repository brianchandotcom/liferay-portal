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

import {getFiltersParam} from '../../../shared/components/filter/util/filterUtil';
import Panel from '../../../shared/components/Panel';
import {ProcessVelocityInfo} from './ProcessVelocityInfo';
import React from 'react';
import Request from '../../../shared/components/request/Request';
import {VelocityDataProvider} from './store/VelocityDataStore';
import {VelocityFiltersProvider} from './store/VelocityFiltersStore';
import VelocityFilters from './VelocityFilters';
import VelocityChart from './VelocityChart';

const CompletionVelocityCard = ({processId, query}) => {
	const {velocityTimeRange = [], velocityUnit = []} = getFiltersParam(query);

	return (
		<Request>
			<VelocityFiltersProvider
				timeRangeKeys={velocityTimeRange}
				velocityUnitKeys={velocityUnit}
			>
				<VelocityDataProvider processId={processId}>
					<Panel>
						<Panel.HeaderWithOptions
							description={Liferay.Language.get(
								'completion-velocity-description'
							)}
							elementClasses="dashboard-panel-header pb-0"
							title={Liferay.Language.get('completion-velocity')}
						>
							<Request.Success>
								<VelocityFilters />
							</Request.Success>
						</Panel.HeaderWithOptions>

						<Request.Error />

						<Request.Loading />

						<Request.Success>
							<Panel.Body elementClasses="pt-0">
								<ProcessVelocityInfo />

								<VelocityChart />
							</Panel.Body>
						</Request.Success>
					</Panel>
				</VelocityDataProvider>
			</VelocityFiltersProvider>
		</Request>
	);
};

export default CompletionVelocityCard;
