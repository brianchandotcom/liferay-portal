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
import PortalComponent from '../../shared/components/header-controller/PortalComponent.es';

const MenuItem = ({children}) => {
	if (!children) return null;

	return <li>{children}</li>;
};

export default class DropDownHeader extends React.Component {
	render() {
		const {children} = this.props;
		const container = document.querySelector(
			'.user-control-group div.control-menu-icon'
		);

		return (
			<PortalComponent container={container} replace>
				<div className="dropdown lfr-icon-menu nav-item portlet-options">
					<a
						aria-expanded="false"
						aria-haspopup="true"
						className="direction-right dropdown-toggle icon-monospaced"
						data-toggle="liferay-dropdown"
						href="javascript:;"
						role="button"
						title="Options"
					>
						<span>
							<Icon iconName="ellipsis-v" />
						</span>
					</a>

					<div
						className="dropdown-menu dropdown-menu-header dropdown-menu-right"
						role="menu"
					>
						<ul className="list-unstyled">{children}</ul>
					</div>
				</div>
			</PortalComponent>
		);
	}
}

DropDownHeader.Item = MenuItem;
