/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
export declare enum TYPES {
	CHANGE_PANEL_EXPANDED = 'CHANGE_PANEL_EXPANDED',
}
declare type TState = {
	expanded: boolean;
};
declare type TAction = {
	payload: {
		expanded: boolean;
	};
	type: TYPES.CHANGE_PANEL_EXPANDED;
};
declare type TDispatch = React.Dispatch<
	React.ReducerAction<React.Reducer<TState, TAction>>
>;
declare const initialState: {
	expanded: boolean;
};
interface IPanelContextProps extends Array<TState | TDispatch> {
	0: typeof initialState;
	1: TDispatch;
}
export declare const PanelContext: React.Context<IPanelContextProps>;
export declare function PanelContextProvider({
	children,
}: React.HTMLAttributes<HTMLElement>): JSX.Element;
export declare function usePanelContext(): IPanelContextProps;
export {};
