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

import defaultReducer from './defaultReducer.es';
import fieldReducer from './fieldReducer.es';
import pageReducer from './pageReducer.es';

export const createReducer = (onEvent) => {
	return (state, action) =>
		[defaultReducer, fieldReducer, pageReducer].reduce(
			(prevState, reducer) => {
				const nextProperties = reducer(prevState, action);

				if (prevState === nextProperties) {
					onEvent(action.type, action.payload);

					return nextProperties;
				}

				// The reducer propagates the events so that they can be
				// handled by other components in the upper layer that are
				// not the responsibility of the form renderer, some
				// operations are transformed and treated here, we issue
				// the final result to avoid double operations that are
				// costly.

				let payload = {transformation: nextProperties};

				if (typeof action.payload === 'object') {
					payload = {...payload, ...action.payload};
				}
				else {
					payload = {...payload, value: action.payload};
				}

				onEvent(action.type, payload);

				return {
					...prevState,
					...nextProperties,
				};
			},
			state
		);
};
