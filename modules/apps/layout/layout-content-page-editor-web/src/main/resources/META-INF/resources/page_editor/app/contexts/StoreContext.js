/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useIsMounted} from '@liferay/frontend-js-react-web';
import React, {
	useCallback,
	useContext,
	useEffect,
	useMemo,
	useReducer,
	useRef,
	useState,
} from 'react';

import useUndo from '../components/undo/useUndo';

const DEFAULT_COMPARE_EQUAL = (a, b) => a === b;
const DEFAULT_DISPATCH = () => {};
const DEFAULT_GET_STATE = () => ({});

const StoreDispatchContext = React.createContext(DEFAULT_DISPATCH);
const StoreGetStateContext = React.createContext(DEFAULT_GET_STATE);
const StoreSubscriptionContext = React.createContext(() => {});

class EventEmitter {
	constructor() {
		this._listeners = new Map();
		this._nextListenerId = 0;
	}

	addListener(callback) {
		const id = this._nextListenerId++;

		this._listeners.set(id, callback);

		return {
			removeListener: () => {
				this._listeners.delete(id);
			},
		};
	}

	emit(data) {
		for (const listener of this._listeners.values()) {
			try {
				listener(data);
			}
			catch (error) {
				if (process.env.NODE_ENV === 'development') {
					console.error(error);
				}
			}
		}
	}
}

/**
 * Although StoreContextProvider creates a full functional store,
 * sometimes mocking dispatchs and/or store state may be necessary
 * for testing purposes.
 *
 * This component wraps it's children with an usable StoreContext
 * that calls dispatch and getState methods instead of using a real
 * reducer internally.
 */
export function StoreAPIContextProvider({
	children,
	dispatch = DEFAULT_DISPATCH,
	getState = DEFAULT_GET_STATE,
}) {
	const [emitter] = useState(() => new EventEmitter());
	const state = getState();

	const subscribe = useCallback(
		(subscriber) => emitter.addListener(subscriber),
		[emitter]
	);

	useEffect(() => {
		emitter.emit(state);
	}, [emitter, state]);

	return (
		<StoreSubscriptionContext.Provider value={subscribe}>
			<StoreDispatchContext.Provider value={dispatch}>
				<StoreGetStateContext.Provider value={getState}>
					{children}
				</StoreGetStateContext.Provider>
			</StoreDispatchContext.Provider>
		</StoreSubscriptionContext.Provider>
	);
}

/**
 * StoreContext is a black box for components: they should
 * get information from state and dispatch actions by using
 * given useSelector and useDispatch hooks.
 *
 * That's why we only provide a custom StoreContextProvider instead
 * of the raw React context.
 */
export function StoreContextProvider({children, initialState, reducer}) {
	const [state, dispatch] = useThunk(
		useUndo(useReducer(reducer, initialState))
	);

	const stateRef = useRef(state);
	const getState = useCallback(() => stateRef.current, []);

	stateRef.current = state;

	return (
		<StoreAPIContextProvider dispatch={dispatch} getState={getState}>
			{children}
		</StoreAPIContextProvider>
	);
}

/**
 * @see https://react-redux.js.org/api/hooks#usedispatch
 */
export function useDispatch() {
	return useContext(StoreDispatchContext);
}

export function useGetState() {
	return useContext(StoreGetStateContext);
}

export function useSelectorCallback(
	selector,
	dependencies,
	compareEqual = DEFAULT_COMPARE_EQUAL
) {
	const getState = useContext(StoreGetStateContext);
	const subscribe = useContext(StoreSubscriptionContext);

	const initialState = useMemo(
		() => selector(getState()),

		// We really want to call selector here just on component mount.
		// This provides an initial value that will be recalculated when
		// store suscription has been called.
		// eslint-disable-next-line
		[]
	);

	const [selectorState, setSelectorState] = useReducer((state, nextState) => {

		// If nextState is undefined, we consider this an accidental
		// outdated-props issue. If you need to use an empty real state,
		// use null instead.

		if (nextState === undefined) {
			return state;
		}

		return compareEqual(state, nextState) ? state : nextState;
	}, initialState);

	/* eslint-disable-next-line react-hooks/exhaustive-deps */
	const selectorCallback = useCallback(selector, dependencies);

	useEffect(() => {
		setSelectorState(selectorCallback(getState()));

		const handler = subscribe((nextState) => {
			setSelectorState(selectorCallback(nextState));
		});

		return () => {
			if (handler) {
				handler.removeListener();
			}
		};
	}, [getState, selectorCallback, subscribe]);

	return selectorState;
}

/**
 * @see https://react-redux.js.org/api/hooks#useselector
 */
export function useSelector(selector, compareEqual = DEFAULT_COMPARE_EQUAL) {
	return useSelectorCallback(selector, [], compareEqual);
}

export function useSelectorRef(selector) {
	const ref = useRef(null);

	useSelector((state) => {
		ref.current = selector(state);
	});

	return ref;
}

function useThunk([state, dispatch]) {
	const isMounted = useIsMounted();
	const stateRef = useRef(state);

	stateRef.current = state;

	const thunkDispatchRef = useRef((action) => {
		if (isMounted()) {
			if (typeof action === 'function') {
				return action(
					(payload) => {
						if (isMounted()) {
							dispatch(payload);
						}
					},
					() => {
						return stateRef.current;
					}
				);
			}
			else {
				dispatch(action);
			}
		}
	});

	return [state, thunkDispatchRef.current];
}
