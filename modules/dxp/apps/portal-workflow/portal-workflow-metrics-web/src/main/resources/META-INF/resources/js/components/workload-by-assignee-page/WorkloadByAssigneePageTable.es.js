/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayTable from '@clayui/table';
import React, {useContext} from 'react';

import filterConstants from '../../shared/components/filter/util/filterConstants.es';
import ListHeadItem from '../../shared/components/list/ListHeadItem.es';
import ChildLink from '../../shared/components/router/ChildLink.es';
import UserAvatar from '../../shared/components/user-avatar/UserAvatar.es';
import {AppContext} from '../AppContext.es';
import {processStatusConstants} from '../filter/ProcessStatusFilter.es';
import {slaStatusConstants} from '../filter/SLAStatusFilter.es';

function Item({
	assignee: {id, image, name},
	onTimeTaskCount,
	overdueTaskCount,
	processId,
	taskCount,
	taskNames,
}) {
	const {defaultDelta} = useContext(AppContext);

	const getFiltersQuery = (slaStatus) => ({
		[filterConstants.assignee.key]: [id],
		[filterConstants.processStatus.key]: [processStatusConstants.pending],
		[filterConstants.processStep.key]: taskNames,
		[filterConstants.slaStatus.key]: [slaStatus],
	});

	const instancesListPath = `/instance/${processId}/${defaultDelta}/1/dateOverdue:asc`;

	return (
		<ClayTable.Row>
			<ClayTable.Cell>
				<UserAvatar className="mr-3" image={image} />

				<ChildLink
					className="workload-by-step-link"
					query={{filters: getFiltersQuery()}}
					to={instancesListPath}
				>
					<span>{name}</span>
				</ChildLink>
			</ClayTable.Cell>

			<ClayTable.Cell className="table-cell-minw-75 text-right">
				<ChildLink
					className="workload-by-step-link"
					query={{
						filters: getFiltersQuery(slaStatusConstants.overdue),
					}}
					to={instancesListPath}
				>
					{overdueTaskCount}
				</ChildLink>
			</ClayTable.Cell>

			<ClayTable.Cell className="table-cell-minw-75 text-right">
				<ChildLink
					className="workload-by-step-link"
					query={{
						filters: getFiltersQuery(slaStatusConstants.onTime),
					}}
					to={instancesListPath}
				>
					{onTimeTaskCount}
				</ChildLink>
			</ClayTable.Cell>

			<ClayTable.Cell className="table-cell-minw-75 text-right">
				<ChildLink
					className="workload-by-step-link"
					query={{filters: getFiltersQuery()}}
					to={instancesListPath}
				>
					{taskCount}
				</ChildLink>
			</ClayTable.Cell>
		</ClayTable.Row>
	);
}

function Table({items, processId, taskNames}) {
	return (
		<ClayTable headingNoWrap>
			<ClayTable.Head>
				<ClayTable.Row>
					<ClayTable.Cell headingCell style={{width: '62%'}}>
						{Liferay.Language.get('assignee-name')}
					</ClayTable.Cell>

					<ClayTable.Cell
						className="table-cell-minw-75 text-right"
						headingCell
					>
						<ListHeadItem
							iconColor="danger"
							iconName="exclamation-circle"
							name="overdueTaskCount"
							title={Liferay.Language.get('overdue')}
						/>
					</ClayTable.Cell>

					<ClayTable.Cell
						className="table-cell-minw-75 text-right"
						headingCell
					>
						<ListHeadItem
							iconColor="success"
							iconName="check-circle"
							name="onTimeTaskCount"
							title={Liferay.Language.get('on-time')}
						/>
					</ClayTable.Cell>

					<ClayTable.Cell
						className="table-cell-minw-75 text-right"
						headingCell
					>
						<ListHeadItem
							name="taskCount"
							title={Liferay.Language.get('total-pending')}
						/>
					</ClayTable.Cell>
				</ClayTable.Row>
			</ClayTable.Head>

			<ClayTable.Body>
				{items.map((item, index) => (
					<Table.Item
						{...item}
						key={index}
						processId={processId}
						taskNames={taskNames}
					/>
				))}
			</ClayTable.Body>
		</ClayTable>
	);
}

Table.Item = Item;

export default Table;
