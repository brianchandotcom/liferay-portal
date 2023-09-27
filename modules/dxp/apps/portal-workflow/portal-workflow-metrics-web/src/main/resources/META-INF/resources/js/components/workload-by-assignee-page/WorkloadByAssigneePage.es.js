/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useMemo} from 'react';

import PromisesResolver from '../../shared/components/promises-resolver/PromisesResolver.es';
import {parse} from '../../shared/components/router/queryString.es';
import {useFilter} from '../../shared/hooks/useFilter.es';
import {usePost} from '../../shared/hooks/usePost.es';
import {useProcessTitle} from '../../shared/hooks/useProcessTitle.es';
import Body from './WorkloadByAssigneePageBody.es';
import Header from './WorkloadByAssigneePageHeader.es';

function WorkloadByAssigneePage({query, routeParams}) {
	const {processId, ...paginationParams} = routeParams;

	const {search = null} = parse(query);
	const filterKeys = ['processStep', 'roles'];

	useProcessTitle(processId, Liferay.Language.get('workload-by-assignee'));

	const {
		filterValues: {roleIds, taskNames},
		prefixedKeys,
		selectedFilters,
	} = useFilter({filterKeys});

	const {data, postData} = usePost({
		body: {
			keywords: search,
			roleIds,
			taskNames,
		},
		params: paginationParams,
		url: `/processes/${processId}/assignees/metrics`,
	});

	const promises = useMemo(
		() => [postData()],

		// eslint-disable-next-line react-hooks/exhaustive-deps
		[routeParams]
	);

	return (
		<PromisesResolver promises={promises}>
			<WorkloadByAssigneePage.Header
				filterKeys={prefixedKeys}
				routeParams={{...routeParams, search}}
				selectedFilters={selectedFilters}
				totalCount={data?.totalCount}
			/>

			<WorkloadByAssigneePage.Body
				{...data}
				filtered={search || !!selectedFilters.length}
				processId={processId}
				taskNames={taskNames}
			/>
		</PromisesResolver>
	);
}

WorkloadByAssigneePage.Body = Body;
WorkloadByAssigneePage.Header = Header;

export default WorkloadByAssigneePage;
