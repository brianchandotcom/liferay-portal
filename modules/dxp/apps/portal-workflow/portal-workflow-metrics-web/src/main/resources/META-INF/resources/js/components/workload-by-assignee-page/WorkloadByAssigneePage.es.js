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

import {getFilterResults} from '../../shared/components/filter/util/filterUtil.es';
import Search from '../../shared/components/pagination/Search.es';
import PromisesResolver from '../../shared/components/request/PromisesResolver.es';
import ResultsBar from '../../shared/components/results-bar/ResultsBar.es';
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

const Filters = ({
	dispatch,
	filterValues,
	page,
	pageSize,
	processId,
	search,
	sort,
	totalCount
}) => {
	const filterResults = getFilterResults(
		filterKeys,
		filterTitles,
		filterValues
	);

	const routerParams = {page, pageSize, processId, sort};

	const selectedFilters = filterResults.filter(filter => {
		filter.items = filter.items
			? filter.items.filter(item => item.active)
			: [];

		return !!filter.items.length;
	});

	const showFiltersResult = search || !!selectedFilters.length;

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

					<div className="nav-form navbar-form-autofit">
						<Search
							disabled={false}
							placeholder={Liferay.Language.get(
								'search-for-assignee-name'
							)}
						/>
					</div>
				</div>
			</nav>

			{showFiltersResult && (
				<ResultsBar>
					<ResultsBar.TotalCount
						search={search}
						totalCount={totalCount}
					/>

					<ResultsBar.FilterItems
						filters={selectedFilters}
						search={search}
						{...routerParams}
					/>

					<ResultsBar.Clear
						filters={selectedFilters}
						{...routerParams}
					/>
				</ResultsBar>
			)}
		</>
	);
};

const WorkloadByAssigneePage = ({page, pageSize, processId, search, sort}) => {
	let keywords;
	useProcessTitle(processId, Liferay.Language.get('workload-by-assignee'));

	const [filterValues, dispatch] = useFiltersReducer(filterKeys);
	const {roleIds, taskKeys} = useFilterItemKeys(filterKeys, filterValues);

	if (typeof search === 'string' && search) {
		keywords = decodeURIComponent(search);
	}

	const {data, promises} = useResource(
		`/processes/${processId}/assignee-users`,
		{
			keywords,
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
				page={page}
				pageSize={pageSize}
				processId={processId}
				search={search}
				sort={sort}
				totalCount={data.totalCount}
			/>

			<div className="container-fluid-1280 mt-4">
				<WorkloadByAssigneePage.Body
					data={data}
					processId={processId}
				/>
			</div>
		</PromisesResolver>
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
