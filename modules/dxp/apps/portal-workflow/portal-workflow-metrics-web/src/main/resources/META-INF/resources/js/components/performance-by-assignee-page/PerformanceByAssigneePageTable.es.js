/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayTable from '@clayui/table';
import React from 'react';

import ListHeadItem from '../../shared/components/list/ListHeadItem.es';
import UserAvatar from '../../shared/components/user-avatar/UserAvatar.es';
import {formatDuration} from '../../shared/util/duration.es';

function Item({assignee: {image, name}, durationTaskAvg, id, taskCount}) {
	const formattedDuration = formatDuration(durationTaskAvg);

	return (
		<ClayTable.Row>
			<ClayTable.Cell>
				<UserAvatar className="mr-3" image={image} />

				<span>{name || id}</span>
			</ClayTable.Cell>

			<ClayTable.Cell className="text-right">
				<span>{taskCount}</span>
			</ClayTable.Cell>

			<ClayTable.Cell className="text-right">
				<span>{formattedDuration}</span>
			</ClayTable.Cell>
		</ClayTable.Row>
	);
}

function Table({items}) {
	return (
		<ClayTable>
			<ClayTable.Head>
				<ClayTable.Row>
					<ClayTable.Cell headingCell style={{width: '60%'}}>
						{Liferay.Language.get('assignee-name')}
					</ClayTable.Cell>

					<ClayTable.Cell
						className="text-right"
						headingCell
						style={{width: '20%'}}
					>
						<ListHeadItem
							name="taskCount"
							title={Liferay.Language.get('completed-tasks')}
						/>
					</ClayTable.Cell>

					<ClayTable.Cell
						className="text-right"
						headingCell
						style={{width: '20%'}}
					>
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
					<Table.Item {...item} key={index} />
				))}
			</ClayTable.Body>
		</ClayTable>
	);
}

Table.Item = Item;

export default Table;
