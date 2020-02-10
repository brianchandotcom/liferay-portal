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
	useState
} from 'react';

import useThunk from '../../core/hooks/useThunk';
import {SERVICE_NETWORK_STATUS_TYPES} from '../config/constants/serviceNetworkStatusTypes';

const INITIAL_STATE = {
	/**
	 * A collection of dynamically loaded reducers that may be loaded from
	 * plugins at runtime.
	 *
	 * TODO: potentially allow us to specify a ranking here to determine order
	 */
	draft: true,
	masterLayoutData: {},
	network: {
		error: null,
		lastFetch: null,
		status: SERVICE_NETWORK_STATUS_TYPES.Idle
	},
	reducers: {},
	sidebarOpen: true
};

/**
 * Prepares the initial contents of the store based on server data.
 *
 * Data that goes in the store should be of "global" interest across the
 * app. Actions (user interactions, network activity etc) update that state,
 * and any interested component can obtain updates via the provided
 * useSelector hook.
 *
 * This is in contrast to immutable config, managed in the neigboring
 * config directory. Immutable config may be of interest across the app,
 * but it does not change in response to actions or events; it remains
 * static over the lifetime of the app.
 *
 * State which is not of "global" interest should go in component-local state
 * (see the `useState` hook) or a in more localized context objects that apply
 * to specific subtrees.
 */
export function getInitialState([data, config]) {
	const state = {
		...transformServerData(data, config)
	};

	// Exclude keys that were partitioned off into config.
	Object.keys(config).forEach(key => {
		delete state[key];
	});

	return {
		...INITIAL_STATE,
		...state
	};
}

/**
 * We may need to transform server data before initializing
 * the application state. These changes should be either temporary
 * or only related to non-persited states.
 */
function transformServerData(data, {panels}) {
	return {
		...data,

		// By default, show first panel of first section.
		sidebarPanelId: panels && panels[0] && panels[0][0]
	};
}

const StoreContext = React.createContext(null);

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
	dispatch = () => {},
	getState = () => ({})
}) => {
	const subscribers = useRef([]);

	const subscribe = useCallback(subscriber => {
		subscribers.current = [...subscribers.current, subscriber];
	}, []);

	const unsubscribe = useCallback(subscriber => {
		subscribers.current = subscribers.current.filter(
			_subscriber => _subscriber !== subscriber
		);
	}, []);

	useEffect(() => {
		storeRef.current.dispatch = dispatch;
	}, [dispatch]);

	useEffect(() => {
		storeRef.current.getState = getState;
		subscribers.current.forEach(subscriber => subscriber());
	}, [getState]);

	const storeRef = useRef({
		dispatch,
		getState,
		subscribe,
		unsubscribe
	});

	return (
		<StoreContext.Provider value={storeRef}>
			{children}
		</StoreContext.Provider>
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
		useReducer(reducer, initialState, getInitialState)
	);

	const getState = useCallback(() => state, [state]);

	return (
		<StoreAPIContextProvider dispatch={dispatch} getState={getState}>
			{children}
		</StoreAPIContextProvider>
	);
};

/**
 * @see https://react-redux.js.org/api/hooks#usedispatch
 */
export const useDispatch = () => {
	const storeRef = useContext(StoreContext);

	if (process.env.NODE_ENV === 'test' && !storeRef) {
		throw new Error('StoreContextProvider was not found');
	}

	return storeRef.current.dispatch;
};

/**
 * @see https://react-redux.js.org/api/hooks#useselector
 */
export const useSelector = (selector, compareEqual = (a, b) => a === b) => {
	const storeRef = useContext(StoreContext);

	if (process.env.NODE_ENV === 'test' && !storeRef) {
		throw new Error('StoreContextProvider was not found');
	}

	const initialState = useMemo(
		() => selector(storeRef.current.getState()),
		// We really want to call selector here just on component mount.
		// This provides an initial value that will be recalculated when
		// store suscription has been called.
		// eslint-disable-next-line
		[]
	);

	const [selectorState, setSelectorState] = useState(initialState);

	useEffect(() => {
		const store = storeRef.current;

		const onStoreChange = () => {
			const nextState = selector(storeRef.current.getState());

			if (!compareEqual(selectorState, nextState)) {
				setSelectorState(nextState);
			}
		};

		store.subscribe(onStoreChange);

		return () => {
			store.unsubscribe(onStoreChange);
		};
	}, [selectorState, storeRef, selector, compareEqual]);

	return selectorState;
};
