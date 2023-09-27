/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayNavigationBar from '@clayui/navigation-bar';
import React, {useCallback} from 'react';
import {Link} from 'react-router-dom';

import {useRouter} from '../../hooks/useRouter.es';
import {getPathname} from '../router/routerUtil.es';

const Item = ({active, name, params, path}) => {
	const {
		location: {search},
	} = useRouter();

	return (
		<ClayNavigationBar.Item active={active}>
			<Link
				className="nav-link"
				to={{
					pathname: getPathname(params, path),
					search,
				}}
			>
				{name}
			</Link>
		</ClayNavigationBar.Item>
	);
};

const NavbarTabs = ({tabs = []}) => {
	const {
		location: {pathname},
	} = useRouter();

	const isActive = useCallback((tab) => pathname.includes(tab.key), [
		pathname,
	]);

	const {name: activeTabName} = tabs.filter(isActive)[0] || {};

	return (
		<ClayNavigationBar triggerLabel={activeTabName}>
			{tabs.map((tab, index) => (
				<NavbarTabs.Item {...tab} active={isActive(tab)} key={index} />
			))}
		</ClayNavigationBar>
	);
};

NavbarTabs.Item = Item;

export default NavbarTabs;
