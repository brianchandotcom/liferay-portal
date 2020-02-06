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

import PromisesResolver from '../../shared/components/promises-resolver/PromisesResolver.es';
import {parse} from '../../shared/components/router/queryString.es';
import {useFetch} from '../../shared/hooks/useFetch.es';
import {useFilter} from '../../shared/hooks/useFilter.es';
import {useProcessTitle} from '../../shared/hooks/useProcessTitle.es';
import {Body} from './WorkloadByAssigneePageBody.es';
import {Header} from './WorkloadByAssigneePageHeader.es';

const WorkloadByAssigneePage = ({query, routeParams}) => {
	const {processId} = routeParams;
	useProcessTitle(processId, Liferay.Language.get('workload-by-assignee'));

	const {search = null} = parse(query);

	const filterKeys = ['processStep', 'roles'];

	const {
		filterValues: {roleIds, taskKeys},
		prefixedKeys,
		selectedFilters
	} = useFilter({filterKeys});

	const filtered = search || selectedFilters.length > 0;

	const {data, fetchData} = useFetch({
		params: {
			keywords: search,
			roleIds,
			taskKeys,
			...routeParams
		},
		url: `/processes/${processId}/assignee-users`
	});

	const promises = useMemo(() => [fetchData()], [fetchData]);

	return (
		<PromisesResolver promises={promises}>
			<WorkloadByAssigneePage.Header
				filterKeys={prefixedKeys}
				routeParams={{...routeParams, search}}
				selectedFilters={selectedFilters}
				totalCount={data.totalCount}
			/>

			<WorkloadByAssigneePage.Body
				data={data}
				filtered={filtered}
				processId={processId}
				taskKeys={taskKeys}
			/>
		</PromisesResolver>
	);
};

WorkloadByAssigneePage.Body = Body;
WorkloadByAssigneePage.Header = Header;

export default WorkloadByAssigneePage;
