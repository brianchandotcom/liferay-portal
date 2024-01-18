/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export interface BaseAction {
	type: string;
}
export interface Reducer<State, Action extends BaseAction> {
	(state: State, action: Action): State;
}
export default function loadReducer<State, Action extends BaseAction>(
	reducer: Reducer<State, Action>,
	key: string
): {
	readonly key: string;
	readonly reducer: Reducer<State, Action>;
	readonly type: 'LOAD_REDUCER';
};
