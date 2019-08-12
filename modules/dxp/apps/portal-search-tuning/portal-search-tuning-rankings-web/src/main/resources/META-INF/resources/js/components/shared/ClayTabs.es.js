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

import getCN from 'classnames';
import React from 'react';
import {Tab, TabList, TabPanel, Tabs} from 'react-tabs';

const ClayTab = ({children, selected, selectedClassName, ...otherProps}) => {
	const classes = getCN('btn', 'btn-unstyled', 'nav-link', {
		[selectedClassName]: selected
	});

	return (
		<Tab className="nav-item" selected={selected} {...otherProps}>
			<button className={classes}>{children}</button>
		</Tab>
	);
};

ClayTab.tabsRole = 'Tab';

const ClayTabs = ({children, ...otherProps}) => (
	<Tabs selectedTabClassName="active" {...otherProps}>
		{children}
	</Tabs>
);

const ClayTabList = ({children, className, ...otherProps}) => {
	const classesNav = getCN(
		'navbar',
		'navbar-collapse-absolute',
		'navbar-expand-md',
		'navbar-underline',
		'navigation-bar',
		'navigation-bar-light'
	);
	const classes = getCN('navbar-nav', className);

	return (
		<nav className={classesNav}>
			<TabList className={classes} {...otherProps}>
				{children}
			</TabList>
		</nav>
	);
};

ClayTabList.tabsRole = 'TabList';

export {ClayTabs, ClayTab, ClayTabList, TabPanel as ClayTabPanel};
