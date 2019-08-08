import {updateWidgets} from '../utils/FragmentsEditorUpdateUtils.es';

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

/**
 * @param {object} state
 * @param {object} action
 * @param {Array} action.fragmentEntryLinkIds
 * @param {string} action.type
 * @return {object}
 * @review
 */
function updateWigetsReducer(state, action) {
	let nextState = state;

	nextState = updateWidgets(state, action.fragmentEntryLinkIds);

	return nextState;
}

export {updateWigetsReducer};
