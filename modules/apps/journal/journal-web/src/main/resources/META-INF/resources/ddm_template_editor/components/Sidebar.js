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

import {ClayButtonWithIcon} from '@clayui/button';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useEffect} from 'react';

import {ElementsSidebarPanel} from './ElementsSidebarPanel';
import PropertiesSidebarPanel from './PropertiesSidebarPanel';
import {PANEL_IDS} from './panelIds';

export default function Sidebar({
	selectedSidebarPanelId,
	setSelectedSidebarPanelId,
}) {
	useEffect(() => {
		const sideNavigation = Liferay.SideNavigation.instance(
			document.querySelector('.product-menu-toggle')
		);

		if (sideNavigation) {
			if (selectedSidebarPanelId && sideNavigation.visible()) {
				sideNavigation.hide();
			}

			const sideNavigationListener = sideNavigation.on(
				'openStart.lexicon.sidenav',
				() => setSelectedSidebarPanelId(null)
			);

			return () => {
				sideNavigationListener.removeListener();
			};
		}
	}, [selectedSidebarPanelId, setSelectedSidebarPanelId]);

	return (
		<div className="ddm_template_editor__App-sidebar">
			<div
				className={classNames(
					'ddm_template_editor__App-sidebar-content',
					{
						open: selectedSidebarPanelId,
					}
				)}
			>
				<ElementsSidebarPanel
					className={classNames({
						'd-none': PANEL_IDS.elements !== selectedSidebarPanelId,
					})}
				/>

				<PropertiesSidebarPanel
					className={classNames({
						'd-none':
							PANEL_IDS.properties !== selectedSidebarPanelId,
					})}
				/>
			</div>
			<div className="ddm_template_editor__App-sidebar-buttons pt-3">
				<ClayButtonWithIcon
					aria-pressed={PANEL_IDS.elements === selectedSidebarPanelId}
					borderless
					className={classNames('mb-2', {
						active: PANEL_IDS.elements === selectedSidebarPanelId,
					})}
					data-tooltip-align="left"
					displayType="secondary"
					monospaced
					onClick={() => {
						setSelectedSidebarPanelId((selectedSidebarPanelId) =>
							selectedSidebarPanelId === PANEL_IDS.elements
								? null
								: PANEL_IDS.elements
						);
					}}
					outline
					symbol="list-ul"
					title={Liferay.Language.get('elements')}
				/>

				<ClayButtonWithIcon
					aria-pressed={
						PANEL_IDS.properties === selectedSidebarPanelId
					}
					borderless
					className={classNames('mb-2', {
						active: PANEL_IDS.properties === selectedSidebarPanelId,
					})}
					data-tooltip-align="left"
					displayType="secondary"
					monospaced
					onClick={() => {
						setSelectedSidebarPanelId((selectedSidebarPanelId) =>
							selectedSidebarPanelId === PANEL_IDS.properties
								? null
								: PANEL_IDS.properties
						);
					}}
					outline
					symbol="cog"
					title={Liferay.Language.get('properties')}
				/>
			</div>
		</div>
	);
}

Sidebar.propTypes = {
	selectedSidebarPanelId: PropTypes.string,
	setSelectedSidebarPanelId: PropTypes.func.isRequired,
};
