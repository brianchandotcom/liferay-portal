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
class PageSizeItem extends React.Component {
	render() {
		const {
			location: {search},
			match,
			pageSize
		} = this.props;

		const params = Object.assign({}, match.params, {page: 1, pageSize});

		const pathname = pathToRegexp.compile(match.path)(params);

		return (
			<Link
				className="dropdown-item"
				to={{
					pathname,
					search
				}}
			>
				{pageSize}
			</Link>
		);
	}
}

export default withRouter(PageSizeItem);
