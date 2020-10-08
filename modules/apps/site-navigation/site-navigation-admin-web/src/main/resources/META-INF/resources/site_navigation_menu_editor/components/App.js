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

import React from 'react';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';

import {SIDEBAR_PANEL_IDS} from '../constants/sidebarPanelIds';
import {ConstantsProvider} from '../contexts/ConstantsContext';
import {ItemsProvider, useItems} from '../contexts/ItemsContext';
import {SelectedMenuItemIdProvider} from '../contexts/SelectedMenuItemIdContext';
import {SidebarPanelIdProvider} from '../contexts/SidebarPanelIdContext';
import {AppLayout} from './AppLayout';
import DragPreview from './DragPreview';
import {EmptyState} from './EmptyState';
import {Menu} from './Menu';
import {MenuItemSettingsPanel} from './MenuItemSettingsPanel';
import {MenuSettingsPanel} from './MenuSettingsPanel';
import {Toolbar} from './Toolbar';

const SIDEBAR_PANELS = [
	{
		component: MenuItemSettingsPanel,
		sidebarPanelId: SIDEBAR_PANEL_IDS.menuItemSettings,
	},
	{
		component: MenuSettingsPanel,
		sidebarPanelId: SIDEBAR_PANEL_IDS.menuSettings,
	},
];

export function App(props) {
	const {siteNavigationMenuItems} = props;

	return (
		<DndProvider backend={HTML5Backend}>
			<ConstantsProvider constants={props}>
				<ItemsProvider initialItems={siteNavigationMenuItems}>
					<DragPreview />
					<SelectedMenuItemIdProvider>
						<SidebarPanelIdProvider>
							<AppLayoutWrapper />
						</SidebarPanelIdProvider>
					</SelectedMenuItemIdProvider>
				</ItemsProvider>
			</ConstantsProvider>
		</DndProvider>
	);
}

const AppLayoutWrapper = () => (
	<AppLayout
		contentChildren={useItems().length ? <Menu /> : <EmptyState />}
		sidebarPanels={SIDEBAR_PANELS}
		toolbarChildren={<Toolbar />}
	/>
);
