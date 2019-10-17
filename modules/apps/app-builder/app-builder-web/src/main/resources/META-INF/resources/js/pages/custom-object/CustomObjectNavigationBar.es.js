/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayNavigationBar from '@clayui/navigation-bar';
import {NavLink, withRouter} from 'react-router-dom';
import React from 'react';

const {Item} = ClayNavigationBar;

export default withRouter(({match: {url}}) => {
	const blur = event => {
		event.target.blur();
	};

	return (
		<ClayNavigationBar
			inverted
			triggerLabel={Liferay.Language.get('form-views')}
		>
			<Item key="0">
				<NavLink
					activeClassName="active"
					className="nav-link"
					onClick={blur}
					to={`${url}/form-views`}
				>
					{Liferay.Language.get('form-views')}
				</NavLink>
			</Item>

			<Item key="1">
				<NavLink
					activeClassName="active"
					className="nav-link"
					onClick={blur}
					to={`${url}/table-views`}
				>
					{Liferay.Language.get('table-views')}
				</NavLink>
			</Item>

			<Item key="2">
				<NavLink
					activeClassName="active"
					className="nav-link"
					onClick={blur}
					to={`${url}/apps`}
				>
					{Liferay.Language.get('apps')}
				</NavLink>
			</Item>
		</ClayNavigationBar>
	);
});
