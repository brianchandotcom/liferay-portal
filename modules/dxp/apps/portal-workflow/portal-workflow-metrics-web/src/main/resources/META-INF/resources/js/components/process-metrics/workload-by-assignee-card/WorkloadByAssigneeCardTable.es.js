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

import React, {useContext, useMemo} from 'react';

import filterConstants from '../../../shared/components/filter/util/filterConstants.es';
import ChildLink from '../../../shared/components/router/ChildLink.es';
import {getFormattedPercentage} from '../../../shared/util/util.es';
import {AppContext} from '../../AppContext.es';
import {processStatusConstants} from '../../filter/ProcessStatusFilter.es';
import {slaStatusConstants} from '../../filter/SLAStatusFilter.es';

const Item = ({
	assignee: {id, name},
	currentTab,
	onTimeTaskCount,
	overdueTaskCount,
	processId,
	processStepKey,
	taskCount,
}) => {
	const {defaultDelta} = useContext(AppContext);

	const counts = useMemo(
		() => ({
			onTime: onTimeTaskCount,
			overdue: overdueTaskCount,
			total: taskCount,
		}),
		[onTimeTaskCount, overdueTaskCount, taskCount]
	);

	const filters = useMemo(
		() => ({
			[filterConstants.assignee.key]: [id],
			[filterConstants.processStatus.key]: [
				processStatusConstants.pending,
			],
			[filterConstants.processStep.key]: [processStepKey],
			[filterConstants.slaStatus.key]: [slaStatusConstants[currentTab]],
		}),
		[currentTab, id, processStepKey]
	);

	const formattedPercentage = useMemo(
		() => getFormattedPercentage(counts[currentTab], taskCount),
		[counts, currentTab, taskCount]
	);

	const instancesListPath = useMemo(
		() => `/instance/${processId}/${defaultDelta}/1`,
		[defaultDelta, processId]
	);

	return (
		<tr>
			<td
				className="assignee-name border-0"
				data-testid="workloadByAssigneeCardItem"
			>
				<ChildLink
					className={'workload-by-assignee-link'}
					query={{filters}}
					to={instancesListPath}
				>
					<span data-testid="assigneeName">{name}</span>
				</ChildLink>
			</td>

			<td className="border-0 text-right" data-testid="taskCount">
				<span className="task-count-value" data-testid="taskCountValue">
					{counts[currentTab]}
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
					<Table.Item
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

Table.Item = Item;

export {Table};
