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

import {deleteIn, setIn} from '../utils/FragmentsEditorUpdateUtils.es';

/**
 * @param {string|string[]} keyPath
 * @return {function}
 */
function createSetValueReducer(keyPath) {
	const path = Array.isArray(keyPath) ? keyPath : [keyPath];

	/**
	 * @type {function}
	 * @param {object} state
	 * @param {object} action
	 * @param {any} [action.value]
	 */
	return (state, action) => {
		let nextState = state;

		if ('value' in action) {
			if (!nextState.lockedSegmentsExperience) {
				nextState = setIn(nextState, path, action.value);
			}
		}
		else {
			nextState = deleteIn(nextState, path);
		}

		return nextState;
	};
}

export {createSetValueReducer};
