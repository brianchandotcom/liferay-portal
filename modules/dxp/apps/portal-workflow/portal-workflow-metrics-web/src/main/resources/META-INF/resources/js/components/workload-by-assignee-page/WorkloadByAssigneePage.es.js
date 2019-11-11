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

import FilterResultsBar from '../../shared/components/filter/FilterResultsBar.es';
import {getFilterResults} from '../../shared/components/filter/util/filterUtil.es';
import PromisesResolver from '../../shared/components/request/PromisesResolver.es';
import {useFilterItemKeys} from '../../shared/hooks/useFilterItemKeys.es';
import {useFiltersReducer} from '../../shared/hooks/useFiltersReducer.es';
import {useProcessTitle} from '../../shared/hooks/useProcessTitle.es';
import {useResource} from '../../shared/hooks/useResource.es';
import ProcessStepFilter from '../process-metrics/filter/ProcessStepFilterHooks.es';
import RoleFilter from '../process-metrics/filter/RoleFilterHooks.es';
import {
	Body,
	EmptyView,
	ErrorView,
	LoadingView
} from './WorkloadByAssigneePageBody.es';
import {Item, Table} from './WorkloadByAssigneePageTable.es';

const filterKeys = {
	processSteps: 'taskKeys',
	roles: 'roleIds'
};

const filterTitles = {
	processSteps: Liferay.Language.get('process-step'),
	roles: Liferay.Language.get('roles')
};

const WorkloadByAssigneePage = ({page, pageSize, processId, sort}) => {
	useProcessTitle(processId, Liferay.Language.get('workload-by-assignee'));

	const [filterValues, dispatch] = useFiltersReducer(filterKeys);
	const {roleIds, taskKeys} = useFilterItemKeys(filterKeys, filterValues);

	const {data, promises} = useResource(
		`/processes/${processId}/assignee-users`,
		{
			page,
			pageSize,
			roleIds,
			sort: decodeURIComponent(sort),
			taskKeys
		}
	);

	return (
		<PromisesResolver promises={promises}>
			<WorkloadByAssigneePage.Filters
				dispatch={dispatch}
				filterValues={filterValues}
				processId={processId}
				totalCount={data.totalCount}
			/>

			<div className="container-fluid-1280 mt-4">
				<WorkloadByAssigneePage.Body
					data={data}
					processId={processId}
					taskKeys={taskKeys}
				/>
			</div>
		</PromisesResolver>
	);
};

const Filters = ({dispatch, filterValues, processId, totalCount}) => {
	const filterResults = getFilterResults(
		filterKeys,
		filterTitles,
		filterValues
	);

	return (
		<>
			<nav className="management-bar management-bar-light navbar navbar-expand-md">
				<div className="container-fluid container-fluid-max-xl">
					<ul className="navbar-nav">
						<li className="nav-item">
							<strong className="ml-0 mr-0 navbar-text">
								{Liferay.Language.get('filter-by')}
							</strong>
						</li>

						<RoleFilter
							dispatch={dispatch}
							filterKey={filterKeys.roles}
							processId={processId}
						/>

						<ProcessStepFilter
							dispatch={dispatch}
							filterKey={filterKeys.processSteps}
							processId={processId}
						/>
					</ul>
				</div>
			</nav>

			<FilterResultsBar filters={filterResults} totalCount={totalCount} />
		</>
	);
};

WorkloadByAssigneePage.Body = Body;
WorkloadByAssigneePage.Empty = EmptyView;
WorkloadByAssigneePage.Error = ErrorView;
WorkloadByAssigneePage.Filters = Filters;
WorkloadByAssigneePage.Item = Item;
WorkloadByAssigneePage.Loading = LoadingView;
WorkloadByAssigneePage.Table = Table;

export default WorkloadByAssigneePage;
