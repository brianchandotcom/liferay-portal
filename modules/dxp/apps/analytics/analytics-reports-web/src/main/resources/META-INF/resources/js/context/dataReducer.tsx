/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
