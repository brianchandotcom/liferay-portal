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

import React from 'react';

import {formatDuration} from '../../../shared/util/duration.es';
import {
	getFormattedPercentage,
	isValidNumber
} from '../../../shared/util/util.es';

const Item = ({
	breachedInstanceCount,
	breachedInstancePercentage,
	durationAvg,
	name
}) => {
	const formattedDuration = formatDuration(durationAvg);
	const formattedPercentage = getFormattedPercentage(
		breachedInstancePercentage,
		100
	);

	return (
		<tr>
			<td className="table-cell-expand" data-testid="stepName">
				{name}
			</td>

			<td className="text-right" data-testid="slaBreached">
				{isValidNumber(breachedInstanceCount)
					? breachedInstanceCount
					: 0}{' '}
				({formattedPercentage})
			</td>

			<td className="text-right" data-testid="avgCompletionTime">
				{formattedDuration}
			</td>
		</tr>
	);
};

const Table = ({items = []}) => (
	<div className="mb-3 table-responsive table-scrollable">
		<table className="table table-autofit table-heading-nowrap table-hover table-list">
			<thead>
				<tr>
					<th style={{width: '60%'}}>
						{Liferay.Language.get('step-name')}
					</th>

					<th className="text-right" style={{width: '20%'}}>
						{Liferay.Language.get('sla-breached-percent')}
					</th>

					<th className="text-right" style={{width: '20%'}}>
						{Liferay.Language.get('average-completion-time')}
					</th>
				</tr>
			</thead>

			<tbody>
				{items.map((item, index) => (
					<Table.Item {...item} key={index} />
				))}
			</tbody>
		</table>
	</div>
);

Table.Item = Item;

export {Table};
