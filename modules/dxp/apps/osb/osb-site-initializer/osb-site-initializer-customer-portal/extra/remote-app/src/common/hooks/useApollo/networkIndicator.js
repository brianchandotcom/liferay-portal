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

import {ActionTypes} from 'react-apollo-network-status';
import isOperationType from '../../utils/isOperationType';

const initialState = {
	error: undefined,
	success: undefined,
};

const reducer = (state, action) => {
	if (isOperationType(action.payload.operation, 'subscription')) {
		return state;
	}

	switch (action.type) {
		case ActionTypes.ERROR: {
			const {networkError, operation} = action.payload;

			return {
				error: {networkError, operation},
			};
		}
		case ActionTypes.SUCCESS: {
			const {operation, result} = action.payload;

			if (result) {
				if (result.errors) {
					return {
						error: {
							operation,
							response: result.errors.map(
								(error) => error.extensions
							),
						},
					};
				}

				return {
					success: {operation, response: result},
				};
			}

			return state;
		}
		default: {
			return state;
		}
	}
};

export const networkIndicator = {
	initialState,
	reducer,
};
