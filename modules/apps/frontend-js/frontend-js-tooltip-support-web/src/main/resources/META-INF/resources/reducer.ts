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
	IDLE: {show: false},
	SHOW: {show: true},
	WAIT_HIDE: {show: true},
	WAIT_RESHOW: {show: true},
	WAIT_SHOW: {show: false},
};

export {STATES};

interface State {
	current: {show: boolean};
	nextTarget?: HTMLElement;
	target?: HTMLElement;
	timestamp?: number;
}

type Action =
	| {type: 'hide'}
	| {type: 'hideDelayCompleted'}
	| {type: 'showDelayCompleted'}
	| {type: 'show'; target?: HTMLElement};

export default function reducer(state: State, action: Action): State {
	switch (action.type) {
		case 'show':
			if (state.current === STATES.IDLE) {
				return {
					...state,
					current: STATES.WAIT_SHOW,
					target: action.target,
					timestamp: Date.now(),
				};
			}
			else if (state.current === STATES.WAIT_SHOW) {
				return {
					...state,
					target: action.target,
					timestamp: Date.now(),
				};
			}
			else if (state.current === STATES.WAIT_RESHOW) {
				return {
					...state,
					nextTarget: action.target,
					timestamp: Date.now(),
				};
			}
			else if (state.current === STATES.WAIT_HIDE) {
				return {
					...state,
					current: STATES.WAIT_RESHOW,
					nextTarget: action.target,
				};
			}
			else {
				return {
					...state,
					timestamp: Date.now(),
				};
			}

		case 'hideDelayCompleted':
			if (state.current === STATES.WAIT_HIDE) {
				return {current: STATES.IDLE};
			}

			break;

		case 'showDelayCompleted':
			if (state.current === STATES.WAIT_SHOW) {
				return {
					...state,
					current: STATES.SHOW,
				};
			}
			else if (state.current === STATES.WAIT_RESHOW) {
				return {
					...state,
					current: STATES.SHOW,
					target: state.nextTarget,
				};
			}

			break;

		case 'hide':
			if (state.current === STATES.WAIT_SHOW) {
				return {current: STATES.IDLE};
			}
			else if (
				state.current === STATES.SHOW ||
				state.current === STATES.WAIT_RESHOW
			) {
				return {
					...state,
					current: STATES.WAIT_HIDE,
					timestamp: Date.now(),
				};
			}

			break;

		default:
			return state;
	}

	return state;
}
