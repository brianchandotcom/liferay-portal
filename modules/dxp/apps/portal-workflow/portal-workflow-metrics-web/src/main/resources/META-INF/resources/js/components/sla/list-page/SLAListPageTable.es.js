/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayTable from '@clayui/table';
import React from 'react';

import Item from './SLAListPageTableItem.es';

function Table({items}) {
	const blockedItems = items.filter(({status}) => status === 2);
	const unblockedItems = items.filter(({status}) => status !== 2);

	const showBlockedDivider = !!blockedItems.length;
	const showRunningDivider = showBlockedDivider && !!unblockedItems.length;

	return (
		<ClayTable className="table-responsive">
			<ClayTable.Head>
				<ClayTable.Row>
					<ClayTable.Cell headingCell style={{width: '27%'}}>
						{Liferay.Language.get('sla-name')}
					</ClayTable.Cell>

					<ClayTable.Cell headingCell style={{width: '24%'}}>
						{Liferay.Language.get('description')}
					</ClayTable.Cell>

					<ClayTable.Cell headingCell style={{width: '17%'}}>
						{Liferay.Language.get('status')}
					</ClayTable.Cell>

					<ClayTable.Cell headingCell style={{width: '17%'}}>
						{Liferay.Language.get('duration')}
					</ClayTable.Cell>

					<ClayTable.Cell headingCell style={{width: '25%'}}>
						{Liferay.Language.get('last-modified')}
					</ClayTable.Cell>

					<ClayTable.Cell />
				</ClayTable.Row>
			</ClayTable.Head>

			<ClayTable.Body>
				{showBlockedDivider && (
					<ClayTable.Row className="table-divider">
						<ClayTable.Cell colSpan="9">
							{Liferay.Language.get('blocked').toUpperCase()}
						</ClayTable.Cell>
					</ClayTable.Row>
				)}

				{blockedItems.map((sla, index) => (
					<Table.Item {...sla} key={`blocked_${index}`} status={2} />
				))}

				{showRunningDivider && (
					<ClayTable.Row className="table-divider">
						<ClayTable.Cell colSpan="9">
							{Liferay.Language.get('running').toUpperCase()}
						</ClayTable.Cell>
					</ClayTable.Row>
				)}

				{unblockedItems.map((sla, index) => (
					<Table.Item {...sla} key={`unblocked_${index}`} />
				))}
			</ClayTable.Body>
		</ClayTable>
	);
}

Table.Item = Item;

export default Table;
