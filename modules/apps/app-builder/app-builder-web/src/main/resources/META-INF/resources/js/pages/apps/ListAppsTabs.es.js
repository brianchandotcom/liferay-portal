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

import React, {useContext, useEffect} from 'react';

import {AppContext} from '../../AppContext.es';
import ControlMenu from '../../components/control-menu/ControlMenu.es';
import NavigationBar from '../../components/navigation-bar/NavigationBar.es';
import useLazy from '../../hooks/useLazy.es';

export default (props) => {
	const {appsTabs, appsTabsKeys} = useContext(AppContext);
	const {tab = appsTabsKeys[0]} = props.match.params;

	useEffect(() => {
		if (props.history.location.pathname === '/') {
			props.history.replace(`${tab}`);
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	const {listEntryPoint, ...otherProps} = appsTabs[tab];
	const navTabs = Object.values(appsTabs).map(({label, scope}) => ({
		active: tab === scope,
		label,
		path: () => `/${scope}`,
	}));
	const TabContent = useLazy();

	return (
		<>
			<ControlMenu title={Liferay.Language.get('apps')} />

			<NavigationBar tabs={navTabs} />

			<TabContent
				module={listEntryPoint}
				props={{...props, ...otherProps}}
			/>
		</>
	);
};
