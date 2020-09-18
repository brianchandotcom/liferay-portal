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

import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useContext, useEffect, useMemo, useState} from 'react';

const DEFAULT_SIDEBAR_PANELS = [];

const SetSidebarPanelIdContext = React.createContext(() => {});

export const useSetSidebarPanelId = () => {
	return useContext(SetSidebarPanelIdContext);
};

export const AppLayout = ({
	contentChildren,
	initialSidebarPanelId = null,
	sidebarPanels = DEFAULT_SIDEBAR_PANELS,
	toolbarChildren,
}) => {
	const [sidebarPanelId, setSidebarPanelId] = useState(initialSidebarPanelId);

	const SidebarPanel = useMemo(
		() =>
			sidebarPanels?.find(
				(sidebarPanel) => sidebarPanel.sidebarPanelId === sidebarPanelId
			)?.component,
		[sidebarPanels, sidebarPanelId]
	);

	useEffect(() => {
		const {removeListener} = onProductMenuOpen(() => {
			setSidebarPanelId(null);
		});

		return () => {
			removeListener();
		};
	}, []);

	useEffect(() => {
		setSidebarPanelId(null);
	}, [sidebarPanels]);

	useEffect(() => {
		if (SidebarPanel) {
			closeProductMenu();
		}
	}, [SidebarPanel]);

	return (
		<SetSidebarPanelIdContext.Provider value={setSidebarPanelId}>
			<div className="bg-white component-tbar tbar">
				<div className="container-fluid container-fluid-max-xl">
					<div className="px-1 tbar-nav">{toolbarChildren}</div>
				</div>
			</div>

			<div
				className={classNames(
					'align-items-stretch d-flex site_navigation_menu_editor_AppLayout-content',
					{
						'site_navigation_menu_editor_AppLayout-content--with-sidebar': !!SidebarPanel,
					}
				)}
			>
				<div className="flex-grow-1">{contentChildren}</div>

				<div
					className={classNames(
						'site_navigation_menu_editor_AppLayout-sidebar',
						{
							'site_navigation_menu_editor_AppLayout-sidebar--visible': !!SidebarPanel,
						}
					)}
				>
					{SidebarPanel && <SidebarPanel />}
				</div>
			</div>
		</SetSidebarPanelIdContext.Provider>
	);
};

AppLayout.propTypes = {
	contentChildren: PropTypes.node,
	initialSidebarPanelId: PropTypes.string,
	sidebarPanels: PropTypes.arrayOf(
		PropTypes.shape({
			component: PropTypes.func.isRequired,
			sidebarPanelId: PropTypes.string.isRequired,
		})
	),
	toolbarChildren: PropTypes.node,
};

AppLayout.ToolbarItem = ({children, expand}) => {
	return (
		<li className={classNames('tbar-item', {'tbar-item-expand': expand})}>
			{children}
		</li>
	);
};

AppLayout.ToolbarItem.propTypes = {
	children: PropTypes.node,
	expand: PropTypes.bool,
};

function closeProductMenu() {
	Liferay.SideNavigation.hide(document.querySelector('.product-menu-toggle'));
}

function onProductMenuOpen(fn) {
	return (
		Liferay.SideNavigation.instance(
			document.querySelector('.product-menu-toggle')
		)?.on('openStart.lexicon.sidenav', fn) ?? {removeListener: () => {}}
	);
}
