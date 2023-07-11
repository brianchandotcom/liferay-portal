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

import {isNullOrUndefined} from '@liferay/layout-js-components-web';

import {SWITCH_SIDEBAR_PANEL} from '../actions/types';

const DEFAULT_PANEL_ID = 'fragments_and_widgets';

export const INITIAL_STATE = {
	hidden: false,
	itemConfigurationOpen: false,
	open: false,
	panelId: DEFAULT_PANEL_ID,
};

export default function sidebarReducer(sidebarStatus = INITIAL_STATE, action) {
	if (action.type === SWITCH_SIDEBAR_PANEL) {
		return {
			hidden: action.hidden,
			itemConfigurationOpen: action.itemConfigurationOpen,
			open: isNullOrUndefined(action.sidebarOpen)
				? sidebarStatus.open
				: action.sidebarOpen,
			panelId:
				action.sidebarPanelId === undefined
					? sidebarStatus.panelId
					: action.sidebarPanelId,
		};
	}

	return sidebarStatus;
}
