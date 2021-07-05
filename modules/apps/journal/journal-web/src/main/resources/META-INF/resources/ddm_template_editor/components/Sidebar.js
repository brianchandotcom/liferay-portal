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
import React from 'react';

import {ElementsSidebarPanel} from './ElementsSidebarPanel';
import PropertiesSidebarPanel from './PropertiesSidebarPanel';

const PANEL_IDS = {
	elements: 'elements',
	properties: 'properties',
};

export default function Sidebar({
	selectedSidebarPanelId,
	setSelectedSidebarPanelId,
}) {
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
			<div className="ddm_template_editor__App-sidebar-buttons pt-1">
				<ClayButtonWithIcon
					borderless
					className="mb-2"
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
				/>

				<ClayButtonWithIcon
					borderless
					className="mb-2"
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
				/>
			</div>
		</div>
	);
}
