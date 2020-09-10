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

export function combineReducers(reducers) {
	const reducerKeys = Object.keys(reducers);

	return function combineReducers(state = {}, action) {
		const nextState = {};

		let didChange = false;

		reducerKeys.forEach((key) => {
			const reducer = reducers[key],
				previousStateForKey = state[key],
				nextStateForKey = reducer(previousStateForKey, action);

			if (typeof nextStateForKey === 'undefined') {
				throw new Error(`The reducer for "${key}" is undefined`);
			}

			nextState[key] = nextStateForKey;

			if (nextStateForKey !== previousStateForKey) {
				didChange = true;
			}
		});

		return didChange ? nextState : state;
	};
}
