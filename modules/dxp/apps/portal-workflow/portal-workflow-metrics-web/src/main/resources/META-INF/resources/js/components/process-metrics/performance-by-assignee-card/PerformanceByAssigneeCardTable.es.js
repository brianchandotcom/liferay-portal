/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayTable from '@clayui/table';
import React from 'react';

import UserAvatar from '../../../shared/components/user-avatar/UserAvatar.es';
import {formatDuration} from '../../../shared/util/duration.es';

function Item({assignee: {image, name}, durationTaskAvg, id, taskCount}) {
	const formattedDuration = formatDuration(durationTaskAvg);

	return (
		<ClayTable.Row>
			<ClayTable.Cell className="assignee-name border-0">
				<UserAvatar className="mr-3" image={image} />

				<span>{name || id}</span>
			</ClayTable.Cell>

			<ClayTable.Cell className="border-0 text-right">
				<span className="task-count-value">{taskCount}</span>
			</ClayTable.Cell>

			<ClayTable.Cell className="border-0 text-right">
				<span className="task-count-value">{formattedDuration}</span>
			</ClayTable.Cell>
		</ClayTable.Row>
	);
}

function Table({items}) {
	return (
		<ClayTable className="mb-3 table-scrollable" headingNoWrap>
			<ClayTable.Head>
				<ClayTable.Row>
					<ClayTable.Cell
						expanded
						headingCell
						headingTitle
						style={{width: '60%'}}
					>
						{Liferay.Language.get('assignee-name')}
					</ClayTable.Cell>

					<ClayTable.Cell
						className="text-right"
						headingCell
						headingTitle
						style={{width: '20%'}}
					>
						{Liferay.Language.get('completed-tasks')}
					</ClayTable.Cell>

					<ClayTable.Cell
						className="text-right"
						headingCell
						headingTitle
						style={{width: '20%'}}
					>
						{Liferay.Language.get('average-completion-time')}
					</ClayTable.Cell>
				</ClayTable.Row>
			</ClayTable.Head>

			<ClayTable.Body>
				{items.map((item, index) => (
					<Table.Item {...item} key={index} />
				))}
			</ClayTable.Body>
		</ClayTable>
	);
}

Table.Item = Item;

export default Table;
