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
import {getFormattedPercentage} from '../../../shared/util/util.es';
import PerformanceByStepCard from './PerformanceByStepCard.es';

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
					<PerformanceByStepCard.Item {...item} key={index} />
				))}
			</tbody>
		</table>
	</div>
);

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
			<td data-testid="stepName">{name}</td>

			<td className="text-right" data-testid="slaBreached">
				{breachedInstanceCount} ({formattedPercentage})
			</td>

			<td className="text-right" data-testid="avgCompletionTime">
				{formattedDuration}
			</td>
		</tr>
	);
};

export {Item, Table};
