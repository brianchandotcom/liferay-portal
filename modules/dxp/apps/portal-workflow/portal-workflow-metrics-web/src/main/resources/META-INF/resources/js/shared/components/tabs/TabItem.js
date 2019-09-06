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

import {Link} from 'react-router-dom';
import pathToRegexp from 'path-to-regexp';
import React from 'react';

export default class TabItem extends React.Component {
	render() {
		const {active, name, params, path, query} = this.props;

		const activeClassName = active ? 'active' : '';
		const activeLabel = active ? Liferay.Language.get('current-page') : '';

		return (
			<Link
				aria-label={activeLabel}
				className={`${activeClassName} nav-link`}
				to={{
					pathname: getPathname(params, path),
					search: query
				}}
			>
				<span className="navbar-text-truncate">{name}</span>
			</Link>
		);
	}
}

export function getPathname(params, path) {
	return pathToRegexp.compile(path)(params);
}
