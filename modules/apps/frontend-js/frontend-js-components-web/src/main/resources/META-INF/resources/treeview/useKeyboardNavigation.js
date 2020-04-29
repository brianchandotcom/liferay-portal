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

import {useCallback, useContext} from 'react';

import TreeviewContext from './TreeviewContext';

/**
 * @see https://developer.mozilla.org/en-US/docs/Web/API/KeyboardEvent/keyCode
 */
const KEYS = {
	DOWN: 40,
	END: 35,
	ENTER: 13,
	HOME: 36,
	LEFT: 37,
	NUMPAD_STAR: 106,
	RIGHT: 39,
	SPACE: 32,
	TAB: 9,
	UP: 38,
};

const HANDLED_KEY_CODES = new Set(Object.values(KEYS));

/**
 * Manage transitions according to accessibility recommendations here:
 *
 * http://oaa-accessibility.org/examplep/treeview1/
 *
 * Plus "SPACE", which is the default key for toggling checkboxes.
 */
const KEY_CODES_TO_ACTIONS = {
	[KEYS.DOWN]: 'SELECT_NEXT_VISIBLE',
	[KEYS.END]: 'SELECT_LAST_VISIBLE',
	[KEYS.ENTER]: 'TOGGLE_EXPANDED',
	[KEYS.HOME]: 'SELECT_ROOT',
	[KEYS.LEFT]: 'COLLAPSE_PARENT',
	[KEYS.NUMPAD_STAR]: 'EXPAND_ALL',
	[KEYS.RIGHT]: 'EXPAND_AND_ENTER',
	[KEYS.SPACE]: 'TOGGLE_SELECT',
	[KEYS.TAB]: 'EXIT',
	[KEYS.UP]: 'SELECT_PREVIOUS_VISIBLE',
};

export default function useKeyboardNavigation(nodeId) {
	const {dispatch, state} = useContext(TreeviewContext);
	const {focusedNodeId} = state;

	const handleKeyDown = useCallback(
		(event) => {
			const {keyCode} = event;

			if (focusedNodeId === nodeId && HANDLED_KEY_CODES.has(keyCode)) {
				if (keyCode !== KEYS.TAB) {
					// We intercept and manage all key presses internally,
					// except for TAB, which is used to navigate away from the
					// component (requires a `tabindex` of -1 on all internal
					// components).

					event.preventDefault();
				}

				dispatch({
					nodeId,
					type: KEY_CODES_TO_ACTIONS[keyCode],
				});
			}
		},
		[dispatch, focusedNodeId, nodeId]
	);

	return handleKeyDown;
}
