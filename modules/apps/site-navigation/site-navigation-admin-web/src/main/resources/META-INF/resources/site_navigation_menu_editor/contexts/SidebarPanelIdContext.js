/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
