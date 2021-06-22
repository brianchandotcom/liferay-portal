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

import {formatDuration} from '../../../shared/util/duration.es';
import {
	getFormattedPercentage,
	isValidNumber,
} from '../../../shared/util/util.es';

function Item({
	breachedInstanceCount,
	breachedInstancePercentage,
	durationAvg,
	node: {label},
}) {
	const formattedDuration = formatDuration(durationAvg);
	const formattedPercentage = getFormattedPercentage(
		breachedInstancePercentage,
		100
	);

	return (
		<ClayTable.Row>
			<ClayTable.Cell expanded>{label}</ClayTable.Cell>

			<ClayTable.Cell className="text-right">
				{isValidNumber(breachedInstanceCount)
					? breachedInstanceCount
					: 0}{' '}
				({formattedPercentage})
			</ClayTable.Cell>

			<ClayTable.Cell className="text-right">
				{formattedDuration}
			</ClayTable.Cell>
		</ClayTable.Row>
	);
}

function Table({items = []}) {
	return (
		<ClayTable className="mb-3 table-scrollable" headingNoWrap>
			<ClayTable.Head>
				<ClayTable.Row>
					<ClayTable.Cell headingCell style={{width: '60%'}}>
						{Liferay.Language.get('step-name')}
					</ClayTable.Cell>

					<ClayTable.Cell
						className="text-right"
						headingCell
						style={{width: '20%'}}
					>
						{Liferay.Language.get('sla-breached-percent')}
					</ClayTable.Cell>

					<ClayTable.Cell
						className="text-right"
						headingCell
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
