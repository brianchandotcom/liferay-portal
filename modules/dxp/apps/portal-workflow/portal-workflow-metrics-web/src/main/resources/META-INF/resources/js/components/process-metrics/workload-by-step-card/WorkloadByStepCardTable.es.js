/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayTable from '@clayui/table';
import React from 'react';

import ListHeadItem from '../../../shared/components/list/ListHeadItem.es';
import Item from './WorkloadByStepCardItem.es';

function Table({items, processId}) {
	const onTimeTitle = Liferay.Language.get('on-time');
	const overdueTitle = Liferay.Language.get('overdue');
	const stepNameTitle = Liferay.Language.get('step-name');
	const totalPendingTitle = Liferay.Language.get('total-pending');

	return (
		<ClayTable headingNoWrap>
			<ClayTable.Head>
				<ClayTable.Row>
					<ClayTable.Cell expanded headingCell>
						{stepNameTitle}
					</ClayTable.Cell>

					<ClayTable.Cell className="text-right" headingCell>
						<ListHeadItem
							iconColor="danger"
							iconName="exclamation-circle"
							name="overdueInstanceCount"
							title={overdueTitle}
						/>
					</ClayTable.Cell>

					<ClayTable.Cell className="text-right" headingCell>
						<ListHeadItem
							iconColor="success"
							iconName="check-circle"
							name="onTimeInstanceCount"
							title={onTimeTitle}
						/>
					</ClayTable.Cell>

					<ClayTable.Cell className="text-right" headingCell>
						<ListHeadItem
							name="instanceCount"
							title={totalPendingTitle}
						/>
					</ClayTable.Cell>
				</ClayTable.Row>
			</ClayTable.Head>

			<ClayTable.Body>
				{items.map((step, index) => (
					<Table.Item {...step} key={index} processId={processId} />
				))}
			</ClayTable.Body>
		</ClayTable>
	);
}

Table.Item = Item;
export default Table;
