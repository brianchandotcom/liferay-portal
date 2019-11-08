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

import React, {useContext, useMemo, useEffect} from 'react';

import {
	getFiltersParam,
	getFilterValues
} from '../../shared/components/filter/util/filterUtil.es';
import EmptyState from '../../shared/components/list/EmptyState.es';
import ListHeadItem from '../../shared/components/list/ListHeadItem.es';
import ReloadButton from '../../shared/components/list/ReloadButton.es';
import LoadingState from '../../shared/components/loading/LoadingState.es';
import PaginationBar from '../../shared/components/pagination/PaginationBar.es';
import Search from '../../shared/components/pagination/Search.es';
import PromisesResolver from '../../shared/components/request/PromisesResolver.es';
import Request from '../../shared/components/request/Request.es';
import ResultsBar from '../../shared/components/results-bar/ResultsBar.es';
import {formatDuration} from '../../shared/util/duration.es';
import {getFormattedPercentage} from '../../shared/util/util.es';
import {AppContext} from '../AppContext.es';
import {TimeRangeFilter} from '../process-metrics/filter/TimeRangeFilter.es';
import {TimeRangeProvider} from '../process-metrics/filter/store/TimeRangeStore.es';
import {
	PerformanceDataContext,
	PerformanceDataProvider
} from './store/PerformanceByStepPageStore.es';

const PerformanceByStepPage = ({
	page,
	pageSize,
	processId,
	query,
	search,
	sort
}) => {
	const {client, setTitle} = useContext(AppContext);
	const filtersParam = getFiltersParam(query);
	const performanceTimeRange = getFilterValues(
		'performanceTimeRange',
		filtersParam
	);

	useEffect(() => {
		client.get(`/processes/${processId}/title`).then(({data}) => {
			setTitle(`${data}: ${Liferay.Language.get('performance-by-step')}`);
			return data;
		});
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return (
		<Request>
			<TimeRangeProvider
				search={search}
				timeRangeKeys={performanceTimeRange}
			>
				<PerformanceDataProvider
					queryDateEnd={filtersParam.dateEnd}
					queryDateStart={filtersParam.dateStart}
					timeRangeKeys={performanceTimeRange}
				>
					<PerformanceByStepPage.Body
						page={page}
						pageSize={pageSize}
						processId={processId}
						query={query}
						search={search}
						sort={sort}
					/>
				</PerformanceDataProvider>
			</TimeRangeProvider>
		</Request>
	);
};

const Body = ({page, pageSize, processId, query, search, sort}) => {
	const {fetchData, items = [], totalCount} = useContext(
		PerformanceDataContext
	);

	const promises = useMemo(
		() => [fetchData(page, pageSize, processId, search, sort, query)],
		// eslint-disable-next-line react-hooks/exhaustive-deps
		[page, pageSize, processId, search, sort, query]
	);

	const errorMessageText = Liferay.Language.get(
		'there-was-a-problem-retrieving-data-please-try-reloading-the-page'
	);

	const emptyMessageText = search
		? Liferay.Language.get('no-results-were-found')
		: Liferay.Language.get('there-is-no-data-at-the-moment');

	return (
		<div>
			<nav className="management-bar management-bar-light navbar navbar-expand-md">
				<div className="container-fluid container-fluid-max-xl">
					<div className="navbar-form navbar-form-autofit">
						<Search
							disabled={false}
							placeholder={Liferay.Language.get(
								'search-for-step-name'
							)}
						/>
					</div>

					<TimeRangeFilter
						buttonClassName="btn-flat btn-sm"
						filterKey="performanceTimeRange"
						hideControl={true}
						position="right"
						showFilterName={false}
					/>
				</div>
			</nav>

			{search && (
				<ResultsBar>
					<>
						<ResultsBar.TotalCount
							search={search}
							totalCount={totalCount}
						/>

						<ResultsBar.Clear
							page={page}
							pageSize={pageSize}
							processId={processId}
							sort={sort}
						/>
					</>
				</ResultsBar>
			)}

			<div
				className="container-fluid-1280 mt-4 workflow-process-dashboard"
				data-testid="performanceByStepBody"
			>
				<PromisesResolver promises={promises}>
					<PromisesResolver.Pending>
						<div className={`border-1 pb-6 pt-6 sheet`}>
							<LoadingState />
						</div>
					</PromisesResolver.Pending>

					<PromisesResolver.Resolved>
						{items && items.length ? (
							<>
								<PerformanceByStepPage.Table items={items} />

								<PaginationBar
									page={page}
									pageCount={items.length}
									pageSize={pageSize}
									totalCount={totalCount}
								/>
							</>
						) : (
							<EmptyState
								className="border-1"
								hideAnimation={false}
								message={emptyMessageText}
								type="not-found"
							/>
						)}
					</PromisesResolver.Resolved>

					<PromisesResolver.Rejected>
						<EmptyState
							actionButton={<ReloadButton />}
							className="border-1"
							hideAnimation={true}
							message={errorMessageText}
							messageClassName="small"
							type="error"
						/>
					</PromisesResolver.Rejected>
				</PromisesResolver>
			</div>
		</div>
	);
};

const Item = ({
	breachedInstanceCount,
	breachedInstancePercentage,
	durationAvg,
	name
}) => {
	const formattedDuration = formatDuration(durationAvg);
	const formattedPercentage = getFormattedPercentage(
		breachedInstancePercentage,
		100
	);

	return (
		<tr>
			<td
				className="lfr-title-column table-cell-expand table-title"
				data-testid="stepName"
			>
				{name}
			</td>

			<td className="text-right">
				{breachedInstanceCount} ({formattedPercentage})
			</td>

			<td className="text-right">{formattedDuration}</td>
		</tr>
	);
};

const Table = ({items}) => {
	return (
		<div className="mb-3 table-responsive">
			<table className="table table-autofit table-heading-nowrap table-hover table-list">
				<thead>
					<tr>
						<th
							className="table-cell-expand"
							style={{width: '60%'}}
						>
							{Liferay.Language.get('step-name')}
						</th>

						<th className="text-right" style={{width: '20%'}}>
							<ListHeadItem
								name="breachedInstancePercentage"
								title={Liferay.Language.get(
									'sla-breached-percent'
								)}
							/>
						</th>

						<th
							className="table-head-title text-right"
							style={{width: '20%'}}
						>
							<ListHeadItem
								name="durationAvg"
								title={Liferay.Language.get(
									'average-completion-time'
								)}
							/>
						</th>
					</tr>
				</thead>

				<tbody>
					{items &&
						items.map((step, index) => (
							<PerformanceByStepPage.Item {...step} key={index} />
						))}
				</tbody>
			</table>
		</div>
	);
};

PerformanceByStepPage.Body = Body;
PerformanceByStepPage.Item = Item;
PerformanceByStepPage.Table = Table;

export default PerformanceByStepPage;
