/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
const KEY_CODES_TO_ACTIONS: {[key: number]: KeyActionType} = {
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

export default function useKeyboardNavigation(nodeId: string) {
	const {dispatch, state} = useContext(TreeviewContext);
	const {focusedNodeId} = state;

	const handleKeyDown = useCallback(
		(event: React.KeyboardEvent) => {
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

type KeyActionType =
	| 'COLLAPSE_PARENT'
	| 'EXIT'
	| 'EXPAND_ALL'
	| 'EXPAND_AND_ENTER'
	| 'SELECT_LAST_VISIBLE'
	| 'SELECT_NEXT_VISIBLE'
	| 'SELECT_PREVIOUS_VISIBLE'
	| 'SELECT_ROOT'
	| 'TOGGLE_EXPANDED'
	| 'TOGGLE_SELECT';
