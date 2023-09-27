/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import './App.scss';

import React, {useRef} from 'react';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';

import {SIDEBAR_PANEL_IDS} from '../constants/sidebarPanelIds';
import {ConstantsProvider} from '../contexts/ConstantsContext';
import {ItemsProvider, useItems} from '../contexts/ItemsContext';
import {KeyboardDndProvider} from '../contexts/KeyboardDndContext';
import {SelectedMenuItemIdProvider} from '../contexts/SelectedMenuItemIdContext';
import {SidebarPanelIdProvider} from '../contexts/SidebarPanelIdContext';
import decorateAddSiteNavigationMenuItemOptions from '../utils/decorateAddSiteNavigationMenuItemOptions';
import {DragDropProvider} from '../utils/useDragAndDrop';
import {AppLayout} from './AppLayout';
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
	const {
		addSiteNavigationMenuItemOptions,
		portletNamespace,
		siteNavigationMenuItems,
	} = props;

	return (
		<DndProvider backend={HTML5Backend}>
			<ConstantsProvider
				constants={{
					...props,
					addSiteNavigationMenuItemOptions: decorateAddSiteNavigationMenuItemOptions(
						{addSiteNavigationMenuItemOptions, portletNamespace}
					),
					portletNamespace,
				}}
			>
				<ItemsProvider initialItems={siteNavigationMenuItems}>
					<KeyboardDndProvider>
						<DragDropProvider>
							<SelectedMenuItemIdProvider>
								<SidebarPanelIdProvider>
									<AppLayoutWrapper />
								</SidebarPanelIdProvider>
							</SelectedMenuItemIdProvider>
						</DragDropProvider>
					</KeyboardDndProvider>
				</ItemsProvider>
			</ConstantsProvider>
		</DndProvider>
	);
}

const AppLayoutWrapper = () => {
	const configButtonRef = useRef(null);
	const sidebarPanelRef = useRef(null);

	return (
		<AppLayout
			configButtonRef={configButtonRef}
			contentChildren={
				useItems().length ? (
					<Menu sidebarPanelRef={sidebarPanelRef} />
				) : (
					<EmptyState />
				)
			}
			sidebarPanelRef={sidebarPanelRef}
			sidebarPanels={SIDEBAR_PANELS}
			toolbarChildren={
				<Toolbar
					configButtonRef={configButtonRef}
					sidebarPanelRef={sidebarPanelRef}
				/>
			}
		/>
	);
};
