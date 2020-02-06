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

import React, {useMemo} from 'react';

import Panel from '../../../shared/components/Panel.es';
import PromisesResolver from '../../../shared/components/promises-resolver/PromisesResolver.es';
import {useFetch} from '../../../shared/hooks/useFetch.es';
import {Body} from './WorkloadByStepCardBody.es';

const WorkloadByStepCard = ({processId, routeParams}) => {
	const {data, fetchData} = useFetch({
		params: routeParams,
		url: `/processes/${processId}/tasks`
	});

	const promises = useMemo(() => [fetchData()], [fetchData]);

	return (
		<PromisesResolver promises={promises}>
			<Panel className="container-fluid-1280 mt-4">
				<Panel.HeaderWithOptions
					description={Liferay.Language.get(
						'workload-by-step-description'
					)}
					elementClasses="dashboard-panel-header"
					title={Liferay.Language.get('workload-by-step')}
					tooltipPosition="bottom"
				/>

				<WorkloadByStepCard.Body {...data} {...routeParams} />
			</Panel>
		</PromisesResolver>
	);
};

WorkloadByStepCard.Body = Body;

export default WorkloadByStepCard;
