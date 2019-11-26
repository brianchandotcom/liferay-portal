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
import {withRouter} from 'react-router-dom';

import Icon from '../../../shared/components/Icon.es';
import {
	pushToHistory,
	removeItem
} from '../../../shared/components/filter/util/filterUtil.es';

class FilterResultsItem extends React.Component {
	onRemoveButtonClick() {
		const {
			filter,
			item,
			location: {search}
		} = this.props;

		const filterQuery = removeItem(filter.key, item, search);

		item.active = false;

		pushToHistory(filterQuery, this.props);
	}

	render() {
		const {filter, item} = this.props;
		const {name, resultName} = item;

		return (
			<li className="tbar-item">
				<div className="tbar-section">
					<span className="component-label label label-dismissible tbar-label">
						<span className="label-item label-item-expand">
							<div className="label-section">
								<span className="font-weight-normal">{`${filter.name}: `}</span>

								<strong>{resultName || name}</strong>
							</div>
						</span>

						{!filter.pinned && (
							<span className="label-item label-item-after">
								<button
									aria-label="close"
									className="btn close"
									onClick={this.onRemoveButtonClick.bind(
										this
									)}
									type="button"
								>
									<Icon iconName="times" />
								</button>
							</span>
						)}
					</span>
				</div>
			</li>
		);
	}
}

export default withRouter(FilterResultsItem);
export {FilterResultsItem};
