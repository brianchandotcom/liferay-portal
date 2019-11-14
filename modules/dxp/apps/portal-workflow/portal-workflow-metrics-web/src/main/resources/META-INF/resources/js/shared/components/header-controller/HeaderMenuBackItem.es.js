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
import {Link, withRouter} from 'react-router-dom';

import Icon from '../Icon.es';
import {parse} from '../router/queryString.es';
import PortalComponent from './PortalComponent.es';

class HeaderMenuBackItem extends React.Component {
	render() {
		const {
			basePath,
			container,
			location: {pathname, search}
		} = this.props;

		const isFirstPage = pathname === basePath || pathname === '/';
		const query = parse(search);

		return (
			<PortalComponent container={container}>
				{!isFirstPage && query.backPath && (
					<li className="control-menu-nav-item">
						<Link
							className="control-menu-icon dropdown-item lfr-icon-item"
							to={query.backPath}
						>
							<span className="icon-monospaced">
								<Icon iconName="angle-left" />
							</span>
						</Link>
					</li>
				)}
			</PortalComponent>
		);
	}
}

export default withRouter(HeaderMenuBackItem);
