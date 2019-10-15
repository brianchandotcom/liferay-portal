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
import {filterConstants} from './store/InstanceListStore.es';
import FilterResultsBar from '../../../shared/components/filter/FilterResultsBar.es';
import SLAStatusFilter from '../filter/SLAStatusFilter.es';
import {ProcessStatusContext} from '../filter/store/ProcessStatusStore.es';
import ProcessStatusFilter from '../filter/ProcessStatusFilter.es';
import {ProcessStepContext} from '../filter/store/ProcessStepStore.es';
import ProcessStepFilter from '../filter/ProcessStepFilter.es';
import {SLAStatusContext} from '../filter/store/SLAStatusStore.es';
import {TimeRangeContext} from '../filter/store/TimeRangeStore.es';
import {TimeRangeFilter} from '../filter/TimeRangeFilter.es';
import AssigneeFilter from '../filter/AssigneeFilter.es';
import {AssigneeContext} from '../filter/store/AssigneeStore.es';

const InstanceListFilters = ({totalCount}) => {
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

export default InstanceListFilters;
