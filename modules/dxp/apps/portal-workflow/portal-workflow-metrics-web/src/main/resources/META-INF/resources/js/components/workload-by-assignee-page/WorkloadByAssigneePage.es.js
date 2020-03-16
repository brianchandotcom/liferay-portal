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
import {useFilter} from '../../shared/hooks/useFilter.es';
import {usePost} from '../../shared/hooks/usePost.es';
import {useProcessTitle} from '../../shared/hooks/useProcessTitle.es';
import {Body} from './WorkloadByAssigneePageBody.es';
import {Header} from './WorkloadByAssigneePageHeader.es';

const WorkloadByAssigneePage = ({query, routeParams}) => {
	const {processId, ...paginationParams} = routeParams;

	const {search = null} = parse(query);
	const filterKeys = ['processStep', 'roles'];

	useProcessTitle(processId, Liferay.Language.get('workload-by-assignee'));

	const {
		filterValues: {roleIds, taskKeys},
		prefixedKeys,
		selectedFilters,
	} = useFilter({filterKeys});

	const filtered = search || selectedFilters.length > 0;

	const {data, postData} = usePost({
		body: {
			keywords: search,
			roleIds,
			taskKeys,
		},
		params: paginationParams,
		url: `/processes/${processId}/assignee-users`,
	});

	const promises = useMemo(() => [postData()], [postData]);

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
