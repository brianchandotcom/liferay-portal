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

import FilterResultsBar from '../../shared/components/filter/FilterResultsBar.es';
import AssigneeFilter from '../process-metrics/filter/AssigneeFilter.es';
import ProcessStatusFilter from '../process-metrics/filter/ProcessStatusFilter.es';
import ProcessStepFilter from '../process-metrics/filter/ProcessStepFilter.es';
import SLAStatusFilter from '../process-metrics/filter/SLAStatusFilter.es';
import {TimeRangeFilter} from '../process-metrics/filter/TimeRangeFilter.es';
import {AssigneeContext} from '../process-metrics/filter/store/AssigneeStore.es';
import {ProcessStatusContext} from '../process-metrics/filter/store/ProcessStatusStore.es';
import {ProcessStepContext} from '../process-metrics/filter/store/ProcessStepStore.es';
import {SLAStatusContext} from '../process-metrics/filter/store/SLAStatusStore.es';
import {TimeRangeContext} from '../process-metrics/filter/store/TimeRangeStore.es';
import {filterConstants} from './store/InstanceListPageStore.es';

const InstanceListPageFilters = ({totalCount}) => {
	const {assignees} = useContext(AssigneeContext);
	const {isCompletedStatusSelected, processStatuses} = useContext(
		ProcessStatusContext
	);
	const {processSteps} = useContext(ProcessStepContext);
	const {slaStatuses} = useContext(SLAStatusContext);
	const {timeRanges} = useContext(TimeRangeContext);

	const completedStatusSelected = isCompletedStatusSelected();

	const getFilters = () => {
		const asFilterObject = (items, key, name, pinned = false) => ({
			items,
			key,
			name,
			pinned
		});

		const filters = [
			asFilterObject(
				slaStatuses,
				filterConstants.slaStatus,
				Liferay.Language.get('sla-status')
			),
			asFilterObject(
				processStatuses,
				filterConstants.processStatus,
				Liferay.Language.get('process-status')
			)
		];

		if (completedStatusSelected) {
			filters.push(
				asFilterObject(
					timeRanges,
					filterConstants.timeRange,
					Liferay.Language.get('completion-period'),
					true
				)
			);
		}

		filters.push(
			asFilterObject(
				processSteps,
				filterConstants.processStep,
				Liferay.Language.get('process-step')
			)
		);

		filters.push(
			asFilterObject(
				assignees,
				filterConstants.assignees,
				Liferay.Language.get('assignees')
			)
		);

		return filters;
	};

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

						<SLAStatusFilter />

						<ProcessStatusFilter />

						{completedStatusSelected && <TimeRangeFilter />}

						<ProcessStepFilter />

						<AssigneeFilter />
					</ul>
				</div>
			</nav>

			<FilterResultsBar filters={getFilters()} totalCount={totalCount} />
		</>
	);
};

export default InstanceListPageFilters;
