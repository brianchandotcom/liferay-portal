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

import {UPDATE_SELECTED_SIDEBAR_PANEL_ID} from '../actions/actions.es';
import {setIn} from '../utils/FragmentsEditorUpdateUtils.es';

/**
 * @param {object} state
 * @param {object} action
 * @param {string} action.type
 * @param {string} [action.sidebarPanelId='']
 * @return {object}
 * @review
 */
function updateSelectedSidebarPanelId(state, action) {
	let nextState = state;

	if (action.type === UPDATE_SELECTED_SIDEBAR_PANEL_ID) {
		nextState = setIn(
			nextState,
			['selectedSidebarPanelId'],
			action.sidebarPanelId || ''
		);
	}

	return nextState;
}

export {updateSelectedSidebarPanelId};
