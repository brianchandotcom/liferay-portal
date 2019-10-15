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
import WorkloadByAssigneeCard from './WorkloadByAssigneeCard.es';
import {getPercentage} from '../../../shared/util/util.es';
import {formatNumber} from '../../../shared/util/numeral.es';

const Item = ({
	currentTab,
	name,
	onTimeTaskCount,
	overdueTaskCount,
	taskCount
}) => {
	const currentCount =
		currentTab === 'overdue'
			? overdueTaskCount
			: currentTab === 'onTime'
			? onTimeTaskCount
			: taskCount;

	const getFormattedPercentage = () => {
		const percentage = getPercentage(currentCount, taskCount);

		return formatNumber(percentage, '0[.]00%');
	};

	return (
		<tr>
			<td className="border-0 assignee-name" data-testid="assigneeName">
				{name}
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

						{getFormattedPercentage()}
					</span>
				)}
			</td>
		</tr>
	);
};

const Table = ({currentTab, items = []}) => (
	<div className="mb-3 table-fit-panel">
		<table className="table table-autofit table-hover">
			<tbody>
				{items.map((item, index) => (
					<WorkloadByAssigneeCard.Item
						{...item}
						currentTab={currentTab}
						key={index}
					/>
				))}
			</tbody>
		</table>
	</div>
);

export {Item, Table};
