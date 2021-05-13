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
import Item from './InstanceListPageItem.es';

const Table = ({items, totalCount}) => {
	return (
		<ClayTable>
			<ClayTable.Head>
				<ClayTable.Row>
					<ClayTable.Cell headingCell style={{width: '5%'}} />

					<ClayTable.Cell headingCell style={{width: '5%'}}>
						{Liferay.Language.get('id')}
					</ClayTable.Cell>

					<ClayTable.Cell headingCell style={{width: '14%'}}>
						<ListHeadItem
							name="dateOverdue"
							title={Liferay.Language.get('due-date')}
						/>
					</ClayTable.Cell>

					<ClayTable.Cell headingCell style={{width: '13%'}}>
						{Liferay.Language.get('item-subject')}
					</ClayTable.Cell>

					<ClayTable.Cell headingCell style={{width: '14%'}}>
						{Liferay.Language.get('process-step')}
					</ClayTable.Cell>

					<ClayTable.Cell headingCell style={{width: '14%'}}>
						{Liferay.Language.get('assignee')}
					</ClayTable.Cell>

					<ClayTable.Cell headingCell style={{width: '14%'}}>
						{Liferay.Language.get('created-by')}
					</ClayTable.Cell>

					<ClayTable.Cell headingCell style={{width: '16%'}}>
						<ListHeadItem
							name="dateCreated"
							title={Liferay.Language.get('creation-date')}
						/>
					</ClayTable.Cell>

					<ClayTable.Cell headingCell style={{width: '5%'}} />
				</ClayTable.Row>
			</ClayTable.Head>

			<ClayTable.Body>
				{items.map((item, index) => (
					<Table.Item {...item} key={index} totalCount={totalCount} />
				))}
			</ClayTable.Body>
		</ClayTable>
	);
};

Table.Item = Item;

export {Table};
