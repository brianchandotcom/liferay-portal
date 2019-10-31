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

import UserAvatar from '../../../shared/components/user-avatar/UserAvatar.es';
import {formatDuration} from '../../../shared/util/duration.es';
import PerformanceByAssigneeCard from './PerformanceByAssigneeCard.es';

const Item = ({durationTaskAvg, image, name, taskCount}) => {
	const formattedDuration = formatDuration(durationTaskAvg);

	return (
		<tr>
			<td
				className="assignee-name border-0"
				data-testid="assigneeProfileInfo"
			>
				<UserAvatar className="mr-3" image={image} />

				<span data-testid="assigneeName">{name}</span>
			</td>

			<td className="border-0 text-right">
				<span className="task-count-value" data-testid="taskCount">
					{taskCount}
				</span>
			</td>
			<td className="border-0 text-right">
				<span
					className="task-count-value"
					data-testid="durationTaskAvg"
				>
					{formattedDuration}
				</span>
			</td>
		</tr>
	);
};

const Table = ({items}) => {
	return (
		<div className="mb-3 table-responsive table-scrollable">
			<table className="table table-autofit table-heading-nowrap table-hover table-list">
				<thead>
					<tr>
						<th
							className="table-cell-expand table-head-title"
							style={{width: '60%'}}
						>
							{Liferay.Language.get('assignee-name')}
						</th>

						<th
							className="table-head-title text-right"
							style={{width: '20%'}}
						>
							{Liferay.Language.get('completed-tasks')}
						</th>

						<th
							className="table-head-title text-right"
							style={{width: '20%'}}
						>
							{Liferay.Language.get('average-completion-time')}
						</th>
					</tr>
				</thead>
				<tbody>
					{items.map((item, index) => (
						<PerformanceByAssigneeCard.Item {...item} key={index} />
					))}
				</tbody>
			</table>
		</div>
	);
};

export {Item, Table};
