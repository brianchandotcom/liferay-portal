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
export declare const initialState: State;
export declare function dataReducer(
	state: State,
	action: Action
):
	| State
	| {
			error: string | undefined;
			loading: boolean;
			data: object | null;
	  };
export {};
