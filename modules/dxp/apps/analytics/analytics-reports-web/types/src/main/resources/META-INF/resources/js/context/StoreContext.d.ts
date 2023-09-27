/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
interface Action {
	type: 'ADD_WARNING' | null;
}
interface State {
	publishedToday: boolean;
	warning: boolean;
}
export declare const StoreDispatchContext: React.Context<React.Dispatch<
	Action
>>;
export declare const StoreStateContext: React.Context<State>;
interface Props {
	children: React.ReactNode;
	value: object;
}
export declare function StoreContextProvider({
	children,
	value,
}: Props): JSX.Element;
export {};
