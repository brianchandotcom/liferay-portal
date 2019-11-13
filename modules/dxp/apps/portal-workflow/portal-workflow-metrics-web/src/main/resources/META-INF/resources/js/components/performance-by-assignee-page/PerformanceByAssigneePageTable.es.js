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

import ClayTable from '@clayui/table';
import React from 'react';

import ListHeadItem from '../../shared/components/list/ListHeadItem.es';
import UserAvatar from '../../shared/components/user-avatar/UserAvatar.es';
import {formatDuration} from '../../shared/util/duration.es';
import PerformanceByAssigneePage from './PerformanceByAssigneePage.es';

const Item = ({durationTaskAvg, image, name, taskCount}) => {
	const formattedDuration = formatDuration(durationTaskAvg);

	return (
		<ClayTable.Row>
			<ClayTable.Cell data-testid="assigneeProfileInfo">
				<UserAvatar className="mr-3" image={image} />

				<span data-testid="assigneeName">{name}</span>
			</ClayTable.Cell>

			<ClayTable.Cell>
				<span className="task-count-value" data-testid="taskCount">
					{taskCount}
				</span>
			</ClayTable.Cell>

			<ClayTable.Cell>
				<span
					className="task-count-value"
					data-testid="durationTaskAvg"
				>
					{formattedDuration}
				</span>
			</ClayTable.Cell>
		</ClayTable.Row>
	);
};

const Table = ({items}) => {
	return (
		<ClayTable>
			<ClayTable.Head>
				<ClayTable.Row>
					<ClayTable.Cell headingCell style={{width: '60%'}}>
						{Liferay.Language.get('assignee-name')}
					</ClayTable.Cell>

					<ClayTable.Cell headingCell style={{width: '20%'}}>
						<ListHeadItem
							name="taskCount"
							title={Liferay.Language.get('completed-tasks')}
						/>
					</ClayTable.Cell>

					<ClayTable.Cell headingCell style={{width: '20%'}}>
						<ListHeadItem
							name="durationTaskAvg"
							title={Liferay.Language.get(
								'average-completion-time'
							)}
						/>
					</ClayTable.Cell>
				</ClayTable.Row>
			</ClayTable.Head>

			<ClayTable.Body>
				{items.map((item, index) => (
					<PerformanceByAssigneePage.Item {...item} key={index} />
				))}
			</ClayTable.Body>
		</ClayTable>
	);
};

export {Item, Table};
