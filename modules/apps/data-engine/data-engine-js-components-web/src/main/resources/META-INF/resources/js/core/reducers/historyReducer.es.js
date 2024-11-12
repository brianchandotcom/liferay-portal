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
				Liferay.fire('journal:unlock');
				Liferay.fire('journal:storeState', {
					fieldName:
						Liferay.Language.get('edit') + ' ' + action.payload,
				});
			}

			return {
				history: {
					...state.history,
					edited: false,
				},
			};
		case EVENT_TYPES.HISTORY.GOTO:
			setTimeout(() => Liferay.fire('ddm:restoreState'), 100);

			return {
				...state.history.steps[action.step],
				history: {
					...state.history,
					currentStep: action.step,
				},
			};
		case EVENT_TYPES.HISTORY.MARK:
			Liferay.fire('journal:lock');

			return {
				history: {
					...state.history,
					edited: true,
				},
			};
		case EVENT_TYPES.HISTORY.NEXT:
			setTimeout(() => Liferay.fire('ddm:restoreState'), 100);

			return {
				...state.history.steps[state.history.currentStep + 1],
				history: {
					...state.history,
					currentStep: state.history.currentStep + 1,
				},
			};
		case EVENT_TYPES.HISTORY.PREV:
			setTimeout(() => Liferay.fire('ddm:restoreState'), 100);

			return {
				...state.history.steps[state.history.currentStep - 1],
				history: {
					...state.history,
					currentStep: state.history.currentStep - 1,
				},
			};
		case EVENT_TYPES.HISTORY.RESET:
			return {
				history: {
					...state.history,
					currentStep: 0,
					steps: [state],
				},
			};
		case EVENT_TYPES.HISTORY.UNMARK:
			return {
				history: {
					...state.history,
					edited: false,
				},
			};
		default:
			return state;
	}
}
