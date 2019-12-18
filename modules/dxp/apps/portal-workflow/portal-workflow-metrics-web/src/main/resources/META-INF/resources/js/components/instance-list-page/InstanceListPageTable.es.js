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

import {Item} from './InstanceListPageItem.es';

const Table = ({items}) => {
	return (
		<ClayTable>
			<ClayTable.Head>
				<ClayTable.Row>
					<ClayTable.Cell headingCell />
					<ClayTable.Cell headingCell style={{width: '8%'}}>
						{Liferay.Language.get('id')}
					</ClayTable.Cell>

					<ClayTable.Cell headingCell style={{width: '17%'}}>
						{Liferay.Language.get('item-subject')}
					</ClayTable.Cell>

					<ClayTable.Cell headingCell style={{width: '18%'}}>
						{Liferay.Language.get('process-step')}
					</ClayTable.Cell>

					<ClayTable.Cell headingCell style={{width: '14%'}}>
						{Liferay.Language.get('assignee')}
					</ClayTable.Cell>

					<ClayTable.Cell headingCell style={{width: '17%'}}>
						{Liferay.Language.get('created-by')}
					</ClayTable.Cell>
					<ClayTable.Cell
						headingCell
						style={{textAlign: 'right', width: '18%'}}
					>
						{Liferay.Language.get('creation-date')}
					</ClayTable.Cell>

					<ClayTable.Cell headingCell />
				</ClayTable.Row>
			</ClayTable.Head>

			<ClayTable.Body>
				{items.map((item, index) => (
					<Table.Item {...item} key={index} />
				))}
			</ClayTable.Body>
		</ClayTable>
	);
};

Table.Item = Item;

export {Table};
