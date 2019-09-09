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

import Icon from '../Icon.es';
import React from 'react';

export default class FilterSearch extends React.Component {
	render() {
		const {children, filteredItems, onChange, totalCount} = this.props;

		const emptyResults = filteredItems.length === 0;
		const searchEnabled = totalCount > 12;

		return (
			<>
				{searchEnabled && (
					<form>
						<div className="dropdown-section">
							<div className="input-group input-group-sm">
								<div className="input-group-item">
									<input
										className="form-control input-group-inset input-group-inset-after"
										onChange={onChange}
										placeholder={Liferay.Language.get(
											'search-for'
										)}
										type="text"
									/>

									<span className="input-group-inset-item input-group-inset-item-after">
										<button
											className="btn btn-unstyled"
											type="button"
										>
											<Icon iconName="search" />
										</button>
									</span>
								</div>
							</div>
						</div>
					</form>
				)}

				{emptyResults && (
					<ul className="list-unstyled">
						<li>
							<span className="disabled dropdown-item">
								{Liferay.Language.get('no-results-were-found')}
							</span>
						</li>
					</ul>
				)}

				{children}
			</>
		);
	}
}
