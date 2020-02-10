/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import React from 'react';

export function reducer(state, action) {
	switch (action.type) {
		case 'activate':
		case 'deactivate':
			{
				const mode = {activate: 'active', deactivate: 'inactive'}[
					action.type
				];

				if (state.mode !== mode) {
					return {
						...state,
						mode
					};
				}
			}
			break;

		case 'editTarget':
			return {
				...state,
				editingTarget: action.selector
			};

		case 'selectTarget':
			return {
				...state,
				editingTarget: null,
				mode: 'active',
				selectedTarget: action.selector
			};

		default:
	}

	return state;
}

export function getInitialState(target) {
	return {
		...INITIAL_STATE,
		selectedTarget: target
	};
}

const INITIAL_STATE = {
	/**
	 * The click goal target that is currently being edited.
	 *
	 * A popover is shown with information about the target and a "Set Element
	 * as Click Target" button.
	 *
	 * Note that it is possible to have one target selected (see below), while
	 * simultaneously editing another; on hitting "Set Element as Click Target",
	 * the `editingTarget` becomes the new `selectedTarget`.
	 */
	editingTarget: '',

	/**
	 * The mode of the component, which will be one of:
	 *
	 * - "inactive": the user is not interacting with the component; or
	 * - "active": the user is selecting or editing a component.
	 *
	 * In "active" mode, the component effectively takes over the screen,
	 * capturing all clicks until the user finishes their selection by
	 * selecting or deleting a target, or by pressing "Escape".
	 */
	mode: 'inactive',

	/**
	 * The click goal target that is currently selected.
	 *
	 * A topper is shown above the target containing a "times" (x) icon that can
	 * be used to unset the target.
	 *
	 * As noted above, it is possible to have one target selected and another
	 * being edited at the same time.
	 */
	selectedTarget: ''
};

export const StateContext = React.createContext(INITIAL_STATE);
