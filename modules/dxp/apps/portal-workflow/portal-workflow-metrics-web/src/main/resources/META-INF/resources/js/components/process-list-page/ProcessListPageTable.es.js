/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayTable from '@clayui/table';
import React from 'react';

import ListHeadItem from '../../shared/components/list/ListHeadItem.es';
import ChildLink from '../../shared/components/router/ChildLink.es';

function Item({
	instanceCount,
	onTimeInstanceCount,
	overdueInstanceCount,
	process: {id, title},
}) {
	return (
		<ClayTable.Row>
			<ClayTable.Cell className="table-title">
				<ChildLink to={`/metrics/${id}`}>
					{title || Liferay.Language.get('untitled-workflow')}
				</ChildLink>
			</ClayTable.Cell>

			<ClayTable.Cell className="text-right">
				{overdueInstanceCount}
			</ClayTable.Cell>

			<ClayTable.Cell className="text-right">
				{onTimeInstanceCount}
			</ClayTable.Cell>

			<ClayTable.Cell className="text-right">
				{instanceCount}
			</ClayTable.Cell>
		</ClayTable.Row>
	);
}

function Table({items}) {
	const onTimeTitle = Liferay.Language.get('on-time');
	const overdueTitle = Liferay.Language.get('overdue');
	const processNameTitle = Liferay.Language.get('process-name');
	const totalPendingTitle = Liferay.Language.get('total-pending');

	return (
		<ClayTable>
			<ClayTable.Head>
				<ClayTable.Row>
					<ClayTable.Cell headingCell style={{width: '70%'}}>
						<ListHeadItem name="title" title={processNameTitle} />
					</ClayTable.Cell>

					<ClayTable.Cell headingCell style={{width: '10%'}}>
						<ListHeadItem
							name="overdueInstanceCount"
							title={overdueTitle}
						/>
					</ClayTable.Cell>

					<ClayTable.Cell headingCell style={{width: '10%'}}>
						<ListHeadItem
							name="onTimeInstanceCount"
							title={onTimeTitle}
						/>
					</ClayTable.Cell>

					<ClayTable.Cell headingCell style={{width: '10%'}}>
						<ListHeadItem
							name="instanceCount"
							title={totalPendingTitle}
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
