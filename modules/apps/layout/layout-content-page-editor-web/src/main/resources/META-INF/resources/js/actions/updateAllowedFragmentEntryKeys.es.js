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

import {UPDATE_ALLOWED_FRAGMENT_ENTRY_KEYS} from './actions.es';
import {updatePageEditorLayoutDataAction} from './updatePageEditorLayoutData.es';

/**
 * @param {string} allowedFragmentEntryKeys
 * @review
 */
function updateAllowedFragmentEntryKeys(allowedFragmentEntryKeys) {
	return function(dispatch) {
		dispatch(_updateAllowedFragmentEntryKeys(allowedFragmentEntryKeys));

		return dispatch(updatePageEditorLayoutDataAction());
	};
}

function _updateAllowedFragmentEntryKeys(allowedFragmentEntryKeys) {
	return {
		allowedFragmentEntryKeys,
		type: UPDATE_ALLOWED_FRAGMENT_ENTRY_KEYS
	};
}

export {updateAllowedFragmentEntryKeys};
