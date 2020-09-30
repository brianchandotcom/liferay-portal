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
import React from 'react';

import {SIDEBAR_PANEL_IDS} from '../constants/sidebarPanelIds';
import {useSetSidebarPanelId} from '../contexts/SidebarPanelIdContext';
import {AddItemDropDown} from './AddItemDropdown';
import {AppLayout} from './AppLayout';

export const Toolbar = () => {
	const setSidebarPanelId = useSetSidebarPanelId();

	const onSettingsButtonClick = () => {
		setSidebarPanelId(SIDEBAR_PANEL_IDS.menuSettings);
	};

	return (
		<>
			<AppLayout.ToolbarItem expand />

			<AppLayout.ToolbarItem>
				<ClayButtonWithIcon
					displayType="unstyled"
					monospaced
					onClick={onSettingsButtonClick}
					small
					symbol="cog"
				/>
			</AppLayout.ToolbarItem>

			<AppLayout.ToolbarItem>
				<AddItemDropDown
					trigger={
						<ClayButtonWithIcon monospaced small symbol="plus" />
					}
				/>
			</AppLayout.ToolbarItem>
		</>
	);
};
