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
