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

interface State {
	data: object | null;
	error: string | null;
	loading: boolean;
}

interface Action {
	data?: ActionData;
	error?: string;
	type: 'LOAD_DATA' | 'SET_ERROR' | 'SET_DATA' | null;
}

interface ActionData {
	error: string;
	publishDate: string;
}

export const initialState: State = {
	data: null,
	error: null,
	loading: false,
};

export function dataReducer(state: State, action: Action) {
	switch (action.type) {
		case 'LOAD_DATA':
			return {
				...state,
				loading: true,
			};

		case 'SET_ERROR':
			return {
				...state,
				error: action.error,
				loading: false,
			};

		case 'SET_DATA':
			return {
				data: {
					...action.data,
					publishedToday:
						action.data &&
						new Date().toDateString() ===
							new Date(action.data.publishDate).toDateString(),
				},
				error: action.data?.error,
				loading: false,
			};

		default:
			return initialState;
	}
}
