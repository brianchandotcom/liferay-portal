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
import ChildLink from '../../shared/components/router/ChildLink.es';

const Item = ({
	instanceCount,
	onTimeInstanceCount,
	overdueInstanceCount,
	process: {id, title},
}) => {
	return (
		<ClayTable.Row>
			<ClayTable.Cell className="table-title">
				<ChildLink to={`/metrics/${id}`}>{title}</ChildLink>
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
};

const Table = ({items}) => {
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
};

Table.Item = Item;

export {Table};
