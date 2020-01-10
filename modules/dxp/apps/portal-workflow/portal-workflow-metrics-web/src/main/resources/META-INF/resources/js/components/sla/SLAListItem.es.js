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

import Icon from '../../shared/components/Icon.es';
import {ChildLink} from '../../shared/components/router/routerWrapper.es';
import {formatDuration} from '../../shared/util/duration.es';
import moment from '../../shared/util/moment.es';
import SLAListCardContext from './SLAListCardContext.es';

class SLAListItem extends React.Component {
	showConfirmDialog() {
		const {id} = this.props;

		this.context.showConfirmDialog(id);
	}

	render() {
		const {
			dateModified,
			description,
			duration,
			id,
			name,
			processId,
			status
		} = this.props;

		const blocked = status === 2;
		const durationString = formatDuration(duration);

		const blockedStatusClass = blocked ? 'text-danger' : '';

		const statusText = blocked
			? Liferay.Language.get('blocked')
			: Liferay.Language.get('running');

		return (
			<tr>
				<td className="table-cell-expand">
					<div className="table-list-title">
						{blocked && (
							<Icon
								elementClasses="text-danger"
								iconName="exclamation-full"
							/>
						)}{' '}
						<ChildLink to={`/sla/edit/${processId}/${id}`}>
							{name}
						</ChildLink>
					</div>
				</td>

				<td>{description}</td>

				<td className={blockedStatusClass}>{statusText}</td>

				<td>{durationString}</td>

				<td>
					{moment
						.utc(dateModified)
						.format(Liferay.Language.get('mmm-dd'))}
				</td>

				<td className="actions">
					<div className="dropdown dropdown-action">
						<a
							aria-expanded="false"
							aria-haspopup="true"
							className="component-action dropdown-toggle"
							data-toggle="liferay-dropdown"
							href="#1"
							id="dropdownAction1"
							role="button"
						>
							<Icon iconName="ellipsis-v" />
						</a>

						<ul
							aria-labelledby=""
							className="dropdown-menu dropdown-menu-right"
						>
							<li>
								<ChildLink
									className="dropdown-item"
									to={`/sla/edit/${processId}/${id}`}
								>
									{Liferay.Language.get('edit')}
								</ChildLink>
							</li>

							<li>
								<button
									className="dropdown-item"
									onClick={this.showConfirmDialog.bind(this)}
								>
									{Liferay.Language.get('delete')}
								</button>
							</li>
						</ul>
					</div>
				</td>
			</tr>
		);
	}
}

SLAListItem.contextType = SLAListCardContext;
export default SLAListItem;
