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
import React, {useEffect, useMemo, useRef} from 'react';

import {APP_LAYOUT_CONTENT_CLASS_NAME} from '../constants/appLayoutClassName';
import {useConstants} from '../contexts/ConstantsContext';
import {
	useSetSidebarPanelId,
	useSidebarPanelId,
} from '../contexts/SidebarPanelIdContext';
import DragPreview from './DragPreview';
import KeyboardMovementText from './KeyboardMovementText';

const DEFAULT_SIDEBAR_PANELS = [];

export function AppLayout({
	contentChildren,
	sidebarPanels = DEFAULT_SIDEBAR_PANELS,
	toolbarChildren,
	sidebarPanelRef,
}) {
	const setSidebarPanelId = useSetSidebarPanelId();
	const sidebarPanelId = useSidebarPanelId();

	const {portletNamespace} = useConstants();

	const SidebarPanel = useMemo(
		() =>
			sidebarPanels?.find(
				(sidebarPanel) => sidebarPanel.sidebarPanelId === sidebarPanelId
			)?.component,
		[sidebarPanelId, sidebarPanels]
	);

	const appLayoutContentRef = useRef();

	useEffect(() => {
		const handler = onProductMenuOpen(() => setSidebarPanelId(null));

		return () => {
			handler.removeListener();
		};
	}, [setSidebarPanelId]);

	useEffect(() => {
		if (SidebarPanel) {
			closeProductMenu();
		}
	}, [SidebarPanel]);

	useEffect(() => {
		const key = `${portletNamespace}itemAdded`;

		const itemAdded = window.sessionStorage.getItem(key);

		if (itemAdded) {
			appLayoutContentRef.current?.scrollTo(
				0,
				appLayoutContentRef.current?.scrollHeight
			);

			window.sessionStorage.removeItem(key);
		}
	}, [portletNamespace]);

	return (
		<>
			<div className="bg-white component-tbar tbar">
				<div className="container-fluid container-fluid-max-xl">
					<div className="cadmin px-1 tbar-nav">
						{toolbarChildren}
					</div>
				</div>
			</div>

			<div
				className={classNames(APP_LAYOUT_CONTENT_CLASS_NAME, {
					[`${APP_LAYOUT_CONTENT_CLASS_NAME}--with-sidebar`]: !!SidebarPanel,
				})}
				ref={appLayoutContentRef}
			>
				<DragPreview wrapperRef={appLayoutContentRef} />

				{contentChildren}

				<div
					className={classNames(
						'site_navigation_menu_editor_AppLayout-sidebar',
						{
							'site_navigation_menu_editor_AppLayout-sidebar--visible': !!SidebarPanel,
						}
					)}
					ref={sidebarPanelRef}
					tabIndex={-1}
				>
					{SidebarPanel && <SidebarPanel />}
				</div>

				<KeyboardMovementText />
			</div>
		</>
	);
}

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
