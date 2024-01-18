/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {Action, State} from '../reducers';
export declare type Dispatch = (action: Action) => void;
export declare type GetState = () => State;
export declare type Thunk = (dispatch: Dispatch, getState: GetState) => void;
export declare type Reducer = (state: State, action: Action) => State;

/**
 * Although StoreContextProvider creates a full functional store,
 * sometimes mocking dispatchs and/or store state may be necessary
 * for testing purposes.
 *
 * This component wraps it's children with an usable StoreContext
 * that calls dispatch and getState methods instead of using a real
 * reducer internally.
 */
export declare function StoreAPIContextProvider({
	children,
	dispatch,
	getState,
}: {
	children: React.ReactNode;
	dispatch: Dispatch;
	getState: GetState;
}): JSX.Element;

/**
 * StoreContext is a black box for components: they should
 * get information from state and dispatch actions by using
 * given useSelector and useDispatch hooks.
 *
 * That's why we only provide a custom StoreContextProvider instead
 * of the raw React context.
 */
export declare function StoreContextProvider({
	children,
	initialState,
	reducer,
}: {
	children: React.ReactNode;
	initialState: State;
	reducer: Reducer;
}): JSX.Element;

/**
 * @see https://react-redux.js.org/api/hooks#usedispatch
 */
export declare function useDispatch(): Dispatch;
export declare function useGetState(): GetState;
export declare function useSelectorCallback<Result>(
	selector: (state: State) => Result,
	dependencies: any[],
	compareEqual?: (a: any, b: any) => boolean
): Result;

/**
 * @see https://react-redux.js.org/api/hooks#useselector
 */
export declare function useSelector<Result>(
	selector: (state: State) => Result,
	compareEqual?: (a: any, b: any) => boolean
): Result;
export declare function useSelectorRef<Result>(
	selector: (state: State) => Result
): React.MutableRefObject<Result | null>;
