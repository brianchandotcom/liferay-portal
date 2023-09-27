/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	const instancesListPath = `/instance/${processId}/${defaultDelta}/1/dateOverdue:asc`;

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
