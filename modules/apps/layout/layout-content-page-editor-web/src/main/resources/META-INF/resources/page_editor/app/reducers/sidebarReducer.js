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

import {SWITCH_SIDEBAR_PANEL} from '../actions/types';
import {config} from '../config/index';

const DEFAULT_PANEL_ID = 'fragments';

export const INITIAL_STATE = {
	open: true,
	panelId:
		(config.panels && config.panels[0] && config.panels[0][0]) ||
		DEFAULT_PANEL_ID
};

export default function sidebarReducer(sidebarStatus = INITIAL_STATE, action) {
	if (action.type === SWITCH_SIDEBAR_PANEL) {
		return {
			open: action.sidebarOpen,
			panelId: action.sidebarPanelId
		};
	}

	return sidebarStatus;
}
