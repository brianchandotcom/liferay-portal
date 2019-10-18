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

import {
	PerformanceDataContext,
	PerformanceDataProvider
} from './store/PerformanceByStepStore.es';
import React, {useContext} from 'react';
import {
	TimeRangeContext,
	TimeRangeProvider
} from '../filter/store/TimeRangeStore.es';
import {formatDuration} from '../../../shared/util/duration.es';

import ListHeadItem from '../../../shared/components/list/ListHeadItem.es';
import PaginationBar from '../../../shared/components/pagination/PaginationBar.es';
import Request from '../../../shared/components/request/Request.es';
import Search from '../../../shared/components/pagination/Search.es';
import {TimeRangeFilter} from '../filter/TimeRangeFilter.es';

function PerformanceByStep({page, pageSize, processId, search, sort}) {
	return (
		<Request>
			<TimeRangeProvider
				previousKeys={['30']}
				search={search}
				timeRangeKeys={[]}
			>
				<TimeRangeContext.Consumer>
					{({getSelectedTimeRange}) => (
						<PerformanceDataProvider
							page={page}
							pageSize={pageSize}
							processId={processId}
							search={search}
							sort={sort}
							timeRange={getSelectedTimeRange() || {}}
						>
							<Request.Error />

							<Request.Loading />

							<Request.Success>
								<PerformanceByStep.Body
									page={page}
									pageSize={pageSize}
								/>
							</Request.Success>
						</PerformanceDataProvider>
					)}
				</TimeRangeContext.Consumer>
			</TimeRangeProvider>
		</Request>
	);
}

const Body = ({page, pageSize}) => {
	const {items = [], totalCount} = useContext(PerformanceDataContext);

	return (
		<div>
			<nav className="management-bar management-bar-light navbar navbar-expand-md">
				<div className="container-fluid container-fluid-max-xl">
					<div className="navbar-form navbar-form-autofit">
						<Search disabled={false} />
					</div>

					<TimeRangeFilter
						filterKey="timeRange"
						hideControl={true}
						position="right"
						showFilterName={false}
					/>
				</div>
			</nav>

			<div
				className="container-fluid-1280 mt-4"
				data-testid="performance-test"
			>
				<PerformanceByStep.Table items={items} />

				<PaginationBar
					page={page}
					pageCount={items.length}
					pageSize={pageSize}
					totalCount={totalCount}
				/>
			</div>
		</div>
	);
};

const Table = ({items}) => {
	return (
		<div className="table-responsive">
			<table className="show-quick-actions-on-hover table table-autofit table-heading-nowrap table-hover table-list">
				<thead>
					<tr>
						<th className="table-cell-expand table-head-title">
							{Liferay.Language.get('step-name')}
						</th>

						<th className="table-head-title text-right">
							<ListHeadItem
								name="overdueInstanceCount"
								title={Liferay.Language.get(
									'sla-breached-percent'
								)}
							/>
						</th>

						<th className="table-head-title text-right">
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
							<PerformanceByStep.Item {...step} key={index} />
						))}
				</tbody>
			</table>
		</div>
	);
};

const Item = ({durationAvg, instanceCount, name, overdueInstanceCount}) => {
	const formattedDuration = formatDuration(durationAvg);
	const formattedPercentage = getFormattedPercentage(
		overdueInstanceCount,
		instanceCount
	);

	return (
		<tr>
			<td className="lfr-title-column table-cell-expand table-cell-minw-200 table-title">
				{name}
			</td>

			<td className="text-right">
				{overdueInstanceCount} {`(${formattedPercentage})`}
			</td>

			<td className="text-right">{formattedDuration}</td>
		</tr>
	);
};

PerformanceByStep.Table = Table;

PerformanceByStep.Item = Item;

PerformanceByStep.Body = Body;

export default PerformanceByStep;
