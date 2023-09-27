/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	const promises = useMemo(
		() => [fetchData()],

		// eslint-disable-next-line react-hooks/exhaustive-deps
		[routeParams]
	);

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
