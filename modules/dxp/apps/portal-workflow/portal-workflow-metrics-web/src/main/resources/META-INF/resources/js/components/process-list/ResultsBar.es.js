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

import pathToRegexp from 'path-to-regexp';
import React from 'react';
import {Link, withRouter} from 'react-router-dom';

import {sub} from '../../shared/util/lang.es';

/**
 * Results bar component.
 * @class
 * @memberof process-list
 */
class ResultsBar extends React.Component {
	render() {
		const {
			location: {search},
			match: {
				params: {page, pageSize, search: term, sort},
				path
			},
			totalCount
		} = this.props;

		const pathname = pathToRegexp.compile(path)({page, pageSize, sort});

		let resultText = Liferay.Language.get('x-results-for-x');

		if (totalCount === 1) {
			resultText = Liferay.Language.get('x-result-for-x');
		}

		return (
			<nav className="subnav-tbar subnav-tbar-primary tbar tbar-inline-xs-down">
				<div className="container-fluid container-fluid-max-xl">
					<ul className="tbar-nav tbar-nav-wrap">
						<li className="tbar-item tbar-item-expand">
							<div className="tbar-section">
								<span className="component-text text-truncate-inline">
									<span className="text-truncate">
										{sub(resultText, [totalCount, term])}
									</span>
								</span>
							</div>
						</li>

						<li className="tbar-item">
							<div className="tbar-section">
								<Link
									className="component-link tbar-link"
									to={{
										pathname,
										search
									}}
								>
									<span>
										{Liferay.Language.get('clear-all')}
									</span>
								</Link>
							</div>
						</li>
					</ul>
				</div>
			</nav>
		);
	}
}

export default withRouter(ResultsBar);
export {ResultsBar};
