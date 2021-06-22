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
