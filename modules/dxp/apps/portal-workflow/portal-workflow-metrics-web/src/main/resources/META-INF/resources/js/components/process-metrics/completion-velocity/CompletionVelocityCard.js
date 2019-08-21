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
