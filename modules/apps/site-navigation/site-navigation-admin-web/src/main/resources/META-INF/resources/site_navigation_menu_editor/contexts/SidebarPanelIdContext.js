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

import PropTypes from 'prop-types';
import React, {useContext, useState} from 'react';

import {SIDEBAR_PANEL_IDS} from '../constants/sidebarPanelIds';
import {useSelectedMenuItemId} from './SelectedMenuItemIdContext';

const SidebarPanelIdContext = React.createContext(null);
const SetSidebarPanelIdContext = React.createContext(() => {});

export function useSetSidebarPanelId() {
	return useContext(SetSidebarPanelIdContext);
}
export function useSidebarPanelId() {
	return useContext(SidebarPanelIdContext);
}

export function SidebarPanelIdProvider({
	children,
	initialSidebarPanelId = null,
}) {
	const selectedMenuItemId = useSelectedMenuItemId();

	const [sidebarPanelId, setSidebarPanelId] = useState(
		selectedMenuItemId
			? SIDEBAR_PANEL_IDS.menuItemSettings
			: initialSidebarPanelId
	);

	return (
		<SetSidebarPanelIdContext.Provider value={setSidebarPanelId}>
			<SidebarPanelIdContext.Provider value={sidebarPanelId}>
				{children}
			</SidebarPanelIdContext.Provider>
		</SetSidebarPanelIdContext.Provider>
	);
}

SidebarPanelIdContext.propTypes = {
	setSidebarPanelId: PropTypes.func,
};
