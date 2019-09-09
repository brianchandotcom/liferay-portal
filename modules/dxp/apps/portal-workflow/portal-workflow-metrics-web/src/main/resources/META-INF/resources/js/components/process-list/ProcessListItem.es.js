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

import {AppContext} from '../AppContext.es';
import {ChildLink} from '../../shared/components/router/routerWrapper.es';
import React from 'react';

/**
 * @class
 * @memberof process-list
 */
class ProcessListItem extends React.Component {
	render() {
		const {
			id,
			instanceCount = '-',
			onTimeInstanceCount = '-',
			overdueInstanceCount = '-',
			title
		} = this.props;

		return (
			<tr>
				<td className="table-cell-expand table-cell-minw-200 table-title lfr-title-column">
					<ChildLink to={`/metrics/${id}`}>
						<span>{title}</span>
					</ChildLink>
				</td>

				<td>{overdueInstanceCount}</td>

				<td>{onTimeInstanceCount}</td>

				<td>{instanceCount}</td>
			</tr>
		);
	}
}

ProcessListItem.contextType = AppContext;
export default ProcessListItem;
