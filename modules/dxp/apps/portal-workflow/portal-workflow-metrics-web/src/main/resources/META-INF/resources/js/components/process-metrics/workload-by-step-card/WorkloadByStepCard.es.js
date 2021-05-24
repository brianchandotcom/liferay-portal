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
import React, {useMemo} from 'react';

import PanelHeaderWithOptions from '../../../shared/components/panel-header-with-options/PanelHeaderWithOptions.es';
import PromisesResolver from '../../../shared/components/promises-resolver/PromisesResolver.es';
import {useFetch} from '../../../shared/hooks/useFetch.es';
import Body from './WorkloadByStepCardBody.es';

function WorkloadByStepCard({processId, routeParams}) {
	const {data, fetchData} = useFetch({
		params: routeParams,
		url: `/processes/${processId}/nodes/metrics`,
	});

	const promises = useMemo(() => [fetchData()], [fetchData]);

	return (
		<PromisesResolver promises={promises}>
			<ClayPanel className="mt-4">
				<PanelHeaderWithOptions
					className="tabs-panel-header"
					description={Liferay.Language.get(
						'workload-by-step-description'
					)}
					title={Liferay.Language.get('workload-by-step')}
					tooltipPosition="bottom"
				/>

				<WorkloadByStepCard.Body {...data} {...routeParams} />
			</ClayPanel>
		</PromisesResolver>
	);
}

WorkloadByStepCard.Body = Body;

export default WorkloadByStepCard;
