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

import {UPDATE_ALLOWED_FRAGMENT_ENTRIES} from './actions.es';
import {updatePageEditorLayoutDataAction} from './updatePageEditorLayoutData.es';

/**
 * @param {string} allowedFragmentEntries
 * @review
 */
function updateAllowedFragmentEntries(allowedFragmentEntries) {
	return function(dispatch) {
		dispatch(_updateAllowedFragmentEntries(allowedFragmentEntries));

		return dispatch(updatePageEditorLayoutDataAction());
	};
}

function _updateAllowedFragmentEntries(allowedFragmentEntries) {
	return {
		allowedFragmentEntries,
		type: UPDATE_ALLOWED_FRAGMENT_ENTRIES
	};
}

export {updateAllowedFragmentEntries};
