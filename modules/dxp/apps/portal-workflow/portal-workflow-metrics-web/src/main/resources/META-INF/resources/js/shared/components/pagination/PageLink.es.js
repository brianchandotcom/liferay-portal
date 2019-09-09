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

import {Link, withRouter} from 'react-router-dom';
import pathToRegexp from 'path-to-regexp';
import React from 'react';

/**
 * @class
 * @memberof shared/components
 */
class PageLink extends React.Component {
	render() {
		const {
			location: {search},
			match: {params, path},
			page
		} = this.props;

		const pathname = pathToRegexp.compile(path)(
			Object.assign({}, params, {page})
		);

		return (
			<li className="page-item">
				<Link className="page-link" to={{pathname, search}}>
					<span className="sr-only" />
					{page}
				</Link>
			</li>
		);
	}
}

export default withRouter(PageLink);
