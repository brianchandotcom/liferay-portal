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

import {formatDuration} from '../../../shared/util/duration.es';
import {formatNumber} from '../../../shared/util/numeral.es';
import PerformanceByStepCard from './PerformanceByStepCard.es';
import React from 'react';

const getSLABreachedPercentage = (instanceCount, overdueInstanceCount) => {
	let percentage = 0;

	if (instanceCount) {
		percentage = overdueInstanceCount / instanceCount;
	}

	return formatNumber(percentage, '0[.]00%');
};

const Table = ({items = []}) => (
	<div className="mb-3 table-scrollable table-responsive">
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

const Item = ({durationAvg, instanceCount, name, overdueInstanceCount}) => {
	const formattedDuration = formatDuration(durationAvg);
	const percentage = getSLABreachedPercentage(
		instanceCount,
		overdueInstanceCount
	);

	return (
		<tr>
			<td data-testId="stepName">{name}</td>

			<td className="text-right" data-testId="slaBreached">
				{`${overdueInstanceCount} (${percentage})`}
			</td>

			<td className="text-right" data-testId="avgCompletionTime">
				{formattedDuration}
			</td>
		</tr>
	);
};

export {Item, Table};
