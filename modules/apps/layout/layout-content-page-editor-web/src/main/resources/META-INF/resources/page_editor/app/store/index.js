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

import React, {
	useCallback,
	useContext,
	useEffect,
	useMemo,
	useReducer,
	useRef,
	useState,
} from 'react';

import useThunk from '../../core/hooks/useThunk';
import useUndo from '../components/undo/useUndo';

const StoreDispatchContext = React.createContext(() => {});
const StoreGetStateContext = React.createContext(null);
const StoreSubscriptionContext = React.createContext([() => {}, () => {}]);

const DEFAULT_COMPARE_EQUAL = (a, b) => a === b;
const DEFAULT_DISPATCH = () => {};
const DEFAULT_GET_STATE = () => ({});

/**
 * Although StoreContextProvider creates a full functional store,
 * sometimes mocking dispatchs and/or store state may be necessary
 * for testing purposes.
 *
 * This component wraps it's children with an usable StoreContext
 * that calls dispatch and getState methods instead of using a real
 * reducer internally.
 */
export const StoreAPIContextProvider = ({
	children,
	dispatch = DEFAULT_DISPATCH,
	getState = DEFAULT_GET_STATE,
	stateChanged,
}) => {
	const subscribers = useRef(new Set());

	const subscribe = useCallback((subscriber) => {
		subscribers.current.add(subscriber);
	}, []);

	const unsubscribe = useCallback((subscriber) => {
		subscribers.current.delete(subscriber);
	}, []);

	const subscriptionContext = useMemo(() => [subscribe, unsubscribe], [
		subscribe,
		unsubscribe,
	]);

	useEffect(() => {
		subscribers.current.forEach((subscriber) => subscriber());
	}, [stateChanged]);

	return (
		<StoreSubscriptionContext.Provider value={subscriptionContext}>
			<StoreDispatchContext.Provider value={dispatch}>
				<StoreGetStateContext.Provider value={getState}>
					{children}
				</StoreGetStateContext.Provider>
			</StoreDispatchContext.Provider>
		</StoreSubscriptionContext.Provider>
	);
};

/**
 * StoreContext is a black box for components: they should
 * get information from state and dispatch actions by using
 * given useSelector and useDispatch hooks.
 *
 * That's why we only provide a custom StoreContextProvider instead
 * of the raw React context.
 */
export const StoreContextProvider = ({children, initialState, reducer}) => {
	const [state, dispatch] = useThunk(
		useUndo(useReducer(reducer, initialState))
	);

	const stateRef = useRef(state);
	const getState = useCallback(() => stateRef.current, []);

	useEffect(() => {
		stateRef.current = state;
	}, [state]);

	return (
		<StoreAPIContextProvider
			dispatch={dispatch}
			getState={getState}
			stateChanged={state}
		>
			{children}
		</StoreAPIContextProvider>
	);
};

/**
 * @see https://react-redux.js.org/api/hooks#usedispatch
 */
export const useDispatch = () => useContext(StoreDispatchContext);

/**
 * @see https://react-redux.js.org/api/hooks#useselector
 */
export const useSelector = (selector, compareEqual = DEFAULT_COMPARE_EQUAL) => {
	const getState = useContext(StoreGetStateContext);
	const [subscribe, unsubscribe] = useContext(StoreSubscriptionContext);

	const initialState = useMemo(
		() => selector(getState()),

		// We really want to call selector here just on component mount.
		// This provides an initial value that will be recalculated when
		// store suscription has been called.
		// eslint-disable-next-line
		[]
	);

	const [selectorState, setSelectorState] = useState(initialState);
	const selectorCallback = useCallback(selector, []);

	useEffect(() => {
		const onStoreChange = () => {
			const nextState = selectorCallback(getState());

			if (!compareEqual(selectorState, nextState)) {
				setSelectorState(nextState);
			}
		};

		subscribe(onStoreChange);

		return () => {
			unsubscribe(onStoreChange);
		};
	}, [
		compareEqual,
		getState,
		selectorCallback,
		selectorState,
		subscribe,
		unsubscribe,
	]);

	return selectorState;
};

export const useSelectorCallback = (
	selector,
	dependencies,
	compareEqual = DEFAULT_COMPARE_EQUAL
) => {
	const getState = useContext(StoreGetStateContext);
	const [subscribe, unsubscribe] = useContext(StoreSubscriptionContext);

	const initialState = useMemo(
		() => selector(getState()),

		// We really want to call selector here just on component mount.
		// This provides an initial value that will be recalculated when
		// store suscription has been called.
		// eslint-disable-next-line
		[]
	);

	const [selectorState, setSelectorState] = useState(initialState);
	const selectorCallback = useCallback(selector, dependencies);

	useEffect(() => {
		const onStoreChange = () => {
			const nextState = selectorCallback(getState());

			if (!compareEqual(selectorState, nextState)) {
				setSelectorState(nextState);
			}
		};

		onStoreChange();
		subscribe(onStoreChange);

		return () => {
			unsubscribe(onStoreChange);
		};
	}, [
		compareEqual,
		getState,
		selectorCallback,
		selectorState,
		subscribe,
		unsubscribe,
	]);

	return selectorState;
};
