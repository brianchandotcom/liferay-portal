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

import React, {useContext} from 'react';
import ListHeadItem from '../../../shared/components/list/ListHeadItem.es';
import {formatDuration} from '../../../shared/util/duration.es';
import PaginationBar from '../../../shared/components/pagination/PaginationBar.es';
import {
	PerformanceDataContext,
	PerformanceDataProvider
} from './store/PerformanceByStepStore.es';
import Search from '../../../shared/components/pagination/Search.es';

function PerformanceByStep({page, pageSize, processId, search, sort}) {
	return (
		<PerformanceDataProvider
			page={page}
			pageSize={pageSize}
			processId={processId}
			search={search}
			sort={sort}
		>
			<PerformanceByStep.Body
				page={page}
				pageSize={pageSize}
				search={search}
			/>
		</PerformanceDataProvider>
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
									'avg-completion-time'
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

const Item = ({name, durationAvg, instanceCount, overdueInstanceCount}) => {
	const percent =
		instanceCount > 0 ? (overdueInstanceCount / instanceCount) * 100 : 0;

	return (
		<tr>
			<td className="lfr-title-column table-cell-expand table-cell-minw-200 table-title">
				{name}
			</td>

			<td className="text-right">
				{overdueInstanceCount} {`(${percent}%)`}
			</td>

			<td className="text-right">{formatDuration(durationAvg)}</td>
		</tr>
	);
};

PerformanceByStep.Table = Table;

PerformanceByStep.Item = Item;

PerformanceByStep.Body = Body;

export default PerformanceByStep;
