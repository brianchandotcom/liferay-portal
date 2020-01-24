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

import ListHeadItem from '../../../shared/components/list/ListHeadItem.es';
import {Item} from './WorkloadByStepCardItem.es';

const Table = ({items, processId}) => {
	const onTimeTitle = Liferay.Language.get('on-time');
	const overdueTitle = Liferay.Language.get('overdue');
	const stepNameTitle = Liferay.Language.get('step-name');
	const totalPendingTitle = Liferay.Language.get('total-pending');

	return (
		<div className="table-responsive">
			<table
				className="show-quick-actions-on-hover table table-autofit table-heading-nowrap table-hover table-list"
				data-testid="workloadByStepTable"
			>
				<thead>
					<tr>
						<th className="table-cell-expand table-head-title">
							{stepNameTitle}
						</th>

						<th className="table-head-title text-right">
							<ListHeadItem
								iconColor="danger"
								iconName="exclamation-circle"
								name="overdueInstanceCount"
								title={overdueTitle}
							/>
						</th>

						<th className="table-head-title text-right">
							<ListHeadItem
								iconColor="success"
								iconName="check-circle"
								name="onTimeInstanceCount"
								title={onTimeTitle}
							/>
						</th>

						<th className="table-head-title text-right">
							<ListHeadItem
								name="instanceCount"
								title={totalPendingTitle}
							/>
						</th>
					</tr>
				</thead>

				<tbody>
					{items.map((step, index) => (
						<Table.Item
							{...step}
							key={index}
							processId={processId}
							taskKey={step.key}
						/>
					))}
				</tbody>
			</table>
		</div>
	);
};

Table.Item = Item;
export {Table};
