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
import React, {useCallback, useContext, useEffect, useState} from 'react';

const ToggleSidebarContext = React.createContext(() => {});
export const useToggleSidebar = () => useContext(ToggleSidebarContext);

export const AppLayout = ({
	autoOpenSidebar = true,
	contentChildren,
	sidebarChildren,
	toolbarChildren,
}) => {
	const [showSidebar, setShowSidebar] = useState(autoOpenSidebar);

	const toggleSidebar = useCallback(
		(force) =>
			setShowSidebar((show) =>
				typeof force === 'undefined' ? !show : !!force
			),
		[]
	);

	useEffect(() => {
		const {removeListener} = onProductMenuOpen(() => {
			toggleSidebar(false);
		});

		return () => {
			removeListener();
		};
	}, [toggleSidebar]);

	useEffect(() => {
		if (showSidebar) {
			closeProductMenu();
		}
	}, [showSidebar]);

	return (
		<ToggleSidebarContext.Provider value={toggleSidebar}>
			<div className="bg-white component-tbar tbar">
				<div className="container-fluid container-fluid-max-xl">
					<div className="px-1 tbar-nav">{toolbarChildren}</div>
				</div>
			</div>

			<div
				className={classNames(
					'align-items-stretch d-flex site_navigation_menu_editor_AppLayout-content',
					{
						'site_navigation_menu_editor_AppLayout-content--with-sidebar': showSidebar,
					}
				)}
			>
				<div className="flex-grow-1">{contentChildren}</div>

				<div
					className={classNames(
						'site_navigation_menu_editor_AppLayout-sidebar',
						{
							'site_navigation_menu_editor_AppLayout-sidebar--visible': showSidebar,
						}
					)}
				>
					{sidebarChildren}
				</div>
			</div>
		</ToggleSidebarContext.Provider>
	);
};

AppLayout.propTypes = {
	autoOpenSidebar: PropTypes.bool,
	contentChildren: PropTypes.node,
	sidebarChildren: PropTypes.node,
	toolbarChildren: PropTypes.node,
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
