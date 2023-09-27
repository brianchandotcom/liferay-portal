/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayTable from '@clayui/table';
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
		<ClayTable.Row>
			<ClayTable.Cell className="assignee-name border-0">
				<ChildLink
					className="workload-by-assignee-link"
					query={{filters}}
					to={`/instance/${processId}/${defaultDelta}/1/dateOverdue:asc`}
				>
					<span>{name}</span>
				</ChildLink>
			</ClayTable.Cell>

			<ClayTable.Cell className="border-0 text-right">
				<span className="task-count-value">{counts[currentTab]}</span>

				{currentTab !== 'total' && (
					<span className="task-count-percentage">
						{' / '}

						{formattedPercentage}
					</span>
				)}
			</ClayTable.Cell>
		</ClayTable.Row>
	);
}

function Table({currentTab, items = [], processId, processStepKey}) {
	return (
		<div className="mb-3 table-fit-panel">
			<ClayTable borderless>
				<ClayTable.Body>
					{items.map((item, index) => (
						<Table.Item
							{...item}
							currentTab={currentTab}
							key={index}
							processId={processId}
							processStepKey={processStepKey}
						/>
					))}
				</ClayTable.Body>
			</ClayTable>
		</div>
	);
}

Table.Item = Item;

export default Table;
