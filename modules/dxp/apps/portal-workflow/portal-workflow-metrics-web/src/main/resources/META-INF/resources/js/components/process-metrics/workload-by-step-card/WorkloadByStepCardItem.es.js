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

import React, {useContext} from 'react';

import filterConstants from '../../../shared/components/filter/util/filterConstants.es';
import ChildLink from '../../../shared/components/router/ChildLink.es';
import {AppContext} from '../../AppContext.es';
import {processStatusConstants} from '../../filter/ProcessStatusFilter.es';

function Item({
	instanceCount,
	node: {label, name},
	onTimeInstanceCount,
	overdueInstanceCount,
	processId,
}) {
	const {defaultDelta} = useContext(AppContext);
	const getFiltersQuery = (slaStatusFilter) => {
		return {
			[filterConstants.processStatus.key]: [
				processStatusConstants.pending,
			],
			[filterConstants.processStep.key]: [name],
			[filterConstants.slaStatus.key]: [slaStatusFilter],
		};
	};
	const instancesListPath = `/instance/${processId}/${defaultDelta}/1/dateCreated:asc`;

	return (
		<tr>
			<td className="lfr-title-column table-cell-expand table-cell-minw-200 table-title">
				{label}
			</td>

			<td className="text-right">
				<ChildLink
					className="workload-by-step-link"
					query={{filters: getFiltersQuery('Overdue')}}
					to={instancesListPath}
				>
					{overdueInstanceCount}
				</ChildLink>
			</td>

			<td className="text-right">
				<ChildLink
					className="workload-by-step-link"
					query={{filters: getFiltersQuery('OnTime')}}
					to={instancesListPath}
				>
					{onTimeInstanceCount}
				</ChildLink>
			</td>

			<td className="text-right">
				<ChildLink
					className="workload-by-step-link"
					query={{filters: getFiltersQuery()}}
					to={instancesListPath}
				>
					{instanceCount}
				</ChildLink>
			</td>
		</tr>
	);
}

export default Item;
