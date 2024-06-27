/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {EVENT_TYPES} from '../actions/eventTypes.es';

export default function historyReducer(state, action) {
	switch (action.type) {
		case EVENT_TYPES.HISTORY.ADD:
			return {
				history: {
					...state.history,
					currentStep: state.history.currentStep + 1,
					steps: [
						...state.history.steps.slice(
							0,
							state.history.currentStep + 1
						),
						state,
					],
				},
			};
		case EVENT_TYPES.HISTORY.BLUR:
			if (state.history.edited) {
				Liferay.fire('journal:storeState', {fieldName: action.payload});
			}

			return {
				history: {
					...state.history,
					edited: false,
				},
			};
		case EVENT_TYPES.HISTORY.EDITED:
			return {
				history: {
					...state.history,
					edited: true,
				},
			};
		case EVENT_TYPES.HISTORY.NEXT:
			return {
				...state.history.steps[state.history.currentStep + 1],
				history: {
					...state.history,
					currentStep: state.history.currentStep + 1,
				},
			};
		case EVENT_TYPES.HISTORY.PREV:
			return {
				...state.history.steps[state.history.currentStep - 1],
				history: {
					...state.history,
					currentStep: state.history.currentStep - 1,
				},
			};
		default:
			return state;
	}
}
