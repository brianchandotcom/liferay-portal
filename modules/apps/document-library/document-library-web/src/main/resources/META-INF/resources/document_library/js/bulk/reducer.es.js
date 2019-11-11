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

const STATES = {
	IDLE: {running: false, show: false},
	LONG_POLLING: {running: true, show: true},
	NOTIFY: {running: false, show: false},
	SHORT_POLLING: {running: true, show: false}
};

const TOASTS = {
	ERROR: {
		message: Liferay.Language.get('an-unexpected-error-occurred'),
		title: Liferay.Language.get('error'),
		type: 'danger'
	},
	SUCCESS: {
		message: Liferay.Language.get('changes-saved')
	}
};

export {STATES};

export default function reducer(state, action) {
	switch (action.type) {
		case 'check':
			if (state.current === STATES.LONG_POLLING) {
				return {
					...state,
					timestamp: Date.now()
				};
			}
			break;

		case 'error':
			return {
				...state,
				current: STATES.NOTIFY,
				toast: TOASTS.ERROR
			};

		case 'initialDelayCompleted':
			if (state.current === STATES.SHORT_POLLING) {
				return {
					...state,
					current: STATES.LONG_POLLING,
					timestamp: Date.now()
				};
			}

			break;

		case 'notificationCompleted':
			if (state.current === STATES.NOTIFY) {
				return {
					...state,
					current: STATES.IDLE
				};
			}

			break;

		case 'start':
			if (state.current === STATES.IDLE) {
				return {
					...state,
					current: STATES.SHORT_POLLING
				};
			}

			break;

		case 'success':
			return {
				...state,
				current: STATES.NOTIFY,
				toast: TOASTS.SUCCESS
			};

		default:
			return state;
	}

	return state;
}
