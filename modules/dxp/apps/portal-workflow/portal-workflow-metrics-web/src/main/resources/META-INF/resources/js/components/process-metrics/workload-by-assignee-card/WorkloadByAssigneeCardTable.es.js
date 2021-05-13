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

import filterConstants from '../../../shared/components/filter/util/filterConstants.es';
import ChildLink from '../../../shared/components/router/ChildLink.es';
import {getFormattedPercentage} from '../../../shared/util/util.es';
import {AppContext} from '../../AppContext.es';
import {processStatusConstants} from '../../filter/ProcessStatusFilter.es';
import {slaStatusConstants} from '../../filter/SLAStatusFilter.es';

function Item({
	assignee: {id, name},
	currentTab,
	onTimeTaskCount,
	overdueTaskCount,
	processId,
	processStepKey,
	taskCount,
}) {
	const {defaultDelta} = useContext(AppContext);

	const counts = {
		onTime: onTimeTaskCount,
		overdue: overdueTaskCount,
		total: taskCount,
	};

	const filters = {
		[filterConstants.assignee.key]: [id],
		[filterConstants.processStatus.key]: [processStatusConstants.pending],
		[filterConstants.processStep.key]: [processStepKey],
		[filterConstants.slaStatus.key]: [slaStatusConstants[currentTab]],
	};

	const formattedPercentage = getFormattedPercentage(
		counts[currentTab],
		taskCount
	);

	return (
		<tr>
			<td className="assignee-name border-0">
				<ChildLink
					className="workload-by-assignee-link"
					query={{filters}}
					to={`/instance/${processId}/${defaultDelta}/1/dateCreated:asc`}
				>
					<span>{name}</span>
				</ChildLink>
			</td>

			<td className="border-0 text-right">
				<span className="task-count-value">{counts[currentTab]}</span>

				{currentTab !== 'total' && (
					<span className="task-count-percentage">
						{' / '}

						{formattedPercentage}
					</span>
				)}
			</td>
		</tr>
	);
}

function Table({currentTab, items = [], processId, processStepKey}) {
	return (
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
}

Table.Item = Item;

export default Table;
