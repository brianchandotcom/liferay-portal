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

import ListHeadItem from '../../shared/components/list/ListHeadItem.es';
import UserAvatar from '../../shared/components/user-avatar/UserAvatar.es';
import WorkloadByAssigneePage from './WorkloadByAssigneePage.es';

const Item = ({image, name, onTimeTaskCount, overdueTaskCount, taskCount}) => {
	return (
		<tr>
			<td className="lfr-title-column table-cell-expand table-title">
				<UserAvatar className="mr-3" image={image} />

				<span data-testid="assigneeName">{name}</span>
			</td>

			<td
				className="table-cell-minw-75 text-right"
				data-testid="overdueTaskCount"
			>
				{overdueTaskCount}
			</td>

			<td
				className="table-cell-minw-75 text-right"
				data-testid="onTimeTaskCount"
			>
				{onTimeTaskCount}
			</td>

			<td
				className="table-cell-minw-75 text-right"
				data-testid="taskCount"
			>
				{taskCount}
			</td>
		</tr>
	);
};

const Table = ({items}) => {
	return (
		<div className="table-responsive workflow-process-dashboard">
			<table className="table table-heading-nowrap table-hover table-list">
				<thead>
					<tr>
						<th
							className="table-cell-expand table-head-title"
							style={{width: '62%'}}
						>
							{Liferay.Language.get('assignee-name')}
						</th>

						<th className="table-cell-minw-75 table-head-title text-right">
							<ListHeadItem
								iconColor="danger"
								iconName="exclamation-circle"
								name="overdueTaskCount"
								title={Liferay.Language.get('overdue')}
							/>
						</th>

						<th className="table-cell-minw-75 table-head-title text-right">
							<ListHeadItem
								iconColor="success"
								iconName="check-circle"
								name="onTimeTaskCount"
								title={Liferay.Language.get('on-time')}
							/>
						</th>

						<th className="table-cell-minw-75 table-head-title text-right">
							<ListHeadItem
								name="taskCount"
								title={Liferay.Language.get('total-pending')}
							/>
						</th>
					</tr>
				</thead>

				<tbody>
					{items.map((item, index) => (
						<WorkloadByAssigneePage.Item {...item} key={index} />
					))}
				</tbody>
			</table>
		</div>
	);
};

export {Item, Table};
