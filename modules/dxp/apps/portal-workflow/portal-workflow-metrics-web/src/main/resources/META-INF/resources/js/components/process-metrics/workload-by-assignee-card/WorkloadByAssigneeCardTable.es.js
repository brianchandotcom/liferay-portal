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

import {ChildLink} from '../../../shared/components/router/routerWrapper.es';
import {getFormattedPercentage} from '../../../shared/util/util.es';
import {AppContext} from '../../AppContext.es';
import {filterConstants} from '../../instance-list-page/store/InstanceListPageStore.es';
import {processStatusConstants} from '../filter/store/ProcessStatusStore.es';
import {slaStatusConstants} from '../filter/store/SLAStatusStore.es';
import WorkloadByAssigneeCard from './WorkloadByAssigneeCard.es';

const Item = ({
	currentTab,
	id,
	name,
	onTimeTaskCount,
	overdueTaskCount,
	processId,
	processStepKey,
	taskCount
}) => {
	const currentCount =
		currentTab === 'overdue'
			? overdueTaskCount
			: currentTab === 'onTime'
			? onTimeTaskCount
			: taskCount;
	const {defaultDelta} = useContext(AppContext);

	const formattedPercentage = getFormattedPercentage(currentCount, taskCount);

	const getFiltersQuery = () => {
		const filterParams = {
			[filterConstants.assignees]: [id],
			[filterConstants.processStatus]: [processStatusConstants.pending],
			[filterConstants.slaStatus]: [slaStatusConstants[currentTab]]
		};

		if (processStepKey && processStepKey !== 'allSteps') {
			filterParams[filterConstants.processStep] = [processStepKey];
		}

		return filterParams;
	};

	const instancesListPath = `/instances/${processId}/${defaultDelta}/1`;

	return (
		<tr>
			<td
				className="assignee-name border-0"
				data-testid="workloadByAssigneeCardItem"
			>
				<ChildLink
					className={'workload-by-assignee-link'}
					query={{filters: getFiltersQuery()}}
					to={instancesListPath}
				>
					<span data-testid="assigneeName">{name}</span>
				</ChildLink>
			</td>

			<td className="border-0 text-right" data-testid="taskCount">
				<span className="task-count-value" data-testid="taskCountValue">
					{currentCount}
				</span>

				{currentTab !== 'total' && (
					<span
						className="task-count-percentage"
						data-testid="taskCountPercentage"
					>
						{' / '}

						{formattedPercentage}
					</span>
				)}
			</td>
		</tr>
	);
};

const Table = ({currentTab, items = [], processId, processStepKey}) => (
	<div className="mb-3 table-fit-panel">
		<table className="table table-autofit table-hover">
			<tbody>
				{items.map((item, index) => (
					<WorkloadByAssigneeCard.Item
						{...item}
						currentTab={currentTab}
						key={index}
						processId={processId}
						processStepKey={processStepKey}
					/>
				))}
			</tbody>
		</table>
	</div>
);

export {Item, Table};
